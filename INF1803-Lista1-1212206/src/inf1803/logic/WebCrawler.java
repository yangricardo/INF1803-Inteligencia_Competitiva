package inf1803.logic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * INF1803 - Lista 1
 * @Author: Yang Ricardo Barcellos Miranda
*/
public class WebCrawler {

	private static Set<String> pagesVisited = new HashSet<String>();
	//Uma instancia de Set não possui itens duplicados
	private static List<String>pagesToVisit = new LinkedList<String>();
	//queue
	private static String webCrawlerResult = "";
	
	private static List<String> linksProcessed = new LinkedList<String>();
	
			
	public static String getLinksProcessed(){
		String result = "";
		for(String l : linksProcessed)
			result+=l+"\r\n";
		return result;
	}
	
	public static String search(String url, int maxPages){
		int i = 1;
		pagesVisited.clear(); pagesToVisit.clear(); linksProcessed.clear(); webCrawlerResult="";
		while(pagesVisited.size() < maxPages){
			System.out.println("---It: "+i);
			String currentUrl;
			//Escolha da pagina corrente
			if(pagesToVisit.isEmpty()){
				//se a lista pagesToVisit esta vazia, o url recebido é o corrente
				currentUrl = url; 
				pagesToVisit.add(currentUrl);
			}
			else{
				//remove o url de paginas a visitar até chegar a um que não tenha sido visitado
				//oou seja, não esteja contido em pagesVisited
				currentUrl = pagesToVisit.remove(0);
				System.out.println(currentUrl);
				while(pagesVisited.contains(currentUrl)){
					if(!pagesToVisit.isEmpty())
						currentUrl = pagesToVisit.remove(0);
					/*else //se não houver paginas para visitar retorna o resultado
						return webCrawlerResult;*/
				}
			}
			
			try{
				Connection connection = Jsoup.connect(currentUrl);
				connection.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1"
						);
				Document html = connection.get();
				
				if(connection.response().statusCode() == 200){//Ok status
					System.out.println("\n**Visitando pagina corrente: "+currentUrl);
				}
				if(!connection.response().contentType().contains("text/html")) {//se a pagina não contém elementos html, retorna falso
	                System.out.println("**Falha ao ler a pagina");
	                webCrawlerResult+="";
	            }
				Elements linksOnPage = html.select("a[href]");
				System.out.println(linksOnPage.size()+" links encontrados");
				for(Element l : linksOnPage){
					//adiciona links no currentUrl a lista pagesToVisit que não estão contidos nas paginas visitadas
					// &&!pagesToVisit.contains(l.absUrl("href"))  
					if(!pagesVisited.contains(l.absUrl("href"))){
						pagesToVisit.add(l.absUrl("href"));
					}
				}
				System.out.println("pagesVisited: "+ pagesVisited.size() +" pagesToVisit: "+ pagesToVisit.size());
				
				//concatena o conteudo da pagina
				webCrawlerResult+=
						i+"----------------------------------------------------------------\r\n"+
						currentUrl+"\r\n"+html.body().text()+
						"----------------------------------------------------------------\r\n";
			} catch (Exception e) {
				//falha ao conectar a pagina
				webCrawlerResult+="";
			}
			
			//marca a pagina corrente como visitada adicionando o currentUrl ao conjunto de pagesVisited
			pagesVisited.add(currentUrl);
			linksProcessed.add(currentUrl);
			i++;
		}
		
		System.out.println("Crawler Finished");
		

		return webCrawlerResult;
	}
	
}
