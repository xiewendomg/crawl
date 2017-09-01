package com.ssii;

import org.apache.commons.io.IOUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Created by Administrator on 2017/8/25.
 */
public class test1 implements PageProcessor{


    @Override
    public void process(Page page) {
        // 创建代理服务器
        InetSocketAddress addr=null;
        addr=new InetSocketAddress("61.135.149.177",8888);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
        //Proxy类代理方法
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
            URLConnection conn = url.openConnection(proxy);
            InputStream in = conn.getInputStream();
            String s = IOUtils.toString(in);
            //System.out.println(s);
            if(s.indexOf("百度")>0){
                System.out.println("ok");}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public Site getSite() {
        return null;
    }

    public static void main(String[] args) {




    }
}
