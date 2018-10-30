package com.face.crawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;

/**
 * @author huangwh
 * @date 2018/10/25
 * @time 22:27
 */
public class NewsCrawler extends BreadthCrawler {

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.url();
        if (page.matchUrl("http://news.hfut.edu.cn/show-.*html")) {
            Document doc = page.doc();
            String title = page.select("div[id=Article]>h2").first().text();
            String content = page.select("div#artibody", 0).text();

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);
        }
    }

    public NewsCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        this.addSeed("http://news.hfut.edu.cn/list-1-1.html");
        this.addRegex("http://news.hfut.edu.cn/show-.*html");
        this.addRegex("-.*\\.(jpg|png|gif).*");
        this.addRegex("-.*#.*");
    }

    public static void main(String[] args) throws Exception {
        NewsCrawler crawler = new NewsCrawler("crawl", true);
        crawler.setThreads(50);
        crawler.setTopN(100);
        crawler.start(4);
    }
}
