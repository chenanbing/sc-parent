package com.cab.commom.utils.josql;

import org.josql.Query;
import org.josql.QueryExecutionException;
import org.josql.QueryParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JoSqlUtils {

    private static Logger logger = LoggerFactory.getLogger(JoSqlUtils.class);

    /**
     *
     * q.parse("select * from com.martin.bean.User where id > : id AND id < : max");
     * q.parse("select * from com.martin.bean.User where name like '%罗%' order by id desc limit 10");
     * q.parse("select * from com.martin.bean.User where age > : min AND age < : max AND sex = : sex AND type = : type order by age desc limit 200,20");
     * @param list
     * @param sql
     * @return
     * @throws
     */
    public static List query(List list, String sql){
        if (null == list || list.isEmpty()){
            return list;
        }
        Query q = new Query();
        try {
            q.parse(sql);
        } catch (QueryParseException e) {
            logger.error("",e);
        }
        try {
            List getList = q.execute(list).getResults();
            return getList;
        } catch (QueryExecutionException e) {
            logger.error("",e);
        }
        return new ArrayList();
    }
}
