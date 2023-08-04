package comcat2.javax.servlet.http;

import comcat2.javax.servlet.YcServletRequest;
import org.apache.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class YcHttpServletRequest implements YcServletRequest {
    private Logger logger=Logger.getLogger(YcHttpServletRequest.class);

    private Socket s;
    private InputStream iis;
    private String method;
    //定位符 http://...................
    private String requestURL;
    //标识符  /108109res/doUpload.action
    private String requestURI;
    //上下文
    private String contextPath;
    //请求字符串  请求的地址栏参数    age=20&sex=mal
    private String queryString;
    /*参数  地址栏参数 uname=a&pwd=b  表单中的参数-请求实体:sex=男&ins=打球*/
    private Map<String,String[]> parameterMap=new ConcurrentHashMap<>();
    //协议类型   http://
    private String scheme;
    //协议版本
    private String protocol;
    //**项目 的真实路径
    private String realPath;
    public YcHttpServletRequest( Socket s,InputStream iis){
        this.s=s;
        this.iis=iis;
        this.parseRequest();
    }
    //解析方法·
    private void parseRequest(){
            String requestInfoString=readFromInputStream();
            if (requestInfoString==null||"".equals(requestInfoString.trim())){
                throw new RuntimeException("读取输入流异常");
            }
            //解析http请求头
        parseRequestInfoString(requestInfoString);
    }

    //http请求头(存各种信息)
    private void parseRequestInfoString(String requestInfoString) {
        StringTokenizer st=new StringTokenizer(requestInfoString);
        this.method=st.nextToken();
        this.requestURI=st.nextToken();
        //要requestURI要考虑地址栏参数
        int questionIndex =this.requestURI.lastIndexOf("?");
        if (questionIndex>=0){
            //有?即有地址栏参数 -》queryString
            this.queryString=this.requestURI.substring(questionIndex+1);
            this.requestURI=this.requestURI.substring(0,questionIndex);
        }
        //第三部分 协议版本
        this.protocol=st.nextToken();
        this.scheme=this.protocol.substring(0,this.protocol.indexOf("/"));

        int slash2Index=this.requestURI.indexOf("/",1);
        if (slash2Index>=0){
            this.contextPath=this.requestURI.substring(0,slash2Index);  //目录名 localhost/...后面的目录名
        }else {
            this.contextPath=this.requestURI;
        }
        this.requestURL=this.scheme+"://"+this.s.getLocalSocketAddress()+this.requestURI;

        //参数的类型 : /1089res/index.html?uname=a&pwd=b
        //从QueryString 取出参数
        if (this.queryString!=null&&this.queryString.length()>0){
            String[]ps=this.queryString.split("&");
            for (String s:ps){
                String [] params=s.split("=");
                //  情况1 uname=a      情况2 ins=a,b,c
                this.parameterMap.put(params[0],params[1].split(","));
            }
            //TODO  还有post的实体中有可能有参数
        }
        //realPath
        this.realPath=System.getProperty("user.dir")+ File.separator+"webapps"; //获取到当前项目的绝对路径


    }

    //从输入流中取http请求信息（文字）
    private String readFromInputStream(){
        int length=-1;
        StringBuffer sb=null;
        byte []bs=new byte[300*1024];
        try {
            length=this.iis.read(bs,0,bs.length);
            sb=new StringBuffer();
            for (int i = 0; i < length; i++) {
                sb.append((char) bs[i]);
            }

        } catch (IOException e) {
            logger.error("读取请求信息失败.....");
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }





    public String[] getParameterValues(String name){
        if (parameterMap==null||parameterMap.size()<0){
            return null;
        }
        String [] values=this.parameterMap.get(name);
        return values;
    }




    private String getParameter(String name){
        String[] values=getParameterValues(name);
        if (values==null||values.length<0){
            return null;
        }
        return values[0];
    }


    public String getMethod(){
        return this.method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getQueryString() {
        return queryString;
    }

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public String getScheme() {
        return scheme;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getRealPath() {
        return realPath;
    }

}
