package comcat2.javax.servlet;


/**
 * 服务器小程序端接口
 */

public interface YcServlet {
        //初始化方法 在生命周期中 该方法只调用一次
    public void init();
    //销毁方法
    public void destroy();
    //每次请求都会调用service；
    public void service(YcServletRequest request,YcServletResponse response);




}
