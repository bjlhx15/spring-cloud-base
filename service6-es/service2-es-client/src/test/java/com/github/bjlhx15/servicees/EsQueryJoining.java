package com.github.bjlhx15.servicees;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Joining搜索 暂时没细看
 *
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
public class EsQueryJoining {

    private EsBaseUtil esBaseUtil;

    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
    }

    /**
     * 1.1、嵌套查询[失败]
     */
    @Test
    public void nested() {
        QueryBuilder qb = nestedQuery(
                "commonts",//嵌套文档的路径
                boolQuery() //您的查询。查询中引用的任何字段都必须使用完整路径（完全限定）。
                        .must(termQuery("commonts.name", "张四"))
//                        .must(rangeQuery("form_son.age").gt(30))
                ,
                ScoreMode.Avg  //ScoreMode.Max, ScoreMode.Min, ScoreMode.Total, ScoreMode.Avg or ScoreMode.None
        );
        esBaseUtil.excuteQuery(qb);
        //sout：{"address":"benjing","age":"1","name":"张三"}
    }

    /**
     * 1.2、布尔组合查询boolQuery
     */
//    @Test
//    public void hasChild() {
//        QueryBuilder qb = hasChildQuery(
//                "form_son",
//                termQuery("name", "张四"),
//                ScoreMode.Avg
//        );
//        esBaseUtil.excuteQuery(qb);
//        //sout
//        //1.6931472
//        //{"address":"benjing","age":"12","name":"张三3"}
//        //1.0
//        //{"address":"benjing","age":"1","name":"张三"}
//    }


    /**
     * 1.3、disMaxQuery
     */
    @Test
    public void disMax() {
        QueryBuilder qb = disMaxQuery()
                .add(termQuery("age.keyword", "1"))
                .add(termQuery("age.keyword", "100"))
                .boost(1.5f)
                .tieBreaker(0.7f);
        esBaseUtil.excuteQuery(qb);
    }



}
