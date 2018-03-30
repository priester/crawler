package com.priester.crawler.news.utils;

public class StripHtmlUtil {

	public static String stripHtml(String content) {

		content = content.replaceAll("<p .*?>", "\r\n");

		content = content.replaceAll("<br\\s*/?>", "\r\n");

		content = content.replaceAll("\\<.*?>", "");

		content = content.replaceAll("&nbsp;", "");
		
		content = content.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
		// content = HTMLDecoder.decode(content);
		content = content.replaceAll("  　　", "");
		content = content.replaceAll(" 　　", "");
		return content;
	}
}
