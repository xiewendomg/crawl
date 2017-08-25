package com.ssii;

import org.apache.log4j.xml.DOMConfigurator;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

/**
 * Created by Administrator on 2017/8/25.
 */
public class Pip {
    public static void main(String[] args) {
        DOMConfigurator.configure("H:\\kj\\crawler\\crawler2\\src\\main\\lesources\\log4j.xml");
        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
        Spider.create(new GithubRepoPageProcessor()).addPipeline(new JsonFilePipeline("D:\\webmagic\\")).addUrl("https://www.cnblogs.com/").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+"条记录");
    }
}
