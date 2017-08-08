package com.core.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class DealStringOrByteBuffer {
	
	public static String getString(ByteBuffer buffer) {
		
		String requestStr = "";
		try {
			byte[] content = new byte[buffer.limit()];
			buffer.get(content);
			requestStr = new String(content, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return requestStr;
	}
}
