package ch2.redo.java;

import java.util.Observable;

public class NewsStation extends Observable
{
	private String news;

	public NewsStation(String news)
	{
		super();
		this.news = news;
	}

	public void setNews(String news)
	{
		this.news = news;
	}

	public void updateNews(String newsUpdate)
	{
		if (!news.equals(newsUpdate))
		{
			news = newsUpdate;
			this.setChanged();
			this.notifyObservers();
		}
	}

	String getNews()
	{
		return news;
	}

}
