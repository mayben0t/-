package httpClientD;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String url = "http://news.baidu.com";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = httpClient.execute(httpGet);

        HttpEntity entity = res.getEntity();
        byte[] bytes = EntityUtils.toByteArray(entity);
        String result = new String(bytes,"utf8");
        System.out.println(result);
    }
}
