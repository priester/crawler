package com.priester.crawler.news.process;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.priester.crawler.news.pojo.News;
import com.priester.crawler.news.utils.StripHtmlUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PageProcessorWithLanchang implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWithLanchang.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		page.addTargetRequests(
				page.getHtml().links().regex("http://www.ncnews.com.cn/hd/ncyl/bgl/[0-9]{6}/t[0-9]{8}_[0-9]{6}.html").all());


//		http://www.ncnews.com.cn/hd/ncyl/bgl/201606/t20160612_198790.html
		if (page.getUrl().regex("http://www.ncnews.com.cn/hd/ncyl/bgl/[0-9]{6}/t[0-9]{8}_[0-9]{6}.html").match()) {
			System.out.println(page.getHtml());
			String title = page.getHtml().xpath("//[@class='read']/h3/text()").toString();
//			System.out.println(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']/table/tbody/tr/td").toString()));
			String content = StripHtmlUtil.stripHtml(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']/table/tbody/tr/td").toString()));
			String url = page.getUrl().toString();
			String introduce = "";

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "南昌新闻网_曝光栏", url, introduce);
				try {
//					JdbcProcess.save(news);
					 logger.info(news.toString());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("error page: " + url);
				}

			}
		}
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		for(int i = 1;i<6;i++) { 
		Spider.create(new PageProcessorWithLanchang()).addUrl("http://www.ncnews.com.cn/hd/ncyl/bgl/")
				.thread(1).run();
		}
	}
}