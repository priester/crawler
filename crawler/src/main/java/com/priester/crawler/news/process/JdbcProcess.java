package com.priester.crawler.news.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.priester.crawler.news.pojo.News;
import com.priester.crawler.news.utils.DBUtil;

public class JdbcProcess {

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

	public static List<String> readerTitle() throws Exception {
		List<String> title = new ArrayList<String>();
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT title FROM news WHERE id > 372 AND id <400";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			title.add(rs.getString(1));
		}
		conn.close();
		ps.close();
		return title;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(readerTitle());
	}
}
