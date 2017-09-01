package com.ssii;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 */
public class SinaBlogProcessor implements PageProcessor {

    public static final String URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";

    public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

    private Site site = Site
            .me()
            .setDomain("blog.sina.com.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        //System.out.print(page.getUrl()+"======================");
        //抓取的url是否与URL_LIST匹配
        if (page.getUrl().regex(URL_LIST).match()) {
            /*addTargetRequests,添加url 去抓取
            * page.getHtml(),获取当前页内容，返回html对象，html类继承AbstractSelectable类
            *xpath（“”）使用xpath选择器。爬取列表返回所有links（）
            *
            * */

            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
           System.out.print(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST));
            //文章页
        } else {
            //将爬取的信息封装的ResultItems对象中
            page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
            page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
            page.putField("date",
            page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SinaBlogProcessor()).addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
                .run();
    }
}