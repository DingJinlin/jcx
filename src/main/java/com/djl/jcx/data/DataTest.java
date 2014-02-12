package com.djl.jcx.data;

import com.djl.jcx.data.dao.ISuitDao;
import com.djl.jcx.data.model.SuitModel;
import com.djl.jcx.db.service.SuitService;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sheep
 * Date: 14-2-12
 * Time: 下午5:57
 * To change this template use File | Settings | File Templates.
 */
public class DataTest {
    static String[] configLocations = new String[]{
            "classpath:applicationContext.xml"
    };

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);
        SuitService suitService = ctx.getBean("suitService", SuitService.class);
        SuitModel suits = suitService.selectByMaxId();
        System.out.println("");

//        Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DataTest.class.getResource("/").getPath() + "jcx.s3db");
//        String sql = "select * from t_suit";
//        Statement stat = conn.createStatement();
//        ResultSet rs = stat.executeQuery(sql);
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            System.out.println("id: " + id);
//        }
//        System.out.println("");


    }



}
