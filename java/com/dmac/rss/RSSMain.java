package com.dmac.rss;

import com.dmac.rest.RESTPostClient;

/**
 * Created by aad-pc1 on 7/3/17.
 */
public class RSSMain {

    public static void main(String args[]) throws InterruptedException {


        String GOOGLE_FEED = "https://news.google.com/news?q=aluminium%20price&output=rss";
        String FRANCE_FEED = "http://www.france24.com/en/france/rss";
        String FRANCE_FEED2 = "http://www.thejournal.ie/france-election-explainer-3230394-Feb2017//feed/";

        RSSFeedParser parser = new RSSFeedParser(
                GOOGLE_FEED);
        Feed feed = parser.readFeed();

        RESTPostClient postClient = new RESTPostClient();

        for (FMessage message : feed.getMessages()) {

            if (message.getDescription().length() < 99200) {
                System.out.println(message.toString());
                postClient.post("newsfeed", "docs", "{\"feed\":\"" + message.getDescription() + "\"}");
            }
            Thread.sleep(10);
        }
    }
}
