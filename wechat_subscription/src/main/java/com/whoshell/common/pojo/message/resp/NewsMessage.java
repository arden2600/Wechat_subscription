package com.whoshell.common.pojo.message.resp;

import java.util.List;

/**
 * NewsMessage : 公众号图文消息响应
 * 所有字段都是不能变的，应为将这些POJO转换为xml格式，微信公众号信息校验很严格
 * @author XianSky
 *
 */
public class NewsMessage extends BaseMessage {
	
	/*图文消息的个数，最多10条*/
	private int ArticleCount;
	
	/*多条图文消息，默认第一个article为大图*/
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
	
}
