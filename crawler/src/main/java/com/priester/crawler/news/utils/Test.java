package com.priester.crawler.news.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class Test {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		System.out.println(HttpClientUtil.httpGet("http://www.p5w.net/news/pgt/"));
	}
}
