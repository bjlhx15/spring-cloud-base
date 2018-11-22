package com.github.bjlhx15.servicees;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainServiceApplication.class)
//@WebAppConfiguration
public class EsMapping {

    private Client client;


    @Test
    public void getMappingStruts() {
        String mapping = getMapping("bt_middle_data_test", "form");
        //{"form":{"properties":{"address":{"type":"text","fields":{"keyword":{"type":"keyword","ignore_above":256}}},
        // "age":{"type":"text","fields":{"keyword":{"type":"keyword","ignore_above":256}}},"name":{"type":"text",
        // "fields":{"keyword":{"type":"keyword","ignore_above":256}}},"num":{"type":"long"}}}}
        System.out.println(mapping);
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
