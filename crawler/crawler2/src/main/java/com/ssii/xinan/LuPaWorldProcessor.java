package com.ssii.xinan;

import com.ssii.Utils.ListUtils;
import com.ssii.Utils.MyJedis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.Collections;

/**
 * @author code4crafter@gmail.com <br>
 */
public class LuPaWorldProcessor implements PageProcessor {
    public static final String URL_POST = "http://ehr\\.goodjobs\\.cn/show\\.php\\?jobID=\\w+";
    public static final String URL_FIRST = "^http://search\\.goodjobs\\.cn/index\\.php\\?boxft=b\\w+";
    int count=0;
    public static int pageNum = 1;
    public static final String URL_INDEX = "http://search\\.goodjobs\\.cn/index\\.php\\?boxft=b18\\&page=\\d+\\&cepage=solrpc1503993718l9Komg";
    private Site site = Site
            .me()
            .setDomain("blog.sina.com.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {


        if (page.getUrl().regex("^http://user\\.goodjobs\\.cn/$").match()){
            page.addTargetRequests(page.getHtml().links().regex(URL_FIRST).all());
        }
         else if(page.getUrl().regex(URL_FIRST).match()){
            //获取URL_INDEX页面列表中的url地址，并加入爬虫
            page.addTargetRequests(page.getHtml().xpath("//form[@id=\"jobApp\"]").links().regex(URL_POST).all());
            //新增爬取主页面（URL_INDEX类似）
            page.addTargetRequests(ListUtils.changeList(page.getHtml().xpath("//div[@class=\"p_in\"]/ul/li/a/@href").all()));

        }else{

             System.out.print("=============================="+count++);
           /* 进入http://ehr.goodjobs.cn/show.php?jobID=（w+）页面
            *由于新安网job有两种不同的页面显示，下面将两种不同得数据做统一爬取
            */
            //获取待遇
           /* page.putField("pay", page.getHtml().xpath("//div[@class='fr zwx']/strong/text()"));
            page.putField("pay2", page.getHtml().xpath("//div[@class='op']/strong/text()"));
            //获取公司名
            page.putField("company", page.getHtml().xpath("//p[@class='cname pt10']/a/text()"));
            page.putField("company2", page.getHtml().xpath("//p[@class='cname']/a/text()"));
            //获取日期
            page.putField("data", page.getHtml().xpath("//div[@class='t1 clearfix']/allText()"));
            //获取标题
            page.putField("title", page.getHtml().xpath("//h1[@class='fl fz22 w500']/text()"));
            page.putField("title2", page.getHtml().xpath("//h1[@class='cn']/h1/text()"));
            //获取公司地址
            page.putField("address", page.getHtml().xpath("//div[@class='comadress clearfix']/text()"));
            //获取岗位要求
            page.putField("jobRequirements", page.getHtml().xpath("//p[@class='duol mt20 lh28 pb10']/text()"));
            page.putField("jobRequirements2", page.getHtml().xpath("//p[@class='duol mt20']/text()"));
            //获取福利
            page.putField("welfare", page.getHtml().xpath("//p[@class='t2 mt10']/allText()"));*/
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //119.57.112.130httpClientDownloader.setProxyProvider(new SimpleProxyProvider(Collections.unmodifiableList(MyJedis.getList())));
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("120.24.208.42",9999)));
        //httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("101.101.101.101",8888)));
        Spider.create(new LuPaWorldProcessor()).setDownloader(httpClientDownloader).addUrl("http://www.baidu.com/").addPipeline(new ConsolePipeline())
                .thread(10).run();
    }
}