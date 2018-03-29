package com.priester.crawler.news.process;
import org.apache.commons.io.FileUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PageProcessorWith315 implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://www.315online.com/survey/[0-9]{6}.html").all());
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        
 
//        if(page.getHtml().links().regex("http://www.315online.com/tousu/redian/[0-9]{6}.html").match()) {
        	System.out.println(page.getHtml().links().regex("http://www.315online.com/survey/[0-9]{6}.html").all());
//        }
    	
    	page.putField("title", page.getHtml().xpath("//[@class='left_content']/h1/text()").toString());    	
    	page.putField("content",page.getHtml().xpath("//[@class='content']").toString());
    	
//    	FileUtils.writeStringToFile(file, data, encoding);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new PageProcessorWith315()).addUrl("http://www.315online.com/tousu/redian/382630.html").thread(1).run();
    }
}