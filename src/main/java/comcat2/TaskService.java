package comcat2;


import comcat2.javax.Processor;
import comcat2.javax.servlet.YcServletContext;
import comcat2.javax.servlet.http.YcHttpServletRequest;
import comcat2.javax.servlet.http.YcHttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TaskService implements Runnable{

    private Logger logger=Logger.getLogger(TaskService.class);
    private Socket s;
    private InputStream iis;
    private OutputStream oos;
    private boolean flag=true;


    public TaskService(Socket s) {
        this.s=s;
        try {
            this.iis=this.s.getInputStream();
            this.oos=this.s.getOutputStream();
        } catch (Exception e) {
            logger.error("socket获取异常");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        if (this.flag){
            YcHttpServletRequest request=new YcHttpServletRequest(this.s ,this.iis);
            YcHttpServletResponse response=new YcHttpServletResponse(request,this.oos);
//            response.send();
            Processor processor=null;
           // int contentPathLength=request.getContextPath().length();
            String uri=request.getRequestURI().substring(0);
            if (YcServletContext.servletClass.containsKey(uri)){
                processor=new DynamicProcess();
            }else {
                processor=new StaticProcessor();
            }
            processor.process(request,response);
        }
        try {
            this.iis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
