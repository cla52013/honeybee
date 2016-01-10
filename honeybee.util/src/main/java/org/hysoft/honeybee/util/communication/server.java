package org.hysoft.honeybee.util.communication;

import java.io.IOException;

public class server {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		TcpService tService=new TcpService(19881,100000);
		while (true) { 
			tService.ListenClientConnect();
		}
	}

}
