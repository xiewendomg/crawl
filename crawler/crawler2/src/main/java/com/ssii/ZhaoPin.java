package com.ssii;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/29.
 */
public class ZhaoPin {

    private String title;
    private String pay;
    private String company;
    private String data;
    private String address;
    private String jobRequirements;
    private String welfare;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPay() {
        return pay;
    }

    @Override
    public String toString() {
        return "ZhaoPin{" +
                "title='" + title + '\'' +
                ", pay='" + pay + '\'' +
                ", company='" + company + '\'' +
                ", data='" + data + '\'' +
                ", address='" + address + '\'' +
                ", jobRequirements='" + jobRequirements + '\'' +
                ", welfare='" + welfare + '\'' +
                '}';
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public static void main(String[] args) {

        Mongo mongo = null;
        try {
            mongo = new Mongo();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB db=mongo.getDB("web2");
        DBCollection collection=db.getCollection("user");
        DBObject bson = new BasicDBObject().append("age",5);
        System.out.println("====================");
        collection.save(bson);
    }

}

