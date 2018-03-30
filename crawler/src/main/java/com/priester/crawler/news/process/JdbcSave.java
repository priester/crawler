package com.priester.crawler.news.process;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.priester.crawler.news.pojo.News;
import com.priester.crawler.news.utils.DBUtil;

public class JdbcSave {

	public static boolean save(News news) throws Exception {
		
		Connection conn = DBUtil.getConnection();
		String sql = "insert into news (title,content,source,url,introduce) value(?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, news.getTitle());
		ps.setString(2, news.getContent());
		ps.setString(3, news.getSource());
		ps.setString(4, news.getUrl());
		ps.setString(5, news.getIntroduce());
		ps.executeUpdate();

		conn.close();
		ps.close();
		
		return true;
	}
}
