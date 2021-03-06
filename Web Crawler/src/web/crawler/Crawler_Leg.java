package web.crawler;

import java.io.IOException;
import java.util.*;


// jsoup library helps in making HTTP request and parsing the page
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler_Leg {

    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT
            = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument; // the extracted document

    public boolean crawl(String url) // takes URL as param. and makes HTTP req. for next URL
    {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
//            Locale locale;
//            locale = Locale.getDefault();
//            
//             String text = htmlDocument.data();
//             
//            if (text.equals(locale) ) {
//                 System.out.println("The Document will be parsed");
//            } else {
//                System.out.println("The Document's languge is not English");
//                System.exit(0);
//            }

            this.htmlDocument = htmlDocument;

            if (connection.response().statusCode() == 200) // 200 is the HTTP OK status code "Everything is great"
            {
                System.out.println("\n**Visiting** Received web page at : " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");// get links
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for (Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    public boolean searchForWord(String searchWord) // Tries to find a word on the page
    {
        if (this.htmlDocument == null) {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }

    public List<String> getLinks()// returns the list of all the links on the page
    {
        return this.links;
    }

}
