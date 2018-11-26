package com.github.bjlhx15.servicees;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
//@RunWith(SpringRunner.class)
////@SpringBootTest(classes = MainServiceApplication.class)
////@WebAppConfiguration
public class EsDocumentOperate {

    private Client client;

    @Test
    public void esWriteOne() {
        String js1="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"张四\",\"num\":12}";
        System.out.println(js1);
        addDocument("bt_middle_data_test", "form", js1);
    }

    @Test
    public void esWriteOneSon() {
        String js1="{\"sex\":\"男\",\"name\":\"张四\"}";
        System.out.println(js1);
        addDocument("bt_middle_data_test", "form_son", js1);
    }

    @Test
    public void esWriteOne2() {
        String js1="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"张四nested\",\"num\":12,"+
        "  \"comments\":[\n" +
                "     {\n" +
                "\t  \"name\":    \"John Smith\",\n" +
                "      \"comment\": \"Great article\",\n" +
                "      \"age\":     28,\n" +
                "      \"stars\":   4,\n" +
                "      \"date\":    \"2014-09-01\"\n" +
                "\t },\n" +
                "\t {\n" +
                "      \"name\":    \"Alice White\",\n" +
                "      \"comment\": \"More like this please\",\n" +
                "      \"age\":     31,\n" +
                "      \"stars\":   5,\n" +
                "      \"date\":    \"2014-10-22\"\n" +
                "     }\n" +
                "  ]"+
                "}";

        System.out.println(js1);
        addDocument("bt_middle_data_test", "form", js1);
    }

    @Test
    public void esWriteMulti() {
        List<String> list = new ArrayList<>();

        String jsonStr="{\"address\":\"benjing\",\"age\":\"1\",\"name\":\"张三\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"张三2\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"50\",\"name\":\"李四\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"100\",\"name\":\"李白\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"王五\",\"num\":3}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"100\",\"name\":\"李白\",\"num\":301}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"李宏旭 张三3\",\"num\":12}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"50\",\"name\":\"李四\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"张三3\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"王五\"}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"50\",\"name\":\"李四\",\"num\":31}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"aa张三3\",\"num\":12}" ;
        list.add(jsonStr);
        jsonStr="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"张爱三\",\"num\":12}" ;
        list.add(jsonStr);

        addDocument("bt_middle_data_test", "form", list);
    }

    @Test
    public void deleteById() {
        deleteDocumentById("bt_middle_data_test", "form", "3d35e73d-fb4a-40de-9d7f-833292b6a104");
    }


    public void deleteDocumentById(String indexName, String type, String id){
        DeleteResponse response = client.prepareDelete(indexName, type, id).get();
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
