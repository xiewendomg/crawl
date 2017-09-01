package com.ssii;

import java.util.*;

/**
 * Created by Administrator on 2017/8/25.
 */
public class JavaTest {

    public void say() {
        int[] array = new int[]{1, 3, 2, 2, 2,3,1};
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (int val : array) {
            if (map.containsKey(val)) {
                map.put(val, map.get(val) + 1);
            } else {
                map.put(val, 1);
            }
        }
        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        for(Map.Entry<Integer,Integer> mapping:list){
            System.out.println(mapping.getKey()+":======"+mapping.getValue());}

        Collections.sort(list,new Comparator<Map.Entry<Integer,Integer>>() {
            //升序排序
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        int [] ab=new int[array.length];
        int num=0;
        for(Map.Entry<Integer,Integer> mapping:list){
            System.out.println(mapping.getKey()+":+++++++++"+mapping.getValue());
            for (int i=num;i<mapping.getValue()+num;i++){
                array[i]=mapping.getKey();
               System.out.println("array["+i+"]="+array[i]);
               System.out.println("i="+i);
            }
            num=num+mapping.getValue();
        }
        System.out.println(Arrays.toString(array));
    }

    public static void main(String[] args) {
        JavaTest jt= new JavaTest();
        jt.say();
      /*  Map<Integer,Integer> hm=new HashMap<Integer,Integer>();
        hm.put(1,2);
        hm.put(3,4);
        hm.put(5,6);
        hm.put(7,8);
        hm.put(9,0);
        for(int key:hm.keySet()){
            System.out.println("key="+key
            +"value="+hm.get(key));
        }
        int [] array=new int[]{1,2,3,2,2,3};*/
       // System.out.println(array[1,2]);
    }
}
