package com.github.bjlhx15.servicees;

import com.alibaba.fastjson.JSON;
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
public class EsIndexOperate {

    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
    }

    class Data {
        String name;
        String address;
        String age;
        long num;

        public Data(String name, String address, String age,long num) {
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
        Data a = new Data("张四", "benjing", "12",12L);
        String jsonString = JSON.toJSONString(a);
        esBaseUtil.addDocument("bt_middle_data_test", "form", jsonString);
    }

    //删除index
    @Test
    public void esDelete() {

        esBaseUtil.deleteIndex("bt_middle_data_test1");
    }




}
