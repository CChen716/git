package comcat2;

import comcat2.javax.servlet.YcServletContext;
import comcat2.javax.servlet.YcWebServlet;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class TomcatServer {
    static Logger logger=Logger.getLogger(TomcatServer.class);
    public static void main(String[] args) {
        logger.debug("程序启动聊");
        TomcatServer ts=new TomcatServer();
        int port=ts.parsePortFromXml();
        //创建日志对象
        logger.debug("服务器配置端口为:"+port);
        ts.startServer(port);
    }

    private void startServer(int port){
        boolean flag=true;

        String packageName="comcat2";
        String packagePath=packageName.replaceAll("\\.","/");

        try {
            Enumeration<URL>  files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while (files.hasMoreElements()){
                URL url=files.nextElement();
                logger.info("正在扫描的包路径为:"+url.getFile());
                findPackageClasses(url.getFile(),packageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       try (  ServerSocket ss=new ServerSocket(port)){   //try()圆括号中表示try块执行结束后会自动关闭资源  就不用再finally中执行close()方法 jdk1.7引入
           logger.debug("服务器启动成功,配置端口为:"+port);
        //TODO 可以读取server.xml关于是否开启关于线程池的配置 决定是否使用线程池
        while (flag) {
            Socket s = null;
            try {
                s = ss.accept();
                logger.debug("客户端" + s.getRemoteSocketAddress() + "连上了服务器");

                TaskService task = new TaskService(s);
                Thread t = new Thread(task);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("服务器套接字创建失败");
            }
        }
        } catch (Exception e) {
           e.printStackTrace();
       }
    }

    private void findPackageClasses(String packagePath,String packageName){
        if ( packageName.startsWith("/")){
            packagePath=packagePath.substring(1);

        }
        //取这个路径下所有的字节码文件
        File file=new File(packagePath);
        File [] classFiles=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".class")|| pathname.isDirectory()) {
                    return true;
                }else {
                    return false;
                }
            }
        });
        System.out.println(classFiles);
        if (classFiles!=null&&classFiles.length>0){
            for (File cf : classFiles) {
                if (cf.isDirectory()){
                    findPackageClasses(cf.getAbsolutePath(),packageName+"."+cf.getName());
                }else {
                    //是字节码文件 则利用类加载器加载这个 class文件
                    URLClassLoader uc=new URLClassLoader(new URL[]{});
                    try {
                   Class cls = uc.loadClass(packageName+"."+cf.getName().replaceAll(".class",""));
//                    logger.info("加载了一个类:"+cls.getName());
                    if (cls.isAnnotationPresent(YcWebServlet.class)){
                        logger.info("加载了一个类:"+cls.getName());
                         YcWebServlet anno= (YcWebServlet) cls.getAnnotation(YcWebServlet.class);  //注解中取出url值
                          String url=anno.value();
                        //通过   注解的value方法取出  url地址  存到 YcservletContex.serrvletClass这个map中
                        YcServletContext.servletClass.put(url,cls);
                    }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }





    private  int parsePortFromXml(){
        int port=8090;
        String serverXmlPath=System.getProperty("user.dir")+ File.separator+"conf"+File.separator+"server.xml";
        try(InputStream iis=new FileInputStream(serverXmlPath);) {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=factory.newDocumentBuilder();
            Document doc=documentBuilder.parse(iis);
            NodeList nl=doc.getElementsByTagName("Connector");
            for (int i=0;i<nl.getLength();i++){
                Element node= (Element) nl.item(i);
                port=Integer.parseInt(node.getAttribute("port"));
            }

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return port;

    }


}
