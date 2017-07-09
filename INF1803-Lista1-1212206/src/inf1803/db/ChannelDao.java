package inf1803.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import inf1803.model.FeedChannel;
import inf1803.utils.Util;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class ChannelDao {

	public static ArrayList<FeedChannel> getFeeds(){
		ArrayList<FeedChannel> resultados = new ArrayList<FeedChannel>();
		try{
			ResultSet rs = ConnectionHandler.openConnection().executeQuery(
						"SELECT * FROM channel;"
					);
			while(rs.next()){
				resultados.add(new FeedChannel(rs.getString("urlrss"), rs.getString("title"),
						rs.getString("link"), rs.getString("description"), rs.getString("pubDate"), 
						rs.getString("category")));
			}
		} catch (SQLException e) {
			Util.printError("User Inexistente ou ja Existente", e);
			return null;
		} finally {
			ConnectionHandler.closeConnection();
		}
		return resultados;
	}
	
	
	public static void insertChannel(FeedChannel feed){
		try{
			ConnectionHandler.openConnection().executeUpdate(
					"INSERT INTO channel (title,description,link,pubdate,category,urlrss)"
					+"VALUES('"+feed.getTitle()
					+"','"+feed.getDescription()
					+"','"+feed.getLink()+
					"','"+ feed.getPubDate()
					+"','"+feed.getCategory()
					+"','"+feed.getUrlrss()
					+"');"
					);
		
		} catch(SQLException e){
			Util.printError("Erro ao criar canal", e);
		} finally{
			ConnectionHandler.closeConnection();
		}
	}
	
	public static void deleteChannel(String urlrss){
		try{
			ConnectionHandler.openConnection().executeUpdate(
					"DELETE FROM channel"
					+ " WHERE urlrss='"+urlrss+"' ;"
					);
		
		} catch(SQLException e){
			Util.printError("Erro ao criar canal", e);
		} finally{
			ConnectionHandler.closeConnection();
		}
	}
	
	
	public static void main(String[] args) {
		//insertChannel(new FeedChannel("a", "b", "c", "d", "e", "f"));
		//deleteChannel("b");
		/*ArrayList<FeedChannel> feeds = getFeeds();
		for(FeedChannel feed : feeds){
			System.out.println(feed.toString());
		}*/
		
		//deleteChannel("http://oglobo.globo.com/rss.xml");
	}

	
}
