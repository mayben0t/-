package nioSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectionKey;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Serverr {
    /*
    NioSocket中服务端的处理过程可以分为5步
    1.创建ServerSocketChannel并设置相应参数
    2.创建Selector并注册到ServerSocketChannel上
    3.调用Selector的select方法等待请求
    4.Selector接收到请求后使用selectedKeys返回SelectionKey集合
    5.使用SelectionKey获取到Channel、Selector和操作类型并进行具体分析
     */

    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel，监听8080端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        //设置为非阻塞模式
        ssc.configureBlocking(false);
        //为ssc注册选择器
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        //创建处理器
        Handler handler = new Handler(1024);
        while (true){
            //等待请求，每次等待阻塞3s，超过3s后线程继续向下运行，如果换入0或者不传参数将一直阻塞
            if(selector.select(3000) == 0){
                System.out.println("等待请求超时.....");
                continue;
            }
            System.out.println("处理请求....");
            //获取待处理的SelectionKey
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()){
                SelectionKey next = keyIter.next();
                try {
                    //接收到连接请求时
                    if(next.isAcceptable()){
                        handler.handleAccept(next);
                    }
                    //读数据
                    if(next.isReadable()){
                        handler.handleRead(next);
                    }
                }catch (IOException e){
                    keyIter.remove();
                    continue;
                }
                //处理完后，从待处理的SelectionKey迭代器中移除当前所使用的key
                keyIter.remove();
            }
        }
    }

    private static class Handler {
        private int bufferSize = 1024;
        private String localCharset = "UTF-8";

        public Handler() {
        }

        public Handler(int bufferSize) {
            this(bufferSize, null);
        }

        public Handler(String localCharset) {
            this(-1, localCharset);
        }

        public Handler(int bufferSize, String localCharset) {
            if (bufferSize > 0) {
                this.bufferSize = bufferSize;
            }
            if (localCharset != null) {
                this.localCharset = localCharset;
            }
        }


        public void handleAccept(SelectionKey key) throws IOException {
            SocketChannel accept = ((ServerSocketChannel) key.channel()).accept();
            accept.configureBlocking(false);
            accept.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead(SelectionKey key) throws IOException{
            //获取channel
            SocketChannel sc = (SocketChannel) key.channel();
            //获取buffer并重置
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            //没有读到内容则关闭
            if(sc.read(buffer)== -1){
                sc.close();
            }else {
                //将buffer转换为读状态
                buffer.flip();
                //将buffer中接收到的值按localCharset格式编码后保存到receivedString
                String receivedString = Charset.forName(localCharset).newDecoder()
                        .decode(buffer).toString();
                System.out.println("从客户端收到消息:"+receivedString);
                sc.write(buffer);
                //关闭socket
                sc.close();
            }
        }
    }
}
