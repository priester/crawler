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
 * 中国日报
 * 
 * @author Administrator
 *
 */
public class PageProcessorWithZGRB implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWithZGRB.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		page.addTargetRequests(
				page.getHtml().links().regex("http://cnews.chinadaily.com.cn/node_1132703_[0-9]{1,2}.htm").all());
		//
		if (page.getUrl().regex("http://cnews.chinadaily.com.cn/node_1132703.htm").match()
				|| page.getUrl().regex("http://cnews.chinadaily.com.cn/node_1132703_[0-9]{1,2}.htm").match()) {
//			hid
			page.addTargetRequests(page.getHtml().xpath("//[@class='hid']").links()
					.regex("http://cnews.chinadaily.com.cn/....-../../content_[0-9]{8}.htm").all());
			
//			System.out.println(page.getHtml().xpath("//[@class='hid']").links()
//					.regex("http://cnews.chinadaily.com.cn/....-../../content_[0-9]{8}.htm").all().size());
		}
		if (page.getUrl().regex("http://cnews.chinadaily.com.cn/....-../../content_[0-9]{8}.htm").match()) {
			// System.out.println(page.getHtml());
			String title = page.getHtml().xpath("//[@class='hid arc']/h1/text()").toString();
			// System.out.println(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']/table/tbody/tr/td").toString()));
			String content = StripHtmlUtil
					.stripHtml(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='arcBox']").toString()));
			String url = page.getUrl().toString();
			String introduce = "";

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "中国日报", url, introduce);
				try {
					 JdbcProcess.save(news);
//					logger.info(news.toString());
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

		Spider.create(new PageProcessorWithZGRB()).addUrl("http://cnews.chinadaily.com.cn/node_1132703.htm").thread(1)
				.run();

	}
}