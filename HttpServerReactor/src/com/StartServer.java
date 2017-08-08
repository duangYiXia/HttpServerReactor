package com;

import com.core.Reactor;

public class StartServer {
	
	private static int port = 8989;
	
	public static void main(String[] args) {
		try {
			new Reactor(port).run();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
