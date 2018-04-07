package com.priester.crawler.news.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.priester.crawler.news.pojo.News;
import com.priester.crawler.news.process.PageProcessorWith315;
import com.priester.crawler.news.utils.DBUtil;

public class JdbcProcess {
	private static final Logger logger = LoggerFactory.getLogger(JdbcProcess.class);
	
	public static boolean save(News news)  {
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into negative_news (title,content,source,url,introduce) value(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getContent());
			ps.setString(3, news.getSource());
			ps.setString(4, news.getUrl());
			ps.setString(5, news.getIntroduce());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error(e.toString());
		}finally {
			try {
				conn.close();
				ps.close();
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		
		}

		return true;
	}

	public static List<String> readerTitle() throws Exception {
		List<String> title = new ArrayList<String>();
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT title FROM negative_news WHERE id > 372 AND id <400";
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
