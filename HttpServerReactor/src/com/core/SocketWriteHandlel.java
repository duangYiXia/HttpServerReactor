package com.core;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Map;
import com.core.header.Response;

public class SocketWriteHandlel implements CommonMethod {

	private SocketChannel sc;
	private SelectionKey key;
	private Selector selector;
	private int acceptCount;
	private RandomAccessFile raf;
	private static int[] positions = new int[1000];//数组大小取决于并发的连接数

	public SocketWriteHandlel(Map<String, Object> dealResult, SelectionKey key) throws ClosedChannelException {

		this.key = key;
		this.raf = (RandomAccessFile) dealResult.get("raf");
		sc = (SocketChannel) key.channel();
		selector = key.selector();
		this.acceptCount = (Integer)dealResult.get("acceptCount");
		// 重新绑定为写对象
		key.attach(this);
		// 同时将SelectionKey标记为可读，以便读取。
		key.interestOps(SelectionKey.OP_WRITE);
		selector.wakeup();
	}

	public void run() {

		//System.out.println("acceptCount-1="+(acceptCount-1));
		try {
			//生成随机数
			FileChannel fc = raf.getChannel();
			Integer fileSize = (int) fc.size();
			if (fileSize > 1024 * 1024 * 10) {// 只在文件大于10M且第一次写头信息
				if (positions[acceptCount] == 0) {
					// 响应信息
					String responseCode = "200".trim();
					String contentType = "application/octet-stream".trim();
					String contenLen = Integer.toString(fileSize);

					Response response = new Response(responseCode, contentType, contenLen);
					String responseStr = response.getRespHeadInfo();

					// 第一部分：响应头部信息
					sc.write(ByteBuffer.wrap(responseStr.getBytes()));
				}
				// 第二部分
				long readbytes = -1;
				System.out.println("position111111111=" + positions[acceptCount]);
				readbytes = fc.transferTo(positions[acceptCount], 128, sc);//1、定义128字节，为了下载慢一点

				positions[acceptCount] += (int) readbytes;
				System.out.println("position222222222=" + positions[acceptCount]);
			} else {

				long readbytes = -1;
				readbytes = fc.transferTo(positions[acceptCount], 2048, sc);//2、定义2048为了浏览快一点，1和2这样写为了方便测试并发。

				positions[acceptCount] += (int) readbytes;
				System.out.println("**************position=" + positions[acceptCount]);
			}

			// 重新绑定为写对象
			key.attach(this);
			// 同时将SelectionKey标记为可读，以便读取。
			key.interestOps(SelectionKey.OP_WRITE);
			selector.wakeup();

			// 关闭通道
			if (positions[acceptCount] == fileSize) {
				raf.close();
				fc.close();
				sc.close();
				key.cancel();
			}
		} catch (Exception e) {
			try {
				sc.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
