package com.github.bjlhx15.servicees;

import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
//@RunWith(SpringRunner.class)
////@SpringBootTest(classes = MainServiceApplication.class)
////@WebAppConfiguration
public class EsDocumentOperate {

    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
    }

    @Test
    public void esWriteOne() {
        String js1="{\"address\":\"benjing\",\"age\":\"12\",\"name\":\"张四\",\"num\":12}";
        System.out.println(js1);
        esBaseUtil.addDocument("bt_middle_data_test", "form", js1);
    }

    @Test
    public void esWriteOneSon() {
        String js1="{\"sex\":\"男\",\"name\":\"张四\"}";
        System.out.println(js1);
        esBaseUtil.addDocument("bt_middle_data_test", "form_son", js1);
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
        esBaseUtil.addDocument("bt_middle_data_test", "form", js1);
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

        esBaseUtil.addDocument("bt_middle_data_test", "form", list);
    }

    @Test
    public void deleteById() {
        esBaseUtil.deleteDocumentById("bt_middle_data_test", "form", "3d35e73d-fb4a-40de-9d7f-833292b6a104");
    }
}
