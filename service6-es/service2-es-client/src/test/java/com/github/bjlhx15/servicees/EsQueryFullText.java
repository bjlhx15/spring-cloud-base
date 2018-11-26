package com.github.bjlhx15.servicees;

import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 全文检索，针对text类型，模糊搜索
 *  @author lihongxu
 * @since 2018/11/20 下午5:56
 */
public class EsQueryFullText {


    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
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
        esBaseUtil.excuteQuery("bt_middle_data_test","form_son",qb);
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
