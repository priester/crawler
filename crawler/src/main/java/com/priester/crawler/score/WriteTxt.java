package com.priester.crawler.score;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteTxt {
	public static List<SpecialtyScoreDetail> beans;

	public static List<SpecialtyScoreDetail> getBeans() {
		if (beans == null) {
			beans = new ArrayList<SpecialtyScoreDetail>();
		}
		return beans;

	}

	public static synchronized void addSpecialtyScoreDetail(SpecialtyScoreDetail bean) {
		getBeans().add(bean);
		if (getBeans().size() == 10) {
			try {
				write(getBeans());
			} catch (IOException e) {
				e.printStackTrace();
			}
			beans = null;
		}
	}

	public static void write(List<SpecialtyScoreDetail> beans) throws IOException {
		FileWriter fw = null;
		BufferedWriter bw = null;

		fw = new FileWriter("D:\\crawler\\crawler\\src\\main\\java\\com\\priester\\crawler\\score\\score", true);
		bw = new BufferedWriter(fw);

		for (SpecialtyScoreDetail bean : beans) {
			bw.write(bean.toString());
			bw.newLine();
		}
		bw.close();
	}
}
