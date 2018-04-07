package com.priester.crawler.news.process;

public class Test {
	public static void main(String[] args) {
//		System.out.println("http://www.ncnews.com.cn/hd/ncyl/bgl/201606/t20160612_198790.html".matches("http://www.ncnews.com.cn/hd/ncyl/bgl/[0-9]{6}/t[0-9]{8}_[0-9]{6}.html"));
//		System.out.println("   ".replace(" ", "").length());
//		System.out.println();
		PageProcessorWithLanchang.Crawlers();
		PageProcessorWithLanzhouwang.Crawlers();
		PageProcessorWithWYCJ.Crawlers();
		PageProcessorWithZGHGB.Crawlers();
		PageProcessorWithZGRB.Crawlers();
		PageProcessorWithzgxww.Crawlers();	
	}
}
