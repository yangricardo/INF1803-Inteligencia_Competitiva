package inf1803.view;

import java.util.ArrayList;
import java.util.Optional;
import inf1803.Main;
import inf1803.controller.RssReaderController;
import inf1803.controller.RssReaderStatus;
import inf1803.model.FeedChannelBean;
import inf1803.model.FeedItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class RootLayoutController {
	
	@FXML
	private TableView<FeedChannelBean> rssFeedsTableView;
	@FXML
	private TableColumn<FeedChannelBean, String> rssFeedsColumn;
	@FXML
	private TableColumn<FeedChannelBean, String> rssFeedsUrlColumn;
	@FXML
	private TableView<FeedItem> rssArticleTableView;
	@FXML
	private TableColumn<FeedItem, String> rssArticleTitleColumn;
	@FXML
	private TableColumn<FeedItem, String> rssArticlePubDateColumn;
	@FXML
	private TableColumn<FeedItem, String> rssArticleAuthorColumn;
	@FXML
	private TableColumn<FeedItem, String> rssArticleCategoryColumn;
	@FXML
	private TableColumn<FeedItem, Hyperlink> rssArticleLinkColumn;
	@FXML
	private TextArea rssArticleDescriptionText;
	
	private Main main;
	
	public void setMain(Main main){
		this.main = main;	
	}
	
	@FXML
	private void initialize(){
		rssFeedsTableView.getItems().addAll(FXCollections.observableArrayList(RssReaderController.getFeeds()));
		rssFeedsColumn.setCellValueFactory(new PropertyValueFactory<>("title") );
		rssFeedsUrlColumn.setCellValueFactory(new PropertyValueFactory<>("urlrss") );
		rssFeedsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showFeedArticles(newValue));
	}
	
	 private void showFeedArticles(FeedChannelBean feed) {
		 rssArticleTableView.getItems().clear();
		 rssArticleTableView.getItems().addAll(
				 RssReaderController.getArticles(feed.getUrlrss())
				 );		 
		 rssArticleTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title") );
		 rssArticlePubDateColumn.setCellValueFactory(new PropertyValueFactory<>("pubDate") );
		 rssArticleAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author") );
		 rssArticleCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category") );
		 rssArticleLinkColumn.setCellFactory(new HyperlinkCell());
		 rssArticleLinkColumn.setCellValueFactory(new PropertyValueFactory<>("hyperlink") );
		 rssArticleTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showArticleDetails(newValue));
		 
	}

	private void showArticleDetails(FeedItem item) {
		rssArticleDescriptionText.setText(
				"Title: "+item.getTitle()+"\n\n"
				+"Author: "+item.getAuthor()+"\n\n"
				+"Publication Date: "+item.getPubDate()+"\n\n"
				+"Category: "+item.getCategory()+"\n\n"
				+"Link: "+item.getLink()+"\n\n"
				+"Description: "+item.getDescription()
				);
		rssArticleDescriptionText.setWrapText(true);
	}

	@FXML
	 private void handleInsertSave() {
		 System.out.println("Inserting Feed");
		 TextInputDialog inputDialog = new TextInputDialog("http://");
		 inputDialog.setTitle("Insert Feed");
		 inputDialog.setContentText("Put the URL Feed:");
		 
		 Optional<String> url = inputDialog.showAndWait();
		 if(url.isPresent()){
			 System.out.println("url:"+url.get());
			 if(RssReaderController.readNewRssFeed(url.get()).equals(RssReaderStatus.NOTRSSXML)){
				 Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("Error on Inserting Feed");
				 alert.setHeaderText("The URL is Malformed or it's not a link to an rss.xml");
				 alert.showAndWait();
			 } else{
				 Alert alert = new Alert(AlertType.INFORMATION);
				 alert.setTitle("The Feed was Inserted");
				 alert.setHeaderText("The Feed search is now available!");
				 alert.showAndWait();
				 System.out.println(RssReaderController.getFeedInfo().toString());
				 rssFeedsTableView.getItems().clear();
				 rssFeedsTableView.getItems().addAll(FXCollections.observableArrayList(RssReaderController.getFeeds()));
			 }
		 }
		 
	 }
	
	@FXML
	 private void handleDeleteSave() {
		 System.out.println("Deleting Feed");
		 ArrayList<FeedChannelBean> availFeeds = RssReaderController.getFeeds();
		 ArrayList<String> options = new ArrayList<String>();
		 for(FeedChannelBean f : availFeeds){
			 options.add(f.getTitle()+" - "+f.getUrlrss());
		 }
		 
		 ChoiceDialog<String> dialog = new ChoiceDialog<>("",options);
		 dialog.setTitle("Delete Feed");
		 dialog.setContentText("Choose de Feed to Delete: ");
		 Optional<String> response = dialog.showAndWait();

		 if(response.isPresent()){
			 String choice = response.get().substring(response.get().indexOf("http"), response.get().length());
			 System.out.println(response.get() +" ====> "+ response.get().substring(response.get().indexOf("http"), response.get().length()));
			 RssReaderController.deleteFeed(choice);
			 rssFeedsTableView.getItems().clear();
			 rssArticleTableView.getItems().clear();
			 rssFeedsTableView.getItems().addAll(FXCollections.observableArrayList(RssReaderController.getFeeds()));
		
		 }
		 
	 }
	
	
	@FXML
	private void handleExit(){
		System.exit(0);
	}
	
	
	@FXML 
	private void handleNewCrawler(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/WebCrawlerDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Web Crawler");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(main.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.showAndWait();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleAbout(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setWidth(1000);
		alert.setHeaderText("INF1803 - Lista 1 de Inteligencia Competitiva");
		alert.setContentText(
				"Aluno: Yang Ricardo Barcellos Miranda"
				+"\r\nMatrícula: 1212206"
				+"\r\nInstruções:\r\n"
				+"*Para visualizar os artigos de um feed, clique em uma linha da tabela 'RSS Feeds', os artigos mais recentes serão exibidos na tabela 'Articles'\r\n"
				+"*Para visualizar os metadados de um artigo, clique em uma linha da tabela 'Articles', os metadados, se disponiveis, aparecerão no espaço em branco ao lado\r\n"
				+"*Para abrir um artigo no navegador padrão, clique no link destacado em azul\r\n"
				+"*Para inserir um novo feed rss vá no Menu 'Rss Reader', opção 'Insert Feed'\r\n"
				+"**Insert Feed: insira um endereço http:\\ ou https:\\ para o bom funcionamento do rss\r\n"
				+"*Para remover um feed rss cadastrado vá no Menu 'Rss Reader', opção 'Delete Feed'\r\n"
				+"**Delete Feed: Escolha uma das opções contidas na lista fornecida\r\n"
				+"*Para executar o WebCrawler vá no menu 'Web Crawler', opção 'New Crawler'\r\n"
				+"**New Crawler: Insita um endereço http:\\ ou http:\\ e um valor para max pages maior ou igual a 10\r\n"
				+"**Ao completar o processo de crawling, duas janelas de salvar são exibidas, a primeira para o resultado e o segundo a lista de links"
				+"\r\n\r\n"
				);
		
		alert.show();
		
	}

}
