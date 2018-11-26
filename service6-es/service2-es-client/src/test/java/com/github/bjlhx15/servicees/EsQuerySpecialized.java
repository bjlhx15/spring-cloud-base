package com.github.bjlhx15.servicees;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 *
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
public class EsQuerySpecialized {

    private Client client;

    /**
     * 1.1、实现基于内容的推荐
     */
    @Test
    public void moreLikeThis() {
        String[] fields = {"name.keyword", "address.keyword"};
        String[] texts = {"张三"};
        MoreLikeThisQueryBuilder.Item[] items = null;

        QueryBuilder qb = moreLikeThisQuery(fields, texts, items)
//                .minTermFreq(10)   //忽略门槛
                .maxQueryTerms(20)  //生成的查询中的最大术语数
                ;
        excuteQuery(qb);
    }

    /**
     * 1.2、脚本查询,内嵌脚本
     */
    @Test
    public void script() {
        QueryBuilder qb =  scriptQuery(
                new Script("doc['num'].value > 30")
        );
        excuteQuery(qb);
        //sout
        //{"address":"benjing","age":"100","name":"李白","num":301}
        //{"address":"benjing","age":"50","name":"李四","num":31}
    }
    /**
     * 1.3、脚本查询,脚本文件
     */
    @Test
    public void scriptFile() {
        QueryBuilder qb = scriptQuery(
                new Script(
                        ScriptType.FILE,
                        "painless",
                        "myscript",
                        Collections.singletonMap("param1", 5))
        );
        excuteQuery(qb);
        //sout
        //{"address":"benjing","age":"100","name":"李白","num":301}
        //{"address":"benjing","age":"50","name":"李四","num":31}
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
