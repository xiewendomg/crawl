package com.ssii.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public class ListUtils {
    public  static List<String> changeList(List<String> list){
        for(String ob:list){
            ob="http://search.goodjobs.cn"+ob;
            System.out.println(ob);
        }
        return list;

    }
}
