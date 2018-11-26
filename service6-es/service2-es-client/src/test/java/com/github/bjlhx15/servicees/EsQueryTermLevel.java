package com.github.bjlhx15.servicees;

import com.lhx.springcloud.provider.business.ApplicationHttpClient7901;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 精准搜索 针对keyword、number、date等
 * @author lihongxu
 * @since 2018/11/20 下午5:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationHttpClient7901.class)
@WebAppConfiguration
public class EsQueryTermLevel {

    private EsBaseUtil esBaseUtil;
    @Before
    public void init() throws UnknownHostException {
        esBaseUtil = new EsBaseUtil();
        esBaseUtil.createClient();
    }

    /**
     * 1.1、精准查询
     * sql: field =1
     */
    @Test
    public void term() {
        QueryBuilder qb = termQuery("name.keyword", "张三");
//        QueryBuilder qb = termQuery("name", "张三");//不行 name 是text类型
        esBaseUtil.excuteQuery(qb);
        //sout：{"address":"benjing","age":"1","name":"张三"}
    }

    /**
     * 1.2、精准in查询
     * sql: field in(1,2)
     */
    @Test
    public void terms() {
        QueryBuilder qb = termsQuery("name.keyword", "张三","李四");
        esBaseUtil.excuteQuery(qb);
        //sout：
        // {"address":"benjing","age":"1","name":"张三"}
        //{"address":"benjing","age":"50","name":"李四"}
        //{"address":"benjing","age":"50","name":"李四","num":31}
        //{"address":"benjing","age":"50","name":"李四"}
    }

    /**
     * 1.3、范围查询
     * sql: field > 1 and field<30
     * gt[from] 大于 如果是数值、日期类型 按照范围，keyword是按照字母范围
     */
    @Test
    public void range() {
        QueryBuilder qb = rangeQuery("age.keyword")
                .from(1)
                .to(30)
                .includeLower(true)
                .includeUpper(false)
                ;

        esBaseUtil.excuteQuery(qb);
        //sout：
        //{"address":"benjing","age":"1","name":"张三"}
        //{"address":"benjing","age":"12","name":"张三2"}
        //{"address":"benjing","age":"100","name":"李白"}
        //{"address":"benjing","age":"12","name":"王五","num":3}
        //{"address":"benjing","age":"100","name":"李白","num":301}
        //{"address":"benjing","age":"12","name":"李宏旭 张三3","num":12}
        //{"address":"benjing","age":"12","name":"张三3"}
        //{"address":"benjing","age":"12","name":"王五"}
        //{"address":"benjing","age":"12","name":"aa张三3","num":12}
        //{"address":"benjing","age":"12","name":"张爱三","num":12}
    }

    /**
     * 1.4、存在查询
     * sql: field is not null
     */
    @Test
    public void exists() {
        QueryBuilder qb = existsQuery("num");
        esBaseUtil.excuteQuery(qb);
    }
    /**
     * 1.5、前缀查询
     * 相当于sql：field like '张*'
     */
    @Test
    public void prefix() {
        QueryBuilder qb = prefixQuery("name.keyword","张三");
        esBaseUtil.excuteQuery(qb);
        //sout：
        // {"address":"benjing","age":"1","name":"张三"}
        //{"address":"benjing","age":"12","name":"张三2"}
        //{"address":"benjing","age":"12","name":"张三3"}
    }
    /**
     * 1.6、通配符插叙
     * 【相当于sql：field like '张_三*'】 中间含有一个字，后面随意
     */
    @Test
    public void wildcard() {
        QueryBuilder qb = wildcardQuery("name.keyword","张?三*");
        esBaseUtil.excuteQuery(qb);
        //sout：
        // {"address":"benjing","age":"12","name":"张爱三","num":12}
    }
    /**
     * 1.7、正则表达式匹配
     */
    @Test
    public void regexp() {
        QueryBuilder qb = regexpQuery("name.keyword","张.*");
        esBaseUtil.excuteQuery(qb);
    }
    /**
     * 1.8、有条件模糊匹配,可以错几个
     */
    @Test
    public void fuzzy() {
        QueryBuilder qb = fuzzyQuery("name.keyword","张爱三").fuzziness(Fuzziness.ONE);//可以错一个
        //sout
        //{"address":"benjing","age":"12","name":"张爱三","num":12}
        //{"address":"benjing","age":"1","name":"张三"}

        //QueryBuilder qb = fuzzyQuery("name.keyword","张爱三").fuzziness(Fuzziness.TWO);//可以错两
        //QueryBuilder qb = fuzzyQuery("name.keyword","张爱三").fuzziness(Fuzziness.AUTO);//不能错
        esBaseUtil.excuteQuery(qb);
    }
    /**
     * 1.9、查找指定类型的文档。
     * select * from table
     */
    @Test
    public void type() {
        QueryBuilder qb = typeQuery("form");
        //sout
        esBaseUtil.excuteQuery(qb);
    }

    /**
     * 1.10、类型主键查询
     * select * from table where id in (1,2)
     */
    @Test
    public void idsQueryAdd() {
        QueryBuilder qb = idsQuery("form").addIds("81f7a567-de78-4c3a-8a18-d0844d037a97");
        esBaseUtil.excuteQuery(qb);
        //sout
        //{"address":"benjing","age":"12","name":"张三2"}
    }



}
