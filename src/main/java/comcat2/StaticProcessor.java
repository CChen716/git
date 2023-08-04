package comcat2;


import comcat2.javax.Processor;
import comcat2.javax.servlet.http.YcHttpServletRequest;
import comcat2.javax.servlet.http.YcHttpServletResponse;

public class StaticProcessor implements Processor {
    @Override
    public void process(YcHttpServletRequest request, YcHttpServletResponse response) {
//        ((YcHttpServletResponse)response).send();
        response.send();
    }
}
