package com.grouphadel.dlsaa.storage;

import com.grouphadel.dlsaa.models.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class NewsArticleDAO extends BaseDAO {
	public List<NewsArticle> getAll() {
		List<NewsArticle> articles = new ArrayList<NewsArticle>();
		
		for (int i = 0; i < 5; i++) {
			articles.add(new NewsArticle());
		}
		
		return articles;
	}
}
