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

/**
 * 中国化工报
 * @author Administrator
 *
 */
public class PageProcessorWithZGHGB implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWithZGHGB.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		
		page.addTargetRequests(
					page.getHtml().links().regex("http://www.ccin.com.cn/templet/ccin/ShowClass.jsp.id=6.pn=[0-9]{1,2}").all());
		
		page.addTargetRequests(
				page.getHtml().links().regex("http://www.ccin.com.cn/ccin/news/[0-9]{4}/[0-9]{2}/[0-9]{2}/[0-9]{6}.shtml").all());

		
		System.out.println(
				page.getHtml().links().regex("http://www.ccin.com.cn/templet/ccin/ShowClass.jsp.id=6.pn=[0-9]{1,2}"));

		if (page.getUrl().regex("http://www.ccin.com.cn/ccin/news/[0-9]{4}/[0-9]{2}/[0-9]{2}/[0-9]{6}.shtml").match()) {
//			System.out.println(page.getHtml());
			String title = page.getHtml().xpath("//[@class='news_con_tit']/h2/text()").toString();
//			System.out.println(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']/table/tbody/tr/td").toString()));
			String content = StripHtmlUtil.stripHtml(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='con']").toString()));
			String url = page.getUrl().toString();
			String introduce = "";

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "中国化工报", url, introduce);
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
	
		Spider.create(new PageProcessorWithZGHGB()).addUrl("http://www.ccin.com.cn/templet/ccin/ShowClass.jsp?id=6&pn=1")
				.thread(1).run();
		
	}
}