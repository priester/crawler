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

public class PageProcessorWithLanzhouwang implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWithLanzhouwang.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		page.addTargetRequests(
				page.getHtml().links().regex("http://www.smelzh.gov.cn/qyxw/list.php\\?catid=32.*").all());

		if (page.getUrl().regex("http://www.smelzh.gov.cn/qyxw/list.php\\?catid=32").match()
				|| page.getUrl().regex("http://www.smelzh.gov.cn/qyxw/list.php\\?catid=32.*").match()) {
			page.addTargetRequests(
					page.getHtml().xpath("//[@class='catlist']").links().regex("http://www.smelzh.gov.cn/qyxw/show.php\\?itemid=[0-9]{5}").all());
		}

		if (page.getUrl().regex("http://www.smelzh.gov.cn/qyxw/show.php\\?itemid=[0-9]{5}").match()) {
			String title = page.getHtml().xpath("//[@class='title']/text()").toString();
			String content = StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='content']").toString());
			String url = page.getUrl().toString();
			String introduce = page.getHtml().xpath("//[@class='introduce']/text()").toString();

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "中国小企业兰州网", url, introduce);
				try {
//					JdbcProcess.save(news);
//					 logger.info(news.toString());
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
		Spider.create(new PageProcessorWithLanzhouwang()).addUrl("http://www.smelzh.gov.cn/qyxw/list.php?catid=32")
				.thread(1).run();
	}
}