package com.ssii.ip;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * Created by Administrator on 2017/8/31.
 */
public class IPProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");


    public void process(Page page) {
        //获取div下中的所有连接地址
        //page.addTargetRequests(page.getHtml().xpath("//div[@class='paybox']").links().all());
        //获取具体的IP和端口
        //page.putField("address",page.getHtml().xpath("//div[@class='wlist']/ul/li[2]/allText()"));
        System.out.println(page.getHtml().xpath("//div[@id='list-content']"));
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.setProperty("selenuim_config", "D:/config.ini");//配置文件，我用的webmagic0.7.2,低版本可能不需要该文件，但也不支持phantomjs.
        Downloader downloader;//调用seleniumdownloader，这个downlaoder可以驱动selenium,phantomjs等方式下载，由config.ini配置
        downloader= new SeleniumDownloader();
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //119.57.112.130httpClientDownloader.setProxyProvider(new SimpleProxyProvider(Collections.unmodifiableList(MyJedis.getList())));
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("45.221.221.129",53281)));
        Spider.create(new IPProcessor()).addUrl("https://shopsearch.taobao.com/search?app=shopsearch&q=%E9%A5%BC%E5%B9%B2&qq-pf-to=pcqq.temporaryc2c").setDownloader(downloader).setDownloader(httpClientDownloader).addPipeline(new ConsolePipeline()).thread(3).run();
    }
}
