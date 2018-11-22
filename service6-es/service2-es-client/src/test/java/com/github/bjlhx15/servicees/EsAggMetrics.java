package com.github.bjlhx15.servicees;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainServiceApplication.class)
//@WebAppConfiguration
public class EsAggMetrics {

    private Client client;


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

    /**
     * 添加数据
     */
    public void addDocument(String indexName, String type, String id, String json) {
        esCreateIndex(indexName);
        client.prepareIndex(indexName, type, id)
                .setSource(json, XContentType.JSON)
                .get();
    }

    /**
     * 添加数据
     */
    public void addDocument(String indexName, String type, String json) {
        esCreateIndex(indexName);
        client.prepareIndex(indexName, type, UUID.randomUUID().toString())
                .setSource(json, XContentType.JSON)
                .get();
    }

    /**
     * 添加数据
     */
    public void addDocument(String indexName, String type, List<String> jsonArray) {
        esCreateIndex(indexName);

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for (String s : jsonArray) {
            bulkRequest.add(client.prepareIndex(indexName, type, UUID.randomUUID().toString())
                    .setSource(s, XContentType.JSON
                    )
            );
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.isFragment()) {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                System.out.println(bulkItemResponse.getId());
            }
        }
    }

    /**
     * 添加数据
     */
    public void addDocument(String indexName, String type, Map<String, String> jsonArrayMap) {
        esCreateIndex(indexName);

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (Map.Entry<String, String> entry : jsonArrayMap.entrySet()) {
            bulkRequest.add(client.prepareIndex(indexName, type, entry.getKey())
                    .setSource(entry.getValue(), XContentType.JSON
                    )
            );
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.isFragment()) {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                System.out.println(bulkItemResponse.getId());
            }
        }
    }

    /**
     * 创建索引
     */
    public void esCreateIndex(String indexName) {
        if (!existIndex(indexName)) {
            client.admin().indices().prepareCreate(indexName).get();
        }
    }

    /**
     * 删除索引
     */
    private void deleteIndex(String indexName) {
        client.admin().indices().prepareDelete(indexName).get();
    }

    /**
     * 索引存在
     */
    private boolean existIndex(String indexName) {
        IndicesExistsRequest request = new IndicesExistsRequest(indexName);
        IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
        return response.isExists();
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
