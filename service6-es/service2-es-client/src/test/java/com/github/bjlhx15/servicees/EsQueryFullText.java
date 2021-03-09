package com.github.bjlhx15.servicees;

import com.github.bjlhx15.eshelper.EsBase543Utils;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 全文检索，针对text类型，模糊搜索
 *  @author lihongxu
 * @since 2018/11/20 下午5:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class EsQueryFullText {

    @Configuration
    static class ContextConfiguration {
        // this bean will be injected into the OrderServiceTest class
        @Bean("esBase543Utils")
        public EsBase543Utils createEsBase543Utils() throws UnknownHostException {
            Settings settings = Settings.builder()
                    .put("cluster.name", "jiesi-5.4")
                    .build();
            InetSocketTransportAddress[] a= new InetSocketTransportAddress[3];
            a[0]=(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.11"), 20101));
            a[1]=(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.12"), 20101));
            a[2]=(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.13"), 20101));
            return new EsBase543Utils(settings,a);
        }
    }



    @Autowired
    private EsBase543Utils esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
//        esBaseUtil = new EsBaseUtil();
//        Settings settings = Settings.builder()
//                .put("cluster.name", "jiesi-5.4")
//                .build();
//        InetSocketTransportAddress[] a= new InetSocketTransportAddress[3];
//        a[0]=(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.11"), 20101));
//        a[1]=(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.12"), 20101));
//        a[2]=(new InetSocketTransportAddress(InetAddress.getByName("192.168.182.13"), 20101));
//
//        esBaseUtil.init(settings,a);
    }

    /**
     * 1.1、查询所有数据
     */
    @Test
    public void matchAll() {
        QueryBuilder qb = matchAllQuery();
        esBaseUtil.excuteQuery(qb);
    }
    @Test
    public void matchType() {
        QueryBuilder qb = matchAllQuery();
        esBaseUtil.excuteQuery("bt_middle_data","form",qb);
//        esBaseUtil.excuteQuery("bt_middle_data_test","form",qb);
    }

    /**
     * 1.2、查询匹配数据，最少包含分词的一个
     */
    @Test
    public void match() {
        QueryBuilder qb = matchQuery("name", "张三");
        esBaseUtil.excuteQuery(qb);
    }


    /**
     * 1.3、多字段查询匹配数据，最少包含分词的一个
     */
    @Test
    public void multiMatch() {
        QueryBuilder qb = multiMatchQuery("张三 12", "name","age");
        esBaseUtil.excuteQuery(qb);
    }

    @Test
    public void commonTerms() {
        QueryBuilder qb = commonTermsQuery("name", "张三");
        esBaseUtil.excuteQuery(qb);
    }
    @Test
    public void queryString() {
        QueryBuilder qb = queryStringQuery("+张三");
        esBaseUtil.excuteQuery(qb);
    }
    @Test
    public void simpleQueryString() {
        QueryBuilder qb = simpleQueryStringQuery("+张三 -12");
        esBaseUtil.excuteQuery(qb);
    }
}
