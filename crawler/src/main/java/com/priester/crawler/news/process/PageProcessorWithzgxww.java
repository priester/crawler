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
 * 中国经济网
 * @author Administrator
 *
 */
public class PageProcessorWithzgxww implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorWithzgxww.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

//		left		
//		http://www.ce.cn/cysc/sp/baoguantai/index_2.shtml
//		page.addTargetRequests(
//					page.getHtml().links().regex("http://www.ce.cn/cysc/sp/baoguantai/index_[0-9]{1,2}.shtml").all());
//		
//		http://www.ce.cn/cysc/sp/baoguantai/index_2.shtml
			
		if (page.getUrl().regex("http://www.ce.cn/cysc/sp/baoguantai/index.shtml").match()
				|| page.getUrl().regex("http://www.ce.cn/cysc/sp/baoguantai/index_[0-9]{1,2}.shtml").match()) {
			
//			http://www.ce.cn/cysc/sp/info/201803/20/t20180320_28543668.shtml
			
		page.addTargetRequests(
				page.getHtml().xpath("//[@class='left").links().regex("http://www.ce.cn/cysc/sp/info/[0-9]{6}/[0-9]{2}/t[0-9]{8}_[0-9]{6,8}.shtml").all());
		}

		if (page.getUrl().regex("http://www.ce.cn/cysc/sp/info/[0-9]{6}/[0-9]{2}/t[0-9]{8}_[0-9]{6,8}.shtml").match()) {
//			
			String title = page.getHtml().xpath("//[@class='neirong']/form/h1/text()").toString();
//			System.out.println(title);
//			System.out.println(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']/table/tbody/tr/td").toString()));
			String content = StripHtmlUtil.stripHtml(StripHtmlUtil.stripHtml(page.getHtml().xpath("//[@class='TRS_Editor']").toString()));
			String url = page.getUrl().toString();
			String introduce = "";

			if (StringUtils.isNotEmpty(title)) {
				News news = new News(title, content, "中国经济网", url, introduce);
				try {
					JdbcProcess.save(news);
//					 logger.info(news.toString());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("error page: " + url);
				}

			}else {
				logger.error("error page: " + url);
			}
		}
	}

	public Site getSite() {
		return site;
	}

	public static void Crawlers() {
	
//		Spider.create(new PageProcessorWithzgxww()).addUrl("http://www.ce.cn/cysc/sp/baoguantai/index.shtml")
//				.thread(1).run();
		for(int i = 2;i < 16;i++) {
			Spider.create(new PageProcessorWithzgxww()).addUrl("http://www.ce.cn/cysc/sp/baoguantai/index_"+i+".shtml")
			.thread(10).run();
		}
		
	}
	
	public static void main(String[] args) {
		Crawlers();
	}
}