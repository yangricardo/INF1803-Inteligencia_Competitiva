package inf1803.controller;

import java.util.ArrayList;

import inf1803.db.ChannelDao;
import inf1803.logic.RSSFeedReader;
import inf1803.model.FeedChannel;
import inf1803.model.FeedChannelBean;
import inf1803.model.FeedItem;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class RssReaderController {

	private static FeedChannel feed;
	private static RssReaderStatus status;
	
	public static ArrayList<FeedChannelBean> getFeeds(){
		ArrayList<FeedChannelBean> feedsBean = new ArrayList<FeedChannelBean>();
		ArrayList<FeedChannel> feeds = ChannelDao.getFeeds();
		for(FeedChannel f : feeds){
			feedsBean.add(new FeedChannelBean(f.getUrlrss(), f.getTitle(), f.getLink(), f.getDescription(), f.getPubDate(), f.getCategory()));
		}
		return feedsBean;		
	}
	
	public static void readRssFeed(String url){
		RssReaderController.feed = RSSFeedReader.ReadRSSFeed(url);
	}
	
	public static RssReaderStatus readNewRssFeed(String url){
		RssReaderController.feed = RSSFeedReader.ReadRSSFeed(url);
		if(RSSFeedReader.getRSSReaderStatus().equals(RssReaderStatus.FEEDCREATED))
			ChannelDao.insertChannel(feed);
		return RSSFeedReader.getRSSReaderStatus(); 
	}
	public static ArrayList<FeedItem> getArticles(String url){
		readRssFeed(url);
		return RssReaderController.feed.getItems();
	}
	
	public static FeedChannel getFeedInfo(){
		return RssReaderController.feed;
	}
	
	public static void deleteFeed(String urlrss){
		ChannelDao.deleteChannel(urlrss);
	}
		
	public static RssReaderStatus getRssReaderStatus(){
		return status;
	}
	
}
