package com.ssii.Utils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.proxy.Proxy;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/31.
 */
public class PatternUtils  {
    public static List<String> changeString(String string) {
        Pattern p = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s+(\\d+)");
        Matcher m = p.matcher(string);
        String ip=null;
        List<String> list=new ArrayList<String>();
        while (m.find()) {
             ip=m.group(1);
             list.add(m.group(1)+" "+m.group(2));
        }
        return list;
    }


}
