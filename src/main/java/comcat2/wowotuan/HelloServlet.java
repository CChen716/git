package comcat2.wowotuan;



import comcat2.javax.servlet.YcWebServlet;
import comcat2.javax.servlet.http.YcHttpServlet;
import comcat2.javax.servlet.http.YcHttpServletRequest;
import comcat2.javax.servlet.http.YcHttpServletResponse;

import java.io.PrintWriter;

@YcWebServlet("/hello")
public class HelloServlet extends YcHttpServlet {

    public HelloServlet(){
        System.out.println("构造方法");
    }
    @Override
    public void init() {
        System.out.println("初始化方法");
    }

    @Override
    protected void doGet(YcHttpServletRequest req, YcHttpServletResponse resp) {
        String result="hello";
        PrintWriter out=resp.getWriter();
        out.println("HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8\r\nContent-length:"+result.getBytes().length+"\r\n\r\n");
        out.println(result);
        out.flush();

    }


}
