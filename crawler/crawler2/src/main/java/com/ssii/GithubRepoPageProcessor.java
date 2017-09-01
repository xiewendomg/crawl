package com.ssii;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import org.apache.log4j.xml.DOMConfigurator;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class GithubRepoPageProcessor implements PageProcessor {
  /*  private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(1000);


    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }


    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        DOMConfigurator.configure("H:\\kj\\crawler\\crawler2\\src\\main\\lesources\\log4j.xml");
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
        System.out.print("==========");
    }*/
  // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
  private Site site = Site.me().setRetryTimes(3);
    private static int count =0;


    public Site getSite() {
        return site;
    }
    @ExtractBy(value = "//div[@class='BlogTags']/a/text()",notNull = true)
    private List<String> tags;
    public void process(Page page) {
        //判断链接是否符合http://www.cnblogs.com/任意个数字字母-/p/7个数字.html格式
        System.out.println(page.getUrl());
        if(!page.getUrl().regex("http://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{7}.html").match()){
            //加入满足条件的链接
            page.addTargetRequests(

                    page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all());
            System.out.println(page.getUrl());
        }else{
            //获取页面需要的内容
            System.out.println("抓取的内容："+
                    page.getHtml().xpath("//*[@id=\"Header1_HeaderTitle\"]/text()").get()
            );
            count ++;
        }
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("开始爬取...");
        startTime = System.currentTimeMillis();
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("101.101.101.101",8888,"username","password")));
        Spider.create(new GithubRepoPageProcessor()).setDownloader(httpClientDownloader).addPipeline(new JsonFilePipeline("D:\\webmagic\\log.txt")).addUrl("https://www.cnblogs.com/").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了"+count+"条记录");
    }
}
