package com.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 反应器模式 用于解决多用户访问并发问题
 * 
 * 举个例子：餐厅服务问题
 * 
 * 传统线程池做法：来一个客人(请求)去一个服务员(线程)
 * 反应器模式做法：当客人点菜的时候，服务员就可以去招呼其他客人了，等客人点好了菜，直接招呼一声“服务员”
 * 
 * @author zjx
 */
public class Reactor {
	public final Selector selector;
	public final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {

		// 创建serverSocketChannel
		serverSocketChannel = ServerSocketChannel.open();
		// InetSocketAddress inetSocketAddress=new
		// InetSocketAddress(InetAddress.getLocalHost(),port);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));

		System.out.println("本地服务器已经启动,端口："+port+"文件路径:/files/1.txt or 2.zip");

		// 设置通道非阻塞
		serverSocketChannel.configureBlocking(false);

		// 创建selector
		selector = Selector.open();

		// 向selector注册该channel
		SelectionKey acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		// 利用selectionKey的attache功能绑定Acceptor 如果有事情，触发Acceptor
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
				// Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
				while (it.hasNext()) {
					// 来一个事件 第一次触发一个accepter线程
					// 以后触发SocketReadHandler
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
	 * 运行Acceptor或SocketReadHandler
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
