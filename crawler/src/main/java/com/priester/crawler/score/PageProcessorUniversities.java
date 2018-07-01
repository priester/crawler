package com.priester.crawler.score;

import java.util.List;

import javax.xml.bind.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PageProcessorUniversities implements PageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(PageProcessorUniversities.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {
	
		List<String> specialtyScores = page.getHtml().xpath("//[@class='li-admissionLine']/tbody/tr").all();

		String provice = "10035";
		String ArtSci = "sci";
		String universities = page.getHtml().xpath("//[@class='li-school-label']/span/text()").toString();

		for (String specialtyScoreDetail : specialtyScores) {
			try {
				SpecialtyScoreDetail bean = new SpecialtyScoreDetail();
				bean.setProvince(provice);
				bean.setArtSci(ArtSci);
				bean.setUniversities(universities);

				String[] info = specialtyScoreDetail.split("\n");

				bean.setSpecialtyName(getText(info[1]));
				bean.setYear(getText((info[2])));
				bean.setHighestScore(getText(info[3]));
				bean.setAverageScore(getText(info[4]));
				bean.setMinimumScore(getText(info[5]));
				bean.setType(getText(info[6]));
				System.out.println(bean);

			} catch (Exception e) {
				System.out.println(specialtyScoreDetail);
			}
			// System.out.println(specialtyScoreDetail.split("\n")[1]);
		}
//		System.out.println(specialtyScores.get(0));
	}

	public Site getSite() {
		return site;
	}

	public static void crawlers() {

		for (int i = 100; i < 200; i++) {
			Spider.create(new PageProcessorUniversities()).addUrl(
					"https://gkcx.eol.cn/schoolhtm/specialty/" + i + "/10035/specialtyScoreDetail_2017_10021.htm")
					.thread(1).run();
		}
	}

	public static void main(String[] args) {
		crawlers();
	}
	
	private String getText(String s) {
		return s.replaceAll("<td>", "").replaceAll("</td>", "").replaceAll("<p>", "").replaceAll("</p>", "");
	}
}