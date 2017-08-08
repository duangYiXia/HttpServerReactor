package com.core.header;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.core.util.DealStringOrByteBuffer;

public class Request {
	
	private SocketChannel sc;
	private String uri;
	private ByteBuffer readbuf;
	
	public Request(SocketChannel sc, ByteBuffer readbuf) {
		this.sc = sc;
		this.readbuf = readbuf;
	}
	public void parseRequest() {
		StringBuffer strbuf = new StringBuffer();
		int len = -1;
		try {
			while((len = sc.read(readbuf)) > 0) {
				
				readbuf.flip();
				String str = DealStringOrByteBuffer.getString(readbuf);
				strbuf.append(str);
				readbuf.clear();
			}
			
			if(len == -1) {
				System.out.println("断开连接！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String requestStr = strbuf.toString();
		System.out.println("requestStr=" + requestStr);
		int firstSpace = requestStr.indexOf(" ");
		if(firstSpace !=-1) {
			
			int secondSpace = requestStr.indexOf(" ", firstSpace+1);
			if(secondSpace > firstSpace) {
				this.uri = requestStr.substring(firstSpace+1, secondSpace);//截取uri
			}
		}
	}
	
	public void printRequestInfo(ByteBuffer buf) {
		
		System.out.println("requestStr="+DealStringOrByteBuffer.getString(buf));
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
