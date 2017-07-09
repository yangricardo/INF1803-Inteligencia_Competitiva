package inf1803.controller;

import inf1803.logic.WebCrawler;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class WebCrawlerController {

	public static void search(String url, int maxPages){
		WebCrawler.search(url, maxPages);
	}
		
}
