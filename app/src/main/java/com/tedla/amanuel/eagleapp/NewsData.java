package com.tedla.amanuel.eagleapp;

/**
 * Created by dVentus-hq on 6/17/2017.
 */
public class NewsData {
    private String newsTitle;
    private String newsDetail;

    public NewsData(String newsTitle, String newsDetail) {
        this.newsTitle = newsTitle;
        this.newsDetail = newsDetail;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsDetail() {
        return newsDetail;
    }

    public static final NewsData[] newsList = {
            new NewsData("Amazon to Buy Whole Foods for $13.4 Billion","Customers at a Whole Foods Market in Midtown Manhattan. Credit John Taggart for The New York Times. Amazon agreed to buy the upscale grocery chain Whole Foods for $13.4 billion, in a deal that will instantly transform the company that pioneered online"),
            new NewsData("Amazon to Buy Whole Foods for $13.4 Billion","Customers at a Whole Foods Market in Midtown Manhattan. Credit John Taggart for The New York Times. Amazon agreed to buy the upscale grocery chain Whole Foods for $13.4 billion, in a deal that will instantly transform the company that pioneered online"),
            new NewsData("Amazon to Buy Whole Foods for $13.4 Billion","Customers at a Whole Foods Market in Midtown Manhattan. Credit John Taggart for The New York Times. Amazon agreed to buy the upscale grocery chain Whole Foods for $13.4 billion, in a deal that will instantly transform the company that pioneered online"),
            new NewsData("Amazon to Buy Whole Foods for $13.4 Billion","Customers at a Whole Foods Market in Midtown Manhattan. Credit John Taggart for The New York Times. Amazon agreed to buy the upscale grocery chain Whole Foods for $13.4 billion, in a deal that will instantly transform the company that pioneered online"),
            new NewsData("Amazon to Buy Whole Foods for $13.4 Billion","Customers at a Whole Foods Market in Midtown Manhattan. Credit John Taggart for The New York Times. Amazon agreed to buy the upscale grocery chain Whole Foods for $13.4 billion, in a deal that will instantly transform the company that pioneered online"),
    };

}
