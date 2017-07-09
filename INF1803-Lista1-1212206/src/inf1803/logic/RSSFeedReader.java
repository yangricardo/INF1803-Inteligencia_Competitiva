package inf1803.logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import inf1803.controller.RssReaderStatus;
import inf1803.model.FeedChannel;
import inf1803.model.FeedItem;
import inf1803.utils.Util;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class RSSFeedReader {

	private static URL url;
	private static RssReaderStatus status;

	public static FeedChannel ReadRSSFeed(String feedUrl) {
		try {
			RSSFeedReader.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			status = RssReaderStatus.NOTRSSXML;
			System.out.println(status);
			Util.printError("Erro ao ler XML", e);
			return null;
		}
		return readRSS();
	}
	
    private static String getRssTagData(XMLEvent rssEvent, XMLEventReader rssReader)
            throws XMLStreamException {
    String result = "";
    rssEvent = rssReader.nextEvent();
    if (rssEvent instanceof Characters) {
            result = rssEvent.asCharacters().getData();
    }
    return result;
}
	
	private static FeedChannel readRSS(){
		FeedChannel rssChannel = null;
		try {
			boolean isChannel = true;

			String title = "";
			String link = "";
			String description = "";
			String pubDate = "";
			String category = "";   
			String author = "";	
			//Cria fabrica para ler input xml
			XMLInputFactory factory = XMLInputFactory.newInstance();
			//Cria instancia do leitor xml
			XMLEventReader rssReader = factory.createXMLEventReader(url.openStream());
			while(rssReader.hasNext()){//enquanto há elementos no arquivo rss.xml
				XMLEvent rssEvent = rssReader.nextEvent();
				if (rssEvent.isStartElement()) {
					String localPart = rssEvent.asStartElement().getName().getLocalPart();
					switch(localPart){
						case "title":
							title = getRssTagData(rssEvent, rssReader);
							break;
						case "link":
							if(isChannel && link.equals(""))
								link = getRssTagData(rssEvent, rssReader);
							else if (isChannel == false)
								link = getRssTagData(rssEvent, rssReader);
							break;
						case "description":
							description = getRssTagData(rssEvent, rssReader);
							break;
						case "pubDate": case "lastBuildDate":
							if(pubDate.equals(""));
								pubDate = getRssTagData(rssEvent, rssReader);
							break;
						case "category":
							category = getRssTagData(rssEvent, rssReader);
							break;
						case "author": case "creator":
							author = getRssTagData(rssEvent, rssReader);
							break;
						case "item":
							if(isChannel){
								// na primeira iteração le os campos de CHANNEL
								// apos a leitura, cria instancia de FeedChannel com os dados lidos
								isChannel = false;
								rssChannel = new FeedChannel(url.toString(),title, link, description,
										pubDate, category);
							}
							//avança para o proximo item
							rssEvent = rssReader.nextEvent();
							break;
					}
				} else if(rssEvent.isEndElement() 
						&& rssEvent.asEndElement().getName().getLocalPart().equals("item") ){
					//se for o final de um elemento item
					//cria a instancia de item
					FeedItem rssItem = new FeedItem(title, link, description, 
							author, category, pubDate);
					//adiciona o rssItem no rssChannel
					rssChannel.getItems().add(rssItem);
					//avança ao proximo item
					rssEvent = rssReader.nextEvent();
					continue;					
				}
			
			}
		} catch (XMLStreamException | IOException e) {
			status = RssReaderStatus.NOTRSSXML;
			System.out.println(status);
			Util.printError("Erro ao ler XML", e);
			return null;
		}		
		status = RssReaderStatus.FEEDCREATED;
		return rssChannel;		
	}	
	
	public static RssReaderStatus getRSSReaderStatus(){
		return status;
	}
	
}
