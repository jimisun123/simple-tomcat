package server;

import server.connector.RequestProcessor;
import server.servlet.DefaultServletContainer;
import server.servlet.ServletContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * simple-tomcat 启动类
 */
public class Bootstrap {

    /**
     * 定义socket监听的端口号
     */
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     * 启动类
     *
     * @param args
     */
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动simple-tomcat入口
     */
    public void start() throws Exception {


        //【step 1】创建线程池/用于侦听客户端请求
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);

        //【step 2】初始化servlet容器
        ServletContainer servletContainer = new DefaultServletContainer();
        servletContainer.init("web.xml");


        //【step 3】 启动端口 侦听客户端请求
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=====>>>simple-tomcat start on port：" + port);

        while (true) {
            Socket socket = serverSocket.accept();
            // 【step 3.1 将用户的请求交给连接器模块的请求处理器进行解析】
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletContainer);
            // 【step 3.2 将解析过的请求处理器交给线程池进行处理】
            threadPoolExecutor.execute(requestProcessor);
        }


    }


}
