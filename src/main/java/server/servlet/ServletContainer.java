package server.servlet;

import java.util.Map;

public interface ServletContainer {

    void init(String webXmlName);

    Map<String, HttpServlet>  getServletMap();
}
