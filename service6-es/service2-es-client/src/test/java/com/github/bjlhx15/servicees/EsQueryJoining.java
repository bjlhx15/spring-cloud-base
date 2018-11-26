package com.github.bjlhx15.servicees;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.exponentialDecayFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;

/**
 * Joining搜索 暂时没细看
 *
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
public class EsQueryJoining {

    private Client client;

    /**
     * 1.1、嵌套查询[失败]
     */
    @Test
    public void nested() {
        QueryBuilder qb = nestedQuery(
                "commonts",//嵌套文档的路径
                boolQuery() //您的查询。查询中引用的任何字段都必须使用完整路径（完全限定）。
                        .must(termQuery("commonts.name", "张四"))
//                        .must(rangeQuery("form_son.age").gt(30))
                ,
                ScoreMode.Avg  //ScoreMode.Max, ScoreMode.Min, ScoreMode.Total, ScoreMode.Avg or ScoreMode.None
        );
        excuteQuery(qb);
        //sout：{"address":"benjing","age":"1","name":"张三"}
    }

    /**
     * 1.2、布尔组合查询boolQuery
     */
    @Test
    public void hasChild() {
        QueryBuilder qb = hasChildQuery(
                "form_son",
                termQuery("name","张四"),
                ScoreMode.Avg
        );
        excuteQuery(qb);
        //sout
        //1.6931472
        //{"address":"benjing","age":"12","name":"张三3"}
        //1.0
        //{"address":"benjing","age":"1","name":"张三"}
    }


    /**
     * 1.3、disMaxQuery
     */
    @Test
    public void disMax() {
        QueryBuilder qb = disMaxQuery()
                .add(termQuery("age.keyword", "1"))
                .add(termQuery("age.keyword", "100"))
                .boost(1.5f)
                .tieBreaker(0.7f)
                ;
        excuteQuery(qb);
    }



    private void excuteQuery(QueryBuilder qb) {
        System.err.println("执行语句：" + qb);
        SearchResponse sr = client.prepareSearch("bt_middle_data_test")
                .setTypes("form")
                .setQuery(qb)
                .execute().actionGet();
        SearchHits searchHits = sr.getHits();

        System.err.println("----结果数据 start----");
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getScore());
            System.out.println(searchHit.getSourceAsString());
        }
        System.err.println("----结果数据 end----");
    }

    /**
     * 客户端
     */
    private TransportClient createClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "jiesi-5.4")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)//Settings.EMPTY
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.11"), 20101))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.12"), 20101))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.13"), 20101));
        return client;
    }

    @Before
    public void init() throws UnknownHostException {
        client = createClient();
    }
}
