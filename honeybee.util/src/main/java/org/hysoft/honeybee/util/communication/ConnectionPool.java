package org.hysoft.honeybee.util.communication;

import java.net.Socket;
import java.util.Hashtable;

public class ConnectionPool {
	private static final int CONNECTION_POOL_SIZE = 10;  //全局常量  
    private static final String API_SERVER_HOST = "127.0.0.1";  
    private static final int API_SERVER_PORT = 8888;  
    private static ConnectionPool self = null;  
    private Hashtable<Integer, Socket> socketPool = null;    //连接池  
    private boolean [] socketStatusArray = null; //连接的状态(true-被占用,false-空闲)  
    /** 
     * 初始化连接池，最大TCP连接的数量为10 
     */  
    public static synchronized void init() {  
        self = new ConnectionPool();  
        self.socketPool = new Hashtable<Integer, Socket>();  
        self.socketStatusArray = new boolean [CONNECTION_POOL_SIZE];  
        //初始化连接池  
        System.out.println("初始化连接池.");  
        buildConnectionPool();  
    }  
    /** 
     * 建立连接池 
     */  
    public synchronized static void buildConnectionPool() {  
        if(self==null)  
            init();  
        System.out.println("准备建立连接池.");  
        Socket socket = null;  
        try {  
            for(int i=0;i<CONNECTION_POOL_SIZE;i++){  
                socket = new Socket(API_SERVER_HOST,API_SERVER_PORT);  
                self.socketPool.put(new Integer(i), socket);  
                self.socketStatusArray[i] = false;  
                }  
        } catch (Exception e) {  
            System.out.println("连接池建立失败!");  
            throw new RuntimeException(e);  //抛出  
        }  
        System.out.println("连接池建立成功.");  
    }  
    /** 
     * 从连接池中获取一个空闲的Socket 
     * @return 获取的TCP连接 
     */  
    public static Socket buildConnection(){  
        if(self==null)  
            init();  
        int i = 0;  
        for(i=0;i<CONNECTION_POOL_SIZE;i++){  
            if(!self.socketStatusArray[i]){  
                self.socketStatusArray[i] = true;  
                break;  
            }  
        }  
        if(i<=CONNECTION_POOL_SIZE) {  
            System.out.println("连接池中的第"+i+"个连接");  
            return self.socketPool.get(new Integer(i));  
        }  
        else {  
            System.out.println("从连接池建立连接失败，没有空闲的连接");  
            throw new RuntimeException("No enough pooled connection");  
        }  
    }  
    /** 
     * 当获得的socket不可用时，重新获得一个空闲的socket。 
     * @param socket 不可用的socket 
     * @return 新得到的socket 
     */  
    public static Socket rebuildConnection(Socket socket) {  
        if(self==null)  
            init();  
        Socket newSocket = null;  
        try {  
            for(int i=0;i<CONNECTION_POOL_SIZE;i++) {  
                if(self.socketPool.get(new Integer(i))==socket){  
                    System.out.println("重建连接池中的第"+i+"个连接");  
                    newSocket = new Socket(API_SERVER_HOST,API_SERVER_PORT);  
                    self.socketPool.put(new Integer(i), newSocket);  
                    self.socketStatusArray[i] = true;  
                }  
            }  
              
        } catch (Exception e) {  
            System.out.println("重建连接失败!");  
            throw new RuntimeException(e);  
        }  
        return newSocket;  
    }  
    /** 
     * 将用完的socket放回池中，调整为空闲状态。此时连接并没有断开。 
     * @param socket 使用完的socket 
     */  
    public static void releaseConnection(Socket socket){  
        if(self==null){  
            init();  
        }  
        for(int i=0;i<CONNECTION_POOL_SIZE;i++){  
            if(self.socketPool.get(new Integer(i))==socket){  
                self.socketStatusArray[i] = false;  
                System.out.println("释放连接 "+i);  
                break;  
            }  
        }  
    }  
    /** 
     * 断开池中所有连接 
     */  
    public synchronized static void releaseAllConnection() {  
        if(self==null)  
            init();  
          
        //关闭所有连接  
        Socket socket = null;  
        for(int i=0;i<CONNECTION_POOL_SIZE;i++){  
            socket = self.socketPool.get(new Integer(i));  
            try {  
                socket.close();  
                self.socketStatusArray[i] = false;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("全部连接已经关闭。");  
    }  
    /** 
     * 重新建立连接池。 
     */  
    public static void reset() {  
        self = null;  
        System.out.println("重建连接池...");  
        init();  
    }  
}
