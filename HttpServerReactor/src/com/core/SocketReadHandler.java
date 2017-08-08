package com.core;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import com.core.header.Request;

public class SocketReadHandler implements CommonMethod {

	private SocketChannel socketChannel;
	private SelectionKey key;
	private ByteBuffer inputBuffer;
	private int acceptCount;

	public SocketReadHandler(Selector selector, SocketChannel socketChannel, ByteBuffer inputBuffer, int acceptCount) throws IOException {

		this.socketChannel = socketChannel;
		this.inputBuffer = inputBuffer;
		this.acceptCount = acceptCount;
		
		socketChannel.configureBlocking(false);
		SelectionKey selectionKey = socketChannel.register(selector, 0);
		this.key = selectionKey;
		// 将SelectionKey绑定为本Handler 下一步有事件触发时，将调用本类的run方法。
		// 参看dispatch(SelectionKey key)
		selectionKey.attach(this);

		// 同时将SelectionKey标记为可读，以便读取。
		selectionKey.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
	}

	/**
	 * 处理读取数据
	 */
	public void run() {
		try {
			// 读并解析请求
			Request req = new Request(socketChannel, inputBuffer);
			req.parseRequest();

			String uri = req.getUri();
			String web_root = System.getProperty("user.dir");
			//获取服务器文件通道
			RandomAccessFile raf = new RandomAccessFile(web_root+uri, "r");
			//激活新线程读取服务器文件
			//Map<String, Object> dealResult = new DoFile().readServerFile(uri);
			Map<String, Object> dealResult = new HashMap<String, Object>();
			dealResult.put("uri", uri);
			dealResult.put("raf", raf);
			dealResult.put("acceptCount", acceptCount);
			new SocketWriteHandlel(dealResult, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
