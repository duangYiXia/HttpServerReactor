package com.core;

import java.io.IOException;  
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;  
  
public class Acceptor implements CommonMethod {  
	
	private static int acceptCount = 0;//记录连接次数
    private Reactor reactor; 
    public Acceptor(Reactor reactor){  
        this.reactor=reactor;  
    }  
    @Override  
    public void run() {  
        try {  
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();  
            acceptCount++;
            System.out.println("成功连接客户端！连接客户端数量acceptCount="+acceptCount);
            ByteBuffer inputBuffer = ByteBuffer.allocate(1024);//一个socketChannel对应一个buf
            //一个请求过来，新建一个变量，用于记录读取服务器文件的位置
            if(socketChannel!=null && socketChannel.isOpen()) {//调用Handler来处理channel  
                new SocketReadHandler(reactor.selector, socketChannel, inputBuffer, acceptCount);
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
} 
