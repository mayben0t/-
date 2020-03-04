package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Provider {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ServerSocket server = new ServerSocket(1234);
        //用于存放生产者服务接口的Map,实际的框架中会有专门保存服务提供者的
        Map<String, Object> serviceMap = new HashMap();
        serviceMap.put(SayHelloService.class.getName(), new SayHelloServiceImpl());
        while (true){
            Socket socket = server.accept();

            //读取服务信息
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String interfacename = input.readUTF();
            String methodName = input.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
            Object[] arguments = (Object[]) input.readObject();

            Class<?> serviceinterfaceclass = Class.forName(interfacename);

            Object service = serviceMap.get(interfacename);
            Method method = serviceinterfaceclass.getMethod(methodName, parameterTypes);
            Object result = method.invoke(service, arguments);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(result);

        }

    }
}
