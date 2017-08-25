package com.ssii;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2017/8/25.
 */
public class test1 implements PageProcessor{


    private static final String URL_LIST="";
    private static final String URL_POST="";
    @Override
    public void process(Page page) {

        if (page.getUrl().regex(URL_LIST).match()){
           // page.addTargetRequest(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex("dd").all());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    private  Site site=Site.me()
            .setCharset("UTF-8")
            .setSleepTime(3000)
            .setUserAgent(
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11"
            );
}
