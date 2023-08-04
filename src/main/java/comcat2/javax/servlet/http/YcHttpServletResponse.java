package comcat2.javax.servlet.http;



import comcat2.javax.servlet.YcServletResponse;

import java.io.*;

public class YcHttpServletResponse implements YcServletResponse {
    private YcHttpServletRequest request;
    private OutputStream oos;

    public YcHttpServletResponse(YcHttpServletRequest request, OutputStream oos){
        this.request=request;
        this.oos=oos;
    }


    public void send(){
        String url=this.request.getRequestURL();    //woluntuan/index.html
        String realPath=this.request.getRealPath();  //服务器路径
        File f=new File(realPath,url);
        String responseProtocol=null;
        byte [] fileContent=null;
        if (!f.exists()){
            //文件不存在
            fileContent=readFile(new File(realPath,"/index.html"));
            responseProtocol=gen404(fileContent);
        }else {
            //文件存在
            fileContent=readFile(new File(realPath,url));
        }
        try {
            oos.write(responseProtocol.getBytes());
            oos.flush();
            oos.write(fileContent);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (oos!=null){
                try {
                    this.oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return oos;
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(this.oos);
    }


    private String gen200(byte[] fileContent){
        String protoco200="";
        String url=this.request.getRequestURL();
        int index=url.lastIndexOf(".");
        if (index>=0){
            index=index+1;
        }
        String fileExtension=url.substring(index);
        //TODO 策略模式 来读取server.xml中的配置文件
        if ("JPG".equalsIgnoreCase(fileExtension)){
            protoco200="HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }else if ("css".equalsIgnoreCase(fileExtension)){
            protoco200="HTTP/1.1 200 OK\r\nContent-Type: text/css\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }else if ("js".equalsIgnoreCase(fileExtension)){
            protoco200="HTTP/1.1 200 OK\r\nContent-Type: application\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }else if ("gif".equalsIgnoreCase(fileExtension)){
            protoco200="HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }else if ("PNG".equalsIgnoreCase(fileExtension)){
            protoco200="HTTP/1.1 200 OK\r\nContent-Type: image/png\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }else {
            protoco200="HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }

        return protoco200;
    }


    private String gen404(byte[] fileContent){
        String protocol404="HTTP/1.1 404 NOT Found\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: "+fileContent.length+"\r\n";
        protocol404+="Server: kitty server\r\n\r\n";
        return protocol404;
    }






    private byte[] readFile(File file){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        FileInputStream fis= null;
        try {
            fis = new FileInputStream(file);
            byte[]bs=new byte[100*1024];
            int length=-1;
            while ((length=fis.read(bs,0,  bs.length))!=-1){
                baos.write(bs,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return baos.toByteArray();
    }
}
