package com.github.bjlhx15.servicees;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 全文检索，针对text类型，模糊搜索
 *  @author lihongxu
 * @since 2018/11/20 下午5:56
 */
public class EsQueryFullText {

    private Client client;

    /**
     * 1.1、查询所有数据
     */
    @Test
    public void matchAll() {
        QueryBuilder qb = matchAllQuery();
        excuteQuery(qb);
    }
    @Test
    public void matchType() {
        QueryBuilder qb = matchAllQuery();
        excuteQuery("bt_middle_data_test","form_son",qb);
    }

    /**
     * 1.2、查询匹配数据，最少包含分词的一个
     */
    @Test
    public void match() {
        QueryBuilder qb = matchQuery("name", "张三");
        excuteQuery(qb);
    }


    /**
     * 1.3、多字段查询匹配数据，最少包含分词的一个
     */
    @Test
    public void multiMatch() {
        QueryBuilder qb = multiMatchQuery("张三 12", "name","age");
        excuteQuery(qb);
    }

    @Test
    public void commonTerms() {
        QueryBuilder qb = commonTermsQuery("name", "张三");
        excuteQuery(qb);
    }
    @Test
    public void queryString() {
        QueryBuilder qb = queryStringQuery("+张三");
        excuteQuery(qb);
    }
    @Test
    public void simpleQueryString() {
        QueryBuilder qb = simpleQueryStringQuery("+张三 -12");
        excuteQuery(qb);
    }


    private void excuteQuery(String index,String type,QueryBuilder qb) {
        SearchResponse sr = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(qb).setSize(20)
                .execute().actionGet();
        SearchHits searchHits = sr.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("数据:" + searchHit.getSourceAsString());
        }
    }
    private void excuteQuery(QueryBuilder qb) {
        excuteQuery("bt_middle_data_test","form",qb);
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
