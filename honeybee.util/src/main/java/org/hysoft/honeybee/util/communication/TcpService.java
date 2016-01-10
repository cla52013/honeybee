package org.hysoft.honeybee.util.communication;

import java.io.*;
import java.net.*;

import javax.sound.sampled.AudioFormat.Encoding;

public class TcpService {

	ServerSocket socket=null;
	   private static byte[] result = new byte[1024]; 
	   public TcpService(int port,Integer timeout) throws IOException{
		socket=new ServerSocket(port);
		socket.setSoTimeout(timeout);
	}
    
	
	
	public void ListenClientConnect() throws IOException {
		while (true)  
        {  
            Socket clientSocket = socket.accept();  
            ListenThread thread=new ListenThread(clientSocket);
            Thread receiveThread = new Thread(thread);  
            receiveThread.start();  
        }  
	}

 

}
