package sockett;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Serverr {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9002);
        Socket accept = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        String s = bufferedReader.readLine();
        System.out.println(s);


//
//        final char[] buffer = new char[1024];
//        InputStream inputStream = accept.getInputStream();
//        StringBuilder sb = new StringBuilder();
//        InputStreamReader in = new InputStreamReader(inputStream,"UTF-8");
//        for(;;){
//            int read = in.read(buffer, 0, buffer.length);
//            if(read<0)
//                break;
//            sb.append(buffer,0,read);
//        }
//        System.out.println(sb.toString());

    }
}
