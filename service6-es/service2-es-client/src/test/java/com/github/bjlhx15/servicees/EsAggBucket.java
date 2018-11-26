package com.github.bjlhx15.servicees;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.global.GlobalAggregationBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainServiceApplication.class)
//@WebAppConfiguration
public class EsAggBucket {
    private Client client;
    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
        client=esBaseUtil.getClient();
    }

    @Test
    public void global(){

        GlobalAggregationBuilder aggregation = AggregationBuilders
                .global("name")
                .subAggregation(AggregationBuilders.terms("number").field("num"));

        SearchResponse sr = client.prepareSearch("bt_middle_data_test")
                .setTypes("form")
                .addAggregation(aggregation
                )
                .execute().actionGet();
        Global aaa = sr.getAggregations().get("name");
        double value = aaa.getDocCount();
        System.out.println("name:"+value);
    }
    @Test
    public void filteragg(){

        FiltersAggregationBuilder aggregation = AggregationBuilders
                .filters("name",
                        new FiltersAggregator.KeyedFilter("lisi", QueryBuilders.termQuery("name", "李四")),
                        new FiltersAggregator.KeyedFilter("wangwu", QueryBuilders.termQuery("name", "王五"))
                );

        SearchResponse sr = client.prepareSearch("bt_middle_data_test")
                .setTypes("form")
                .addAggregation(aggregation
                )
                .execute().actionGet();
        Filters aaa = sr.getAggregations().get("name");
        for (Filters.Bucket entry : aaa.getBuckets()) {
            String key = entry.getKeyAsString();            // bucket key
            long docCount = entry.getDocCount();            // Doc count
            System.out.println("key ["+key+"], doc_count [{"+docCount+"}]");
        }
    }
}
