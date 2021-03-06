package com.priester.crawler.news.process;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.priester.crawler.news.dto.JdbcProcess;
import com.priester.crawler.news.pojo.News;
import com.priester.crawler.news.utils.StripHtmlUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PageProcessorWith315 implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWith315.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		page.addTargetRequests(
				page.getHtml().links().regex("http://www.315online.com/tousu/redian/list_267_[0-9]{1,2}.html").all());
		

		if (page.getUrl().regex("http://www.315online.com/tousu/redian/(list_267_[0-9]{1,2}|index).html").match()) {
			// media-list
			page.addTargetRequests(page.getHtml().xpath("//[@class='media-list']").links()
					.regex("http://www.315online.com/tousu/redian/[0-9]{6}.html").all());
		}

		if (page.getUrl().regex("http://www.315online.com/tousu/redian/[0-9]{6}.html").match()) {
			String title = page.getHtml().xpath("//[@class='left_content']/h1/text()").toString();
			String content = StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='content']").toString());
			String url = page.getUrl().toString();

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "12315_热点投诉", url, "");
				try {
					JdbcProcess.save(news);
					// logger.info(news.getTitle());
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

	public static void Crawlers() {
		Spider.create(new PageProcessorWith315()).addUrl("http://www.315online.com/tousu/redian/index.html").thread(1)
				.run();
	}
	
	public static void main(String[] args) {
		Spider.create(new PageProcessorWith315()).addUrl("http://www.315online.com/tousu/redian/index.html").thread(5)
		.run();
	}
}