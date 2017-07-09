package inf1803.model;

import java.util.ArrayList;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class FeedChannel {

	//campos obrigatorios de canal
    final String title;				//nome do canal
    final String link;				//url para pagina html
    final String description;		//descrição do canasl
    //campos opcionais de canal
    final String pubDate;			//data da publicação
    final String category;			//inclui o canal em uma ou mais categorias
    final String urlrss;			//armazena a url que gera o feed - recebida pelo usuario
    
    final ArrayList<FeedItem> items = new ArrayList<FeedItem>();

	 public FeedChannel(String urlrss, String title, String link, String description , String pubDate, String category) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.category = category;
		this.urlrss = urlrss;
	}
	
	public ArrayList<FeedItem> getItems() {
         return items;
	}
	 
	 public String getTitle() {
		return title;
	}
	
	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getPubDate() {
		return pubDate;
	}


	public String getCategory() {
		return category;
	}

	public String getUrlrss() {
		return urlrss;
	}

	@Override
	    public String toString() {	
			return "FeedChannel ["
					+" urlrss= "+ urlrss
					+" | title= "+ title 
					+" | link= "+ link
					+" | description= "+ description 
					+" | pubDate= "+ pubDate 
					+" | category= "+ category 
				+"]";			
	    }
  

}
