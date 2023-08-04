package comcat2.javax;


import comcat2.javax.servlet.http.YcHttpServletRequest;
import comcat2.javax.servlet.http.YcHttpServletResponse;

/**
 * 资源处理接口
 */
public interface Processor {
    public void process(YcHttpServletRequest request, YcHttpServletResponse response);
}
