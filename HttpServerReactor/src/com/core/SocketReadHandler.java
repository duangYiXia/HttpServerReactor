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
		// ��SelectionKey��Ϊ��Handler ��һ�����¼�����ʱ�������ñ����run������
		// �ο�dispatch(SelectionKey key)
		selectionKey.attach(this);

		// ͬʱ��SelectionKey���Ϊ�ɶ����Ա��ȡ��
		selectionKey.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
	}

	/**
	 * �����ȡ����
	 */
	public void run() {
		try {
			// ������������
			Request req = new Request(socketChannel, inputBuffer);
			req.parseRequest();

			String uri = req.getUri();
			String web_root = System.getProperty("user.dir");
			//��ȡ�������ļ�ͨ��
			RandomAccessFile raf = new RandomAccessFile(web_root+uri, "r");
			//�������̶߳�ȡ�������ļ�
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
