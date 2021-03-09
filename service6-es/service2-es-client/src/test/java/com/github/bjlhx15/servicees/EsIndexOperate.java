package com.github.bjlhx15.servicees;

import com.alibaba.fastjson.JSON;
import com.github.bjlhx15.eshelper.EsBase543Utils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
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
public class EsIndexOperate {

    private EsBase543Utils esBaseUtil;

    @Before
    public void init() throws UnknownHostException {
        System.out.println("--------init--------");
        Settings settings = Settings.builder()
                .put("cluster.name", "jiesi-5.4")
                .build();
//        TransportClient client = new PreBuiltTransportClient(settings)//Settings.EMPTY
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.11"), 20101))
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.12"), 20101))
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.13"), 20101));
        TransportClient client = new PreBuiltTransportClient(settings)//Settings.EMPTY
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        esBaseUtil = new EsBase543Utils(client);
//        esBaseUtil = new EsBaseUtil();
//        esBaseUtil.createClient();
    }

    class Data {
        String name;
        String address;
        String age;
        long num;

        public Data(String name, String address, String age, long num) {
            this.name = name;
            this.address = address;
            this.age = age;
            this.num = num;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    //添加数据同时  添加一个索引，在插入的时候 有新的字段 同时会自动更新
    @Test
    public void esWriteOne() {
        Data a = new Data("张四3", "benjing3", "33", 12L);
        String jsonString = JSON.toJSONString(a);
        esBaseUtil.addDocument("bt_middle_data_test4", "bt_middle_data","form", jsonString);
    }

    @Test
    public void addAliasIndexName() {
        esBaseUtil.addAliasIndex("bt_middle_data_test1", "bt_middle_data");
    }

    //删除index
    @Test
    public void esDelete() {

        esBaseUtil.deleteIndex("bt_middle_data_test");
    }

    @After
    public void finish() {
        System.out.println("--------finish--------");
        esBaseUtil.getClient().close();
    }


}
