package com.github.bjlhx15.servicees;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Collections;

import static org.elasticsearch.index.query.QueryBuilders.moreLikeThisQuery;
import static org.elasticsearch.index.query.QueryBuilders.scriptQuery;

/**
 *
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
public class EsQuerySpecialized {

    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
    }

    /**
     * 1.1、实现基于内容的推荐
     */
    @Test
    public void moreLikeThis() {
        String[] fields = {"name.keyword", "address.keyword"};
        String[] texts = {"张三"};
        MoreLikeThisQueryBuilder.Item[] items = null;

        QueryBuilder qb = moreLikeThisQuery(fields, texts, items)
//                .minTermFreq(10)   //忽略门槛
                .maxQueryTerms(20)  //生成的查询中的最大术语数
                ;
        esBaseUtil.excuteQuery(qb);
    }

    /**
     * 1.2、脚本查询,内嵌脚本
     */
    @Test
    public void script() {
        QueryBuilder qb =  scriptQuery(
                new Script("doc['num'].value > 30")
        );
        esBaseUtil.excuteQuery(qb);
        //sout
        //{"address":"benjing","age":"100","name":"李白","num":301}
        //{"address":"benjing","age":"50","name":"李四","num":31}
    }
    /**
     * 1.3、脚本查询,脚本文件
     */
    @Test
    public void scriptFile() {
        QueryBuilder qb = scriptQuery(
                new Script(
                        ScriptType.FILE,
                        "painless",
                        "myscript",
                        Collections.singletonMap("param1", 5))
        );
        esBaseUtil.excuteQuery(qb);
        //sout
        //{"address":"benjing","age":"100","name":"李白","num":301}
        //{"address":"benjing","age":"50","name":"李四","num":31}
    }

}
