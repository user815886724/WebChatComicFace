package com.face.crawler.util;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * @author huangwh
 * @date 2018/10/26
 * @time 0:20
 */
public class CrawlerUtil extends BreadthCrawler {
    public CrawlerUtil(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);

    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {

    }
}
