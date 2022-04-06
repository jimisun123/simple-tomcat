package server.servlet;

import server.connector.Request;
import server.connector.Response;
import server.utils.HttpProtocolUtil;

import java.io.IOException;

public class DemoServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {


        String content = "<h1>Demo get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>Demo post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }
}
