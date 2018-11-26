package com.github.bjlhx15.servicees;

import com.lhx.springcloud.provider.business.ApplicationHttpClient7901;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.exponentialDecayFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;

/**
 * 复合搜索 结合其他的
 *
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ApplicationHttpClient7901.class)
//@WebAppConfiguration
public class EsQueryCompound {

    private Client client;

    /**
     * 1.1、常量得分查询constantScoreQuery
     */
    @Test
    public void constantScore() {
        QueryBuilder qb = constantScoreQuery(
                termQuery("name.keyword", "张三")
        )
                .boost(1.0f);
        excuteQuery(qb);
        //sout：{"address":"benjing","age":"1","name":"张三"}
    }

    /**
     * 1.2、布尔组合查询boolQuery
     */
    @Test
    public void bools() {
        QueryBuilder qb = boolQuery()
                .must(wildcardQuery("name.keyword", "*张三*"))//查询所有 包含 张三 的数据 一定是
                .mustNot(termQuery("name.keyword", "张三2"))//一定不包含 张三2
                .filter(prefixQuery("name.keyword", "张三"))//过滤 只需要张三前缀的
                .should(termQuery("age.keyword", "12")) // 在查询结果中 并且还满足年龄是 12 的 评分高
                ;
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
    /**
     * 1.4、functionScore
     */
    @Test
    public void functionScore() {
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        matchQuery("name.keyword", "张三"),
                        randomFunction("ABCDEF")),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        exponentialDecayFunction("num", 0L, 1L))
        };
        QueryBuilder qb = QueryBuilders.functionScoreQuery(functions);
        excuteQuery(qb);
    }

    /**
     * 1.5、提升查询 boosting
     */
    @Test
    public void boosting() {
        QueryBuilder qb = boostingQuery(
                termQuery("name.keyword","张三"),//提升
                termQuery("name.keyword","李宏旭"))//降级的
                .negativeBoost(0.2f);
        excuteQuery(qb);
    }

    /**
     * 1.6、indicesQuery： 废弃了 in 5.0.0.
     */
    @Test
    public void indices() {
        // Using another query when no match for the main one
        QueryBuilder qb = indicesQuery(
                termQuery("tag", "wow"),
                "index1", "index2"
        ).noMatchQuery(termQuery("tag", "kow"));
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
