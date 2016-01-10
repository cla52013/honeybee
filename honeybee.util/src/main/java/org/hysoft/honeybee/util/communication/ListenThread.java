package org.hysoft.honeybee.util.communication;

import java.io.InputStream;
import java.net.Socket;

public class ListenThread implements Runnable  {
	
	Socket myClientSocket=null;
	public  ListenThread(Socket socket) {
		myClientSocket=socket;
	}
	
	public void run() {
		while (true)  
        {  
            try  
            {  
            	 InputStream is =myClientSocket.getInputStream();  
         	     String ip= myClientSocket.getInetAddress().getHostAddress();
         	     byte[] buf = new byte[1024]; 
         	     int len = is.read(buf); 
         	     String text = new String(buf,0,len); 
         	     System.out.println(ip+":"+text);  
            }  
            catch(Exception ex)  
            {  
                break;  
            }  
        }  
	}
}

