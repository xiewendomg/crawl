package com.ssii.test;

import com.ssii.Utils.ProxyUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/1.
 */
public class ipTest {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost");
        //changeString("address=IP 端口 匿名度 类型 国家 省市 运营商 响应速度 最后验证时间 45.76.184.95 8498 透明 http 美国 3.549 秒 15分钟前 119.82.252.6 8188 透明 http 柬埔寨 0.427 秒 16分钟前 201.148.127.58 8502 透明 http 巴西 1.288 秒 16分钟前 36.66.64.74 8417 透明 印尼 2.792 秒 16分钟前 74.");
        //迭代器遍历集合，获取其元素
        Set<String> sets =  jedis.smembers("ips");
        Iterator<String> iterator = sets.iterator();
        Proxy proxy=null;
        Integer port =null;
        String ip=null;
        String it=null;
        while(iterator.hasNext()){
            it= iterator.next();
            ip=it.split(" ")[0];
            port=Integer.parseInt(it.split(" ")[1]);
            if(ProxyUtils.checkUrl(ip,port)){
            }else {
                System.out.println(it);
                jedis.srem("ips",it);
            }
        }
    }
}
