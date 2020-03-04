package http.req;

import com.sun.xml.internal.fastinfoset.Encoder;
import http.conf.EncodeConf;

import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        //请求
        Request req = new Request();
        req.setCommand("HELLO");
        req.setCommandLength(req.getCommand().length());
        req.setEncode(EncodeConf.UTF8.getCode());
        Socket client = new Socket("127.0.0.1", 4567);
        OutputStream outputStream = client.getOutputStream();


    }
}
