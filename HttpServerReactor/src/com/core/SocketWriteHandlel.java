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
	private static int[] positions = new int[1000];//�����Сȡ���ڲ�����������

	public SocketWriteHandlel(Map<String, Object> dealResult, SelectionKey key) throws ClosedChannelException {

		this.key = key;
		this.raf = (RandomAccessFile) dealResult.get("raf");
		sc = (SocketChannel) key.channel();
		selector = key.selector();
		this.acceptCount = (Integer)dealResult.get("acceptCount");
		// ���°�Ϊд����
		key.attach(this);
		// ͬʱ��SelectionKey���Ϊ�ɶ����Ա��ȡ��
		key.interestOps(SelectionKey.OP_WRITE);
		selector.wakeup();
	}

	public void run() {

		//System.out.println("acceptCount-1="+(acceptCount-1));
		try {
			//���������
			FileChannel fc = raf.getChannel();
			Integer fileSize = (int) fc.size();
			if (fileSize > 1024 * 1024 * 10) {// ֻ���ļ�����10M�ҵ�һ��дͷ��Ϣ
				if (positions[acceptCount] == 0) {
					// ��Ӧ��Ϣ
					String responseCode = "200".trim();
					String contentType = "application/octet-stream".trim();
					String contenLen = Integer.toString(fileSize);

					Response response = new Response(responseCode, contentType, contenLen);
					String responseStr = response.getRespHeadInfo();

					// ��һ���֣���Ӧͷ����Ϣ
					sc.write(ByteBuffer.wrap(responseStr.getBytes()));
				}
				// �ڶ�����
				long readbytes = -1;
				System.out.println("position111111111=" + positions[acceptCount]);
				readbytes = fc.transferTo(positions[acceptCount], 128, sc);//1������128�ֽڣ�Ϊ��������һ��

				positions[acceptCount] += (int) readbytes;
				System.out.println("position222222222=" + positions[acceptCount]);
			} else {

				long readbytes = -1;
				readbytes = fc.transferTo(positions[acceptCount], 2048, sc);//2������2048Ϊ�������һ�㣬1��2����дΪ�˷�����Բ�����

				positions[acceptCount] += (int) readbytes;
				System.out.println("**************position=" + positions[acceptCount]);
			}

			// ���°�Ϊд����
			key.attach(this);
			// ͬʱ��SelectionKey���Ϊ�ɶ����Ա��ȡ��
			key.interestOps(SelectionKey.OP_WRITE);
			selector.wakeup();

			// �ر�ͨ��
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
