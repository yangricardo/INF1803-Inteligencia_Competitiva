package inf1803.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class FeedChannelBean {

	private StringProperty title;
	private StringProperty link;
	private StringProperty description;
	private StringProperty pubDate;
	private StringProperty category;
	private StringProperty urlrss;
	
	public FeedChannelBean(String urlrss, String title, String link, String description , String pubDate, String category) {
		this.urlrss = new SimpleStringProperty(urlrss);
		this.title = new SimpleStringProperty(title);
		this.link = new SimpleStringProperty(link);
		this.description = new SimpleStringProperty(description);
		this.pubDate = new SimpleStringProperty(pubDate);
		this.category = new SimpleStringProperty(category);
	}

	public StringProperty getTitleProperty() {
		return title;
	}

	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public StringProperty getLinkProperty() {
		return link;
	}

	public void setLink(StringProperty link) {
		this.link = link;
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}

	public StringProperty getPubDateProperty() {
		return pubDate;
	}

	public void setPubDate(StringProperty pubDate) {
		this.pubDate = pubDate;
	}

	public StringProperty getCategoryProperty() {
		return category;
	}

	public void setCategory(StringProperty category) {
		this.category = category;
	}

	public StringProperty getUrlrssProperty() {
		return urlrss;
	}

	public void setUrlrss(StringProperty urlrss) {
		this.urlrss = urlrss;
	}

	public String getTitle() {
		return title.get();
	}

	public String getLink() {
		return link.get();
	}

	public String getDescription() {
		return description.get();
	}

	public String getPubDate() {
		return pubDate.get();
	}

	public String getCategory() {
		return category.get();
	}

	public String getUrlrss() {
		return urlrss.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public void setLink(String link) {
		this.link.set(link);
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public void setPubDate(String pubDate) {
		this.pubDate.set(pubDate);
	}

	public void setCategory(String category) {
		this.category.set(category);
	}

	public void setUrlrss(String urlrss) {
		this.urlrss.set(urlrss);
	}

	
	
}
