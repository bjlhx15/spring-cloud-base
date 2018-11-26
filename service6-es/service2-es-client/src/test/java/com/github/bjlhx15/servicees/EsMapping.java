package com.github.bjlhx15.servicees;

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
public class EsMapping {

    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
    }


    @Test
    public void getMappingStruts() {
        String mapping = esBaseUtil.getMapping("bt_middle_data_test", "form");
        //{"form":{"properties":{"address":{"type":"text","fields":{"keyword":{"type":"keyword","ignore_above":256}}},
        // "age":{"type":"text","fields":{"keyword":{"type":"keyword","ignore_above":256}}},"name":{"type":"text",
        // "fields":{"keyword":{"type":"keyword","ignore_above":256}}},"num":{"type":"long"}}}}
        System.out.println(mapping);
    }



}
