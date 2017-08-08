package com.core;

import java.io.IOException;  
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;  
  
public class Acceptor implements CommonMethod {  
	
	private static int acceptCount = 0;//��¼���Ӵ���
    private Reactor reactor; 
    public Acceptor(Reactor reactor){  
        this.reactor=reactor;  
    }  
    @Override  
    public void run() {  
        try {  
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();  
            acceptCount++;
            System.out.println("�ɹ����ӿͻ��ˣ����ӿͻ�������acceptCount="+acceptCount);
            ByteBuffer inputBuffer = ByteBuffer.allocate(1024);//һ��socketChannel��Ӧһ��buf
            //һ������������½�һ�����������ڼ�¼��ȡ�������ļ���λ��
            if(socketChannel!=null && socketChannel.isOpen()) {//����Handler������channel  
                new SocketReadHandler(reactor.selector, socketChannel, inputBuffer, acceptCount);
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
} 
