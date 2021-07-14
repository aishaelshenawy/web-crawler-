/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler;

import java.util.*;



public class Crawler  {

    private static final int MAX_PAGES_TO_SEARCH = 1500;
    private Set<String> pagesVisited = new HashSet<String>(); //DS "set": to avoid the duplication of URLs
    private List<String> pagesToVisit = new LinkedList<String>();// DS "list": to store group of URLs to be visited
    
    public void search(String url, String searchWord){
      String currentUrl;
        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            
            Crawler_Leg leg = new Crawler_Leg();
            if (this.pagesToVisit.isEmpty()) {
                currentUrl = url;
                this.pagesVisited.add(url);
            } else {
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl);
            boolean success = leg.searchForWord(searchWord);
            if (success) {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
    }

    private String nextUrl() // method to return the next URL to be visited
    {
        String nextUrl;
        do {
            // to make sure that the URL is not duplicated
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}
 
