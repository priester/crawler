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

/**
 * 网易财经
 * 
 * @author Administrator
 *
 */
public class PageProcessorWithWYCJ implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWithWYCJ.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		page.addTargetRequests(
				page.getHtml().links().regex("http://money.163.com/special/baoguagntai_[0-9]{2}/").all());
		//
		if (page.getUrl().regex("http://money.163.com/special/baoguagntai_[0-9]{2}/").match()
				|| page.getUrl().regex("http://money.163.com/special/baoguagntai/").match()) {
			page.addTargetRequests(page.getHtml().xpath("//[@class='col_l']").links()
					.regex("http://money.163.com/[0-9]{2}/[0-9]{4}/[0-9]{2}/.*.html").all());
		}

		String url = page.getUrl().toString();
		if (page.getUrl().regex("http://money.163.com/[0-9]{2}/[0-9]{4}/[0-9]{2}/.*.html").match()) {
			// System.out.println(page.getHtml());
			String title = page.getHtml().xpath("//[@class='post_content_main']/h1/text()").toString();
			// System.out.println(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']/table/tbody/tr/td").toString()));
			String content = StripHtmlUtil
					.stripHtml(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='post_text']").toString()));

			String introduce = "";

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "网易财经", url, introduce);
				try {
					JdbcProcess.save(news);
					// logger.info(news.toString());
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

		Spider.create(new PageProcessorWithWYCJ()).addUrl("http://money.163.com/special/baoguagntai/").thread(10).run();

	}
	
	public static void main(String[] args) {
		Crawlers();
		
	}
}