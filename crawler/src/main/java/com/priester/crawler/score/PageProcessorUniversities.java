package com.priester.crawler.score;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class PageProcessorUniversities implements PageProcessor {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PageProcessorUniversities.class);

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public void process(Page page) {

		// FileWriter fw = null;
		// BufferedWriter bw = null;
		// try {
		// fw = new
		// FileWriter("D:\\crawler\\crawler\\src\\main\\java\\com\\priester\\crawler\\score\\score",
		// true);

		// bw = new BufferedWriter(fw);

		List<String> specialtyScores = page.getHtml().xpath("//[@class='li-admissionLine']/tbody/tr").all();

		String provice = "广东省";
		String ArtSci = "sci";
		String universities = page.getHtml().xpath("//[@class='li-school-label']/span/text()").toString();

		for (String specialtyScoreDetail : specialtyScores) {
			// try {
			SpecialtyScoreDetail bean = new SpecialtyScoreDetail();
			bean.setProvince(provice);
			bean.setArtSci(ArtSci);
			bean.setUniversities(universities);

			String[] info = specialtyScoreDetail.split("\n");

			if (info.length >= 7) {

				bean.setSpecialtyName(getText(info[1]));
				bean.setYear(getText((info[2])));
				bean.setHighestScore(getText(info[3]));
				bean.setAverageScore(getText(info[4]));
				bean.setMinimumScore(getText(info[5]));
				bean.setType(getText(info[6]));
			}

			WriteTxt.addSpecialtyScoreDetail(bean);

			// bw.write(bean.toString());
			// System.out.println(bean);

			// } catch (Exception e) {
			// System.out.println(specialtyScoreDetail);

			// }
			// System.out.println(specialtyScoreDetail.split("\n")[1]);
			// }

			// } catch (IOException e) {
			//
			// e.printStackTrace();
			// } finally {
			// try {
			// fw.close();
			// bw.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

		}

		// System.out.println(specialtyScores.get(0));
	}

	public Site getSite() {
		return site;
	}

	public static void crawlers() {

		for (int i = 100; i < 3311; i++) {
			Spider.create(new PageProcessorUniversities()).addUrl(
					"https://gkcx.eol.cn/schoolhtm/specialty/" + i + "/10035/specialtyScoreDetail_2017_10011.htm")
					.thread(1).run();
		}
	}

	public static void main(String[] args) {
		crawlers();
	}

	private String getText(String s) {
		s= s.replaceAll("<td>", "").replaceAll("</td>", "").replaceAll("<p>", "").replaceAll("</p>", "");
		return s.trim();
	}
}