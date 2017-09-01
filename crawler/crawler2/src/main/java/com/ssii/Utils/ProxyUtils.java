package com.ssii.Utils;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/8/31.
 */
public class ProxyUtils {

    public static boolean checkUrl(String hostname,int port) {
        // 创建代理服务器
        InetSocketAddress addr = null;
        addr = new InetSocketAddress(hostname, port);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
        //Proxy类代理方法
        URL url = null;
        try {
            url = new URL("http://www.baidu.com/");
            URLConnection conn = url.openConnection(proxy);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            InputStream in = conn.getInputStream();
            String s = IOUtils.toString(in);
            //System.out.println(s);
            if (s.indexOf("百度") > 0) {
                System.out.println("OK");
               return true;
            }
        } catch(Exception e){
            return false;
        }
        return false;
    }
}

