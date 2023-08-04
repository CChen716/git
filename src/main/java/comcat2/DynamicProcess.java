package comcat2;



import comcat2.javax.Processor;
import comcat2.javax.servlet.YcServlet;
import comcat2.javax.servlet.YcServletContext;
import comcat2.javax.servlet.http.YcHttpServletRequest;
import comcat2.javax.servlet.http.YcHttpServletResponse;

import java.io.PrintWriter;

/**
 * 动态资源处理类
 */
public class DynamicProcess implements Processor {

    @Override
    public void process(YcHttpServletRequest request, YcHttpServletResponse response) {
        String url=((YcHttpServletRequest)request).getRequestURI();
        int contentPathLength=((YcHttpServletRequest)request).getContextPath().length();
        //url=url.substring(contentPathLength);


        YcServlet servlet=null;

        try {


            if (YcServletContext.servletInstance.containsKey(url)) {
                servlet = YcServletContext.servletInstance.get(url);
            } else {
                Class clz = YcServletContext.servletClass.get(url);
                Object obj = clz.newInstance();
                if (obj instanceof YcServlet) {
                    servlet = (YcServlet) obj;
                    servlet.init();
                }
            }
            servlet.service(request, response);


        }catch (Exception e){
            String bodyEntity=e.toString();
            String protocol=gen500(bodyEntity);
            //以输出流返回到客户端
            PrintWriter out= response.getWriter();
            out.println(protocol);
            out.println(bodyEntity);
            out.flush();
        }


    }

    private String gen500(String bodyEntity) {
        String protocol500="HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/html\r\nContent-length: "+ bodyEntity.getBytes ().length+"\r\n\r\n";
        return protocol500;

    }

}
