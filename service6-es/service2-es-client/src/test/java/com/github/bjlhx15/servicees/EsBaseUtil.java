package com.github.bjlhx15.servicees;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Response;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.ClosedWatchServiceException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author lihongxu
 * @since 2018/11/26 上午10:57
 */
@Deprecated
public class EsBaseUtil {
    private Client client;

    public Client getClient() {
        return client;
    }

    //region 客户端

    /**
     * 客户端
     */
    public Client createClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "jiesi-5.4")
                .build();
//        client = new PreBuiltTransportClient(settings)//Settings.EMPTY
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.11"), 20101))
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.12"), 20101))
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.13"), 20101));


        client = new PreBuiltTransportClient(settings)//Settings.EMPTY
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }
    //endregion

    //region 索引映射操作

    /**
     * 1、创建索引【创建数据库】 单纯创建 没有指明映射以及分片信息
     *
     * @param indexName 索引名称
     */
    public void esCreateIndex(String indexName) {
        if (!existIndex(indexName)) {
            client.admin().indices().prepareCreate(indexName).get();
        }
    }

    /**
     * 2、创建索引【创建数据库】 指明分片以及副本
     *
     * @param indexName 索引名
     * @param shards    分分片数
     * @param replicas  副本数
     */
    public void esCreateIndex(String indexName, int shards, int replicas) {
        if (!existIndex(indexName)) {
            esCreateIndex(indexName, Settings.builder()
                    .put("index.number_of_shards", shards)
                    .put("index.number_of_replicas", replicas)
            );
        }
    }

    /**
     * 3、创建索引【创建数据库】  指明分片以及副本
     *
     * @param indexName 索引名称
     * @param builder   基本配置
     */
    public void esCreateIndex(String indexName, Settings.Builder builder) {
        if (!existIndex(indexName)) {
            client.admin().indices().prepareCreate(indexName)
                    .setSettings(builder)
                    .get();
        }
    }

    /**
     * 4、创建索引【创建数据库，表以及字段】 同时 具有mapping
     *
     * @param indexName 索引名
     * @param type      type名 表名
     * @param mapping   映射文件 {"typeName":{"properties":{"fieldName":{"type":"string"}}}}
     */
    public void esCreateIndexWithMapping(String indexName, String type, String mapping) {
        if (!existIndex(indexName)) {
            client.admin().indices().prepareCreate(indexName)
                    .addMapping(type, mapping, XContentType.JSON)
                    .get();
        }
    }

    /**
     * 5、在 索引已经创建的 创建或更新 type 以及mapping【在已有数据库，创建表以及字段】
     *
     * @param indexName
     * @param type
     * @param mapping   {"properties":{"name":{"type ":"string "}}} 或者 {"typeName":{"properties":{"name":{"type ":"string "}}}}
     */
    public void esCreateOrUpdateTypeWithMapping(String indexName, String type, String mapping) {
        if (!existIndex(indexName)) {
            client.admin().indices().preparePutMapping(indexName)
                    .setType(type)
                    .setSource(mapping, XContentType.JSON)
                    .get();
        }
    }

    /**
     * 获取映射
     */
    public String getMapping(String indexName, String typeName) {
        MappingMetaData mappingMetaData = client.admin().cluster().prepareState().execute()
                .actionGet().getState().getMetaData().getIndices()
                .get(indexName).getMappings()
                .get(typeName);
        String toString = mappingMetaData.source().toString();
        return toString;
    }

    /**
     * 刷新索引
     *
     * @param indices 空 刷新所有；指定具体刷新
     *                es默认的refresh间隔时间是1s 近乎实时搜索
     */
    public void esRefreshIndices(String[] indices) {
        if (indices == null || indices.length == 0) {
            client.admin().indices().prepareRefresh().get();
        } else {

            client.admin().indices()
                    .prepareRefresh(indices)
                    .get();
        }
    }


    /**
     * 删除索引
     */
    public void deleteIndex(String indexName) {
        client.admin().indices().prepareDelete(indexName).get();
    }

    /**
     * 判断索引是否存在
     */
    public boolean existIndex(String indexName) {
        IndicesExistsRequest request = new IndicesExistsRequest(indexName);
        IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
        return response.isExists();
    }

    //endregion

    // region 文档操作

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
     * 通过ID删除文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     */
    public ActionResponse deleteDocumentById(String indexName, String type, String id) {
        return client.prepareDelete(indexName, type, id).get();
    }

    /**
     * 通过ID获取文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     */
    public ActionResponse getDocumentById(String indexName, String type, String id) {
        return client.prepareGet(indexName, type, id).get();
    }

    /**
     * 通过ID更新文档
     *
     * @param indexName
     * @param type
     * @param id
     * @return
     */
    public ActionResponse updateDocumentById(String indexName, String type, String id, String updateJsonString) throws ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexName);
        updateRequest.type(type);
        updateRequest.id(id);
        updateRequest.doc(updateJsonString, XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest).get();
        return updateResponse;
    }

    // endregion

    //region 查询

    /**
     * 执行查询
     *
     * @param index
     * @param type
     * @param qb
     */
    public void excuteQuery(String index, String type, QueryBuilder qb) {
        SearchResponse sr = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(qb).setSize(20)
                .execute().actionGet();
        SearchHits searchHits = sr.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("数据:" + searchHit.getSourceAsString());
        }
    }

    /**
     * 执行查询——_—
     *
     * @param qb
     */
    public void excuteQuery(QueryBuilder qb) {
        excuteQuery("bt_middle_data_test", "form", qb);
    }
    //endregion

    //region 索引配置信息

    //设置索引 配置信息 public void esCreateIndex(String indexName, Settings.Builder builder)
    /**
     * 获取索引 配置信息
     *
     * @param indices 索索引名
     * @return
     */
    public ActionResponse getSettingResponse(String[] indices) {
        GetSettingsResponse response = client.admin().indices()
                .prepareGetSettings(indices).get();
        return response;
//        for (ObjectObjectCursor<String, Settings> cursor : response.getIndexToSettings()) {
//            String index = cursor.key;
//            Settings settings = cursor.value;
//            Integer shards = settings.getAsInt("index.number_of_shards", null);
//            Integer replicas = settings.getAsInt("index.number_of_replicas", null);
//        }
    }

    /**
     * 更新索引 配置信息
     *
     * @param indexName 索引名
     * @param builder 更新设置 Settings.builder().put("index.number_of_replicas", 0)
     *
     * @return
     */
    public void updateIndicesSetting(String indexName, Settings.Builder builder) {
        client.admin().indices().prepareUpdateSettings(indexName)
                .setSettings(builder)
                .get();
    }
    //endregion

    //region 集群健康

    public ActionResponse getClusterHealthResponse(){
        ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
        String clusterName = healths.getClusterName();
        int numberOfDataNodes = healths.getNumberOfDataNodes();
        int numberOfNodes = healths.getNumberOfNodes();

        System.out.println("集群名称："+clusterName);
        System.out.println("集群节点数："+numberOfNodes);
        System.out.println("集群数据节点数："+numberOfDataNodes);

        for (ClusterIndexHealth health : healths.getIndices().values()) {
            String index = health.getIndex();
            int numberOfShards = health.getNumberOfShards();
            int numberOfReplicas = health.getNumberOfReplicas();
            ClusterHealthStatus status = health.getStatus();

            System.out.println("集群索引名："+index+"；分片数："+numberOfShards
                    +"；副本数："+numberOfReplicas+"；状态："+status);
        }
        return healths;
    }

    public ActionResponse getClusterHealthGreenStatus(String[] indices){
        ClusterHealthResponse healthResponse = client.admin().cluster().prepareHealth(indices)
                .setWaitForGreenStatus()
                .get();
        for (ClusterIndexHealth health : healthResponse.getIndices().values()) {
            String index = health.getIndex();
            int numberOfShards = health.getNumberOfShards();
            int numberOfReplicas = health.getNumberOfReplicas();
            ClusterHealthStatus status = health.getStatus();

            System.out.println("集群索引名："+index+"；分片数："+numberOfShards
                    +"；副本数："+numberOfReplicas+"；状态："+status);
        }
        return healthResponse;
    }
    public ActionResponse getClusterHealthGreenStatus(String index){
        ClusterHealthResponse healthResponse = client.admin().cluster().prepareHealth(index)
                .setWaitForGreenStatus()
                .get();
        ClusterHealthStatus status = healthResponse.getIndices().get(index).getStatus();
        if (!status.equals(ClusterHealthStatus.GREEN)) {
            throw new RuntimeException("Index is in " + status + " state");
        }


        return healthResponse;
    }
    //endregion
}
