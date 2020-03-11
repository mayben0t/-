package sockett;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Clientt {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",9002);

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        OutputStream outputStream = socket.getOutputStream();

        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(s);
        printWriter.flush();
//        PrintWriter pw = new PrintWriter(socket.getOutputStream());
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        pw.println("aaa");
//        pw.flush();
    }
}
