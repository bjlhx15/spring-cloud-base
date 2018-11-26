package com.github.bjlhx15.servicees;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
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
public class EsAggMetrics {

    private Client client;
    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
        client=esBaseUtil.getClient();
    }


    @Test
    public void min(){
        // 这里可以修改成 min max  sum avg
        MinAggregationBuilder aggregation =
                AggregationBuilders
                        .min("号码")
                        .field("num");

        SearchResponse sr = client.prepareSearch()
                .addAggregation(aggregation
                )
                .execute().actionGet();
        Min aaa = sr.getAggregations().get("号码");
        double value = aaa.getValue();
        System.out.println("号码:"+value);
    }
    @Test
    public void count(){
        ValueCountAggregationBuilder aggregation =
                AggregationBuilders
                        .count("号码")
                        .field("num");

        SearchResponse sr = client.prepareSearch("bt_middle_data_test")
                .setTypes("form")
                .addAggregation(aggregation
                )
                .execute().actionGet();
        ValueCount aaa = sr.getAggregations().get("号码");
        double value = aaa.getValue();
        System.out.println("号码:"+value);
    }
}
