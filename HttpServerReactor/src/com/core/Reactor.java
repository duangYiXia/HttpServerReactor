package com.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * ��Ӧ��ģʽ ���ڽ�����û����ʲ�������
 * 
 * �ٸ����ӣ�������������
 * 
 * ��ͳ�̳߳���������һ������(����)ȥһ������Ա(�߳�)
 * ��Ӧ��ģʽ�����������˵�˵�ʱ�򣬷���Ա�Ϳ���ȥ�к����������ˣ��ȿ��˵���˲ˣ�ֱ���к�һ��������Ա��
 * 
 * @author zjx
 */
public class Reactor {
	public final Selector selector;
	public final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {

		// ����serverSocketChannel
		serverSocketChannel = ServerSocketChannel.open();
		// InetSocketAddress inetSocketAddress=new
		// InetSocketAddress(InetAddress.getLocalHost(),port);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));

		System.out.println("���ط������Ѿ�����,�˿ڣ�"+port+"�ļ�·��:/files/1.txt or 2.zip");

		// ����ͨ��������
		serverSocketChannel.configureBlocking(false);

		// ����selector
		selector = Selector.open();

		// ��selectorע���channel
		SelectionKey acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		// ����selectionKey��attache���ܰ�Acceptor ��������飬����Acceptor
		acceptKey.attach(new Acceptor(this));
	}

	public void run() {
		//DebugMethod d = new DebugMethod();
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				//d.debug("all keys number: " + (selector.keys().size()));
				/*for (SelectionKey key : selector.keys()) {
					if(key.isValid())
						d.printKeyInfo(key);
				}*/
				//d.debug("selected keys number: " + (selectionKeys.size()));
				Iterator<SelectionKey> it = selectionKeys.iterator();
				// Selector�������channel��OP_ACCEPT��READ�¼����������б����ͻ���С�
				while (it.hasNext()) {
					// ��һ���¼� ��һ�δ���һ��accepter�߳�
					// �Ժ󴥷�SocketReadHandler
					SelectionKey selectionKey = it.next();
					//d.printKeyInfo(selectionKey);
					it.remove();
					dispatch(selectionKey);

					//d.printKeyInfo(selectionKey);
					//selectionKeys.clear();
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * ����Acceptor��SocketReadHandler
	 * 
	 * @param key
	 */
	void dispatch(SelectionKey key) {
		CommonMethod r = (CommonMethod) (key.attachment());
		if (r != null) {
			r.run();
		}
	}

}
