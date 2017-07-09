package inf1803.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import inf1803.logic.WebCrawler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class WebCrawlerDialogController {

	@FXML
	private TextField urlText;
	@FXML
	private TextField maxPages;
		
	private Stage dialogStage;
	
	public void setDialogStage (Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void initialize(){
		urlText.setText("http://");
		urlText.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	        	if (urlText.getText().matches("^((https|http)://)([0-9a-z.-]+).([a-z.]{2,6})([/[a-z0-9].-]*)*/?$")) 
	        		urlText.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
	            else
	            	urlText.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
	        }
	    });
		maxPages.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	        	if (maxPages.getText().matches("^[1-9][0-9]+$")) 
	        		maxPages.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
	            else
	            	maxPages.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
	        }
	    });
		
	}
	
	
	@FXML
	private void handleExecuteWebCrawler(){
		System.out.println(urlText.getText() +" | "+ maxPages.getText());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("WebCrawler");
		alert.setHeaderText("O processo de Web Crawler pode demorar alguns minutos...");
		alert.showAndWait();
		String result = WebCrawler.search(urlText.getText(),
				new Integer(maxPages.getText()));
		FileChooser filechooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"TXT Files", ".txt");
		filechooser.getExtensionFilters().add(extFilter);
		filechooser.setInitialFileName(
				"WebCrawler Result "+
				new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(
						new Date() ));
		File fileResult = filechooser.showSaveDialog(dialogStage);
		if(fileResult!=null){
			try{
				FileWriter fileWriter = new FileWriter(fileResult);
				fileWriter.write(result);
				fileWriter.close();
			}catch (IOException e) {
				inf1803.utils.Util.printError("Erro ao salvar arquivo", e);
			}
		}
		filechooser.setInitialFileName(
				"WebCrawler links "+
				new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(
						new Date() ));
		File fileLinks = filechooser.showSaveDialog(dialogStage);
		if(fileLinks!=null){
			try{
				FileWriter fileWriter = new FileWriter(fileLinks);
				fileWriter.write(WebCrawler.getLinksProcessed());
				fileWriter.close();
			}catch (IOException e) {
				inf1803.utils.Util.printError("Erro ao salvar arquivo", e);
			}
		}
		
	}
	
}
