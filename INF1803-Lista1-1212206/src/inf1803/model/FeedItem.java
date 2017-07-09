package inf1803.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class FeedItem {
	
	String title;			//titulo do item
	String link;			//url do item
	String description;		//descrição do item
	String author;			//email do autor
	String category;		//inclui o item em uma ou mais categorias
    String pubDate;			//data da publicação
    Hyperlink hyperlink;

   
	public FeedItem(String title, String link, String description, 
			String author, String category, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.author = author;
		this.category = category;
		this.pubDate = pubDate;
		this.hyperlink = new Hyperlink(link);
		this.hyperlink.setOnAction( new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		        try {
					java.awt.Desktop.getDesktop().browse(new URI(link));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }});
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
		this.hyperlink = new Hyperlink(link);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public Hyperlink getHyperlink(){
		return hyperlink;
	}
	

	@Override
	public String toString() {
		return "FeedItem ["
					+"title= "+ title 
					+" | link= "+ link
					+" | description= "+ description 
					+" | author= "+ author
					+" | category= "+ category
					+" | pubDate= "+ pubDate
				+"]";	
	}
}
