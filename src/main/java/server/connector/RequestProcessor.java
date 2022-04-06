package server.connector;

import server.servlet.HttpServlet;
import server.servlet.ServletContainer;

import java.io.InputStream;
import java.net.Socket;

public class RequestProcessor extends Thread {

    private Socket socket;
    private ServletContainer servletContainer;


    public RequestProcessor(Socket socket, ServletContainer servletContainer) {
        this.socket = socket;
        this.servletContainer = servletContainer;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if (servletContainer.getServletMap().get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());
            } else {
                // 动态资源servlet请求
                HttpServlet httpServlet = servletContainer.getServletMap().get(request.getUrl());
                httpServlet.service(request, response);
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
