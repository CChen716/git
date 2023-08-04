package comcat2.javax.servlet.http;


import comcat2.javax.servlet.YcServlet;
import comcat2.javax.servlet.YcServletRequest;
import comcat2.javax.servlet.YcServletResponse;

public  abstract class YcHttpServlet implements YcServlet {


    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    protected void doPost(YcHttpServletRequest req, YcHttpServletResponse resp){

    }
    protected void doGet(YcHttpServletRequest req,YcHttpServletResponse resp){

    }
    protected void doHead(YcHttpServletRequest req,YcHttpServletResponse resp){

    }
    protected void doDelete(YcHttpServletRequest req,YcHttpServletResponse resp){

    }
    protected void doTrance(YcHttpServletRequest req,YcHttpServletResponse resp){

    }
    protected void doOption(YcHttpServletRequest req,YcHttpServletResponse resp){

    }

    /**
     * 模版设计模式， 规范httpservlet中的方法的调用顺序
     * @param request
     * @param response
     */
    @Override//service中 判断method是什么 再对应调用doXXX方法
    public void service(YcServletRequest request, YcServletResponse response) {
        //从request中取出method(http协议持有)
      String method= ((YcHttpServletRequest) request).getMethod();
      if ("get".equalsIgnoreCase(method)){
            doGet((YcHttpServletRequest)request,( YcHttpServletResponse)response );
      }else if ("post".equalsIgnoreCase(method)){
          doPost((YcHttpServletRequest)request,( YcHttpServletResponse)response );
      }else if ("head".equalsIgnoreCase(method)){
          doHead((YcHttpServletRequest)request,( YcHttpServletResponse)response );
      }
      else if ("trace".equalsIgnoreCase(method)){
          doTrance((YcHttpServletRequest)request,( YcHttpServletResponse)response );
      }
      else if ("delete".equalsIgnoreCase(method)){
          doDelete((YcHttpServletRequest)request,( YcHttpServletResponse)response );
      }else if ("option".equalsIgnoreCase(method)){
          doOption((YcHttpServletRequest)request,( YcHttpServletResponse)response );
      }else {
          //TODO 错误的响应协议

      }

    }
    public void service(YcHttpServletRequest request, YcHttpServletResponse response) {
      service(request,response);

    }
}
