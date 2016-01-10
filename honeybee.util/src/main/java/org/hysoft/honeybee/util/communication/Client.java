package org.hysoft.honeybee.util.communication;

import java.io.*;
import java.net.Socket;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		Socket socket = new Socket("127.0.0.1",19881); 
		while (true) {
			Thread.sleep(10);
		    //获取socket流中的输出流。 向服务器输出消息，即发消息到服务器 
		    OutputStream out = socket.getOutputStream(); 
		    //使用输出流将指定的数据写出去。 
		    out.write("tcp演示：哥们又来了!".getBytes()); 
		}

	}

}
