package inf1803.view;

import inf1803.model.FeedItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class HyperlinkCell implements  Callback<TableColumn<FeedItem, Hyperlink>, TableCell<FeedItem, Hyperlink>> {
	 
    @Override
    public TableCell<FeedItem, Hyperlink> call(TableColumn<FeedItem, Hyperlink> arg) {
    	
        TableCell<FeedItem, Hyperlink> cell = new TableCell<FeedItem, Hyperlink>() {
        	@Override
            protected void updateItem(Hyperlink item, boolean empty) {
        		setGraphic(item);
            }

            
        };
        /*cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    try {
                    	System.out.println(cell.getAccessibleText());
						java.awt.Desktop.getDesktop().browse(new URI(cell.getText()));
					} catch (IOException | URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });*/
        return cell;
    }
    
}