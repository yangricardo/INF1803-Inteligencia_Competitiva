package inf1803.logic;

import java.util.List;

import inf1803.model.FeedChannel;
import inf1803.model.FeedItem;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class ReadTest {
    public static void main(String[] args) {
    		FeedChannel feed = RSSFeedReader.ReadRSSFeed(
                            "http://feeds.feedburner.com/noticiasveja?format=xml");
            System.out.println(feed);
            List<FeedItem> listItem = feed.getItems();
            for (FeedItem article : listItem) {
                    System.out.println(article);
            }

    }
}
//http://g1.globo.com/dynamo/rss2.xml
//http://g1.globo.com/dynamo/ciencia-e-saude/rss2.xml
//http://oglobo.globo.com/rss.xml
//http://rss.home.uol.com.br/index.xml
//https://news.google.com.br/news?cf=all&hl=pt-BR&pz=1&ned=pt-BR_br&output=rss
//http://www.infowester.com/newsiw.rss
//http://feeds.feedburner.com/noticiasveja?format=xml