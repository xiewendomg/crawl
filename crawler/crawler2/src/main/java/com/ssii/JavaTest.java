package com.ssii;

/**
 * Created by Administrator on 2017/8/25.
 */
public class JavaTest {
    private  int id;
    private String name;
    private int age;



    private  JavaTest(){};
    private  static JavaTest buider(){
        return new JavaTest();
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }


    public static void main(String[] args) {
        JavaTest jt=new JavaTest();
        jt.buider().setAge(4);
    }
}
