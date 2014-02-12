package com.djl.jcx.data;

import com.djl.jcx.data.dao.*;
import com.djl.jcx.data.model.*;
//import com.raycom.support.convert.DateConvert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * User: Administrator
 * Date: 13-2-19
 * Time: 下午2:32
 */
public class IbatisTest {
    //    private static SqlMapClient sqlMapClient;
    private static ITypeDao typeDao;
    private static ISuitDao suitDao;
    private static IFactoryDao factoryDao;
    private static IDepositoryDao depositoryDao;
    private static IAdminDao adminDao;
    private static IAccessDao accessDao;

    @BeforeClass
    public static void setUpClass() {
        String[] configLocations = new String[]{
                "classpath:com/djl/jcx/data/sqlmaps/applicationContext-resources.xml",
                "classpath:com/djl/jcx/data/sqlmaps/applicationContext-ibatis.xml"};
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext(configLocations);
//        sqlMapClient = ctx.getBean("sqlMapClient", SqlMapClient.class);
        typeDao = ctx.getBean("typeDao", ITypeDao.class);
        suitDao = ctx.getBean("suitDao", ISuitDao.class);
        factoryDao = ctx.getBean("factoryDao", IFactoryDao.class);
        depositoryDao = ctx.getBean("depositoryDao", IDepositoryDao.class);
        adminDao = ctx.getBean("adminDao", IAdminDao.class);
        accessDao = ctx.getBean("accessDao", IAccessDao.class);
    }

    @Before
    public void setUp() throws SQLException {
        typeDao.createTable();
        suitDao.createTable();
        factoryDao.createTable();
        depositoryDao.createTable();
        adminDao.createTable();
        accessDao.createTable();
    }

    @After
    public void tearDown() throws SQLException {
        typeDao.dropTable();
        suitDao.dropTable();
        factoryDao.dropTable();
        depositoryDao.dropTable();
        adminDao.dropTable();
        accessDao.dropTable();
    }

    @Test
    public void testSqlMapClientTemplate() throws ParseException {
        // 测试MODEL
        FactoryModel factory1 = new FactoryModel("", "香黛坊", "XDF", "", "");
        FactoryModel factory2 = new FactoryModel("", "家宜地毯", "JYDT", "", "");

        TypeModel type1 = new TypeModel("沙发垫", "SFD");
        TypeModel type2 = new TypeModel("地毯", "DT");

        SuitModel suit1 = new SuitModel("", factory1, String.valueOf(new Date().getTime()), "夏威夷", "XWY", 100.00f, 150.00f, "1", type1, "座");
        SuitModel suit2 = new SuitModel("", factory1, String.valueOf(new Date().getTime()), "春天", "CT", 100.00f, 150.00f, "1", type1, "座");
        SuitModel suit3 = new SuitModel("1.4 * 2", factory2, String.valueOf(new Date().getTime()), "斑马", "BM",100.00f, 150.00f, "1",
                type1, "张");

//        DepositoryModel depository1 = new DepositoryModel(suit1, 0);
//        DepositoryModel depository2 = new DepositoryModel(suit2, 0);
//        DepositoryModel depository3 = new DepositoryModel(suit3, 0);

        AdminModel adminFXL = new AdminModel("", "fengxili", "123456");
        AdminModel adminDJL = new AdminModel("", "dingjinlin", "123456");

//        AccessModel accessFXL_STORAGE = new AccessModel(AccessModel.EAction.STORAGE, adminFXL, 10, suit1);
//        AccessModel accessFXL_SOLD = new AccessModel(AccessModel.EAction.SOLD, adminFXL, 10, suit1);
//        AccessModel accessDJL_STORAGE = new AccessModel(AccessModel.EAction.STORAGE, adminDJL, 10, suit2);
//        AccessModel accessDJL_SOLD = new AccessModel(AccessModel.EAction.SOLD, adminDJL, 10, suit2);

        // 测试增加
        int adminId;
        adminId = adminDao.save(adminFXL);
        System.out.println("add admin id = " + adminId);
        adminId = adminDao.save(adminDJL);
        System.out.println("add admin id = " + adminId);

        int factoryId;
        factoryId = factoryDao.save(factory1);
        System.out.println("add layout id = " + factoryId);
        factoryId = factoryDao.save(factory2);
        System.out.println("add layout id = " + factoryId);

        int typeId;
        typeId = typeDao.save(type1);
        System.out.println("add type id = " + typeId);
        typeId = typeDao.save(type2);
        System.out.println("add type id = " + typeId);

        int suitId;
        int depositoryId;

        suitId = suitDao.save(suit1);
        System.out.println("add suit id = " + suitId);
        depositoryId = depositoryDao.save(new DepositoryModel(0, suit1));
        System.out.println("add depository id = " + depositoryId);

        suitId = suitDao.save(suit2);
        System.out.println("add suit id = " + suitId);
        depositoryId = depositoryDao.save(new DepositoryModel(0, suit2));
        System.out.println("add depository id = " + depositoryId);

        suitId = suitDao.save(suit3);
        System.out.println("add suit id = " + suitId);
        depositoryId = suitId = depositoryDao.save(new DepositoryModel(0, suit3));
        System.out.println("add depository id = " + depositoryId);

        // 测试修改
        Date date1 = new Date();
        depositoryDao.add(suit1, 10);
        accessDao.save(new AccessModel(AccessModel.EAction.STORAGE, adminFXL, 10, date1, suit1));
        depositoryDao.add(suit2, 10);
        accessDao.save(new AccessModel(AccessModel.EAction.STORAGE, adminFXL, 10, date1, suit2));
        depositoryDao.add(suit3, 10);
        accessDao.save(new AccessModel(AccessModel.EAction.STORAGE, adminFXL, 10, date1, suit3));

        Date date2 = new Date();
        depositoryDao.add(suit1, 10);
        accessDao.save(new AccessModel(AccessModel.EAction.STORAGE, adminDJL, 10, date2, suit1));
        depositoryDao.add(suit2, 10);
        accessDao.save(new AccessModel(AccessModel.EAction.STORAGE, adminDJL, 10, date2, suit2));
        depositoryDao.add(suit3, 10);
        accessDao.save(new AccessModel(AccessModel.EAction.STORAGE, adminDJL, 10, date2, suit3));

        Date date3 = new Date();
        depositoryDao.subtract(suit1, 1);
        accessDao.save(new AccessModel(AccessModel.EAction.SOLD, adminDJL, 1, date3, suit1));
        depositoryDao.subtract(suit2, 1);
        accessDao.save(new AccessModel(AccessModel.EAction.SOLD, adminDJL, 1, date3, suit2));
        depositoryDao.subtract(suit3, 1);
        accessDao.save(new AccessModel(AccessModel.EAction.SOLD, adminDJL, 1, date3, suit3));

        Date date4 = new Date();
        depositoryDao.subtract(suit1, 1);
        accessDao.save(new AccessModel(AccessModel.EAction.SOLD, adminFXL, 1, date4, suit1));
        depositoryDao.subtract(suit2, 1);
        accessDao.save(new AccessModel(AccessModel.EAction.SOLD, adminFXL, 1, date4, suit2));
        depositoryDao.subtract(suit3, 1);
        accessDao.save(new AccessModel(AccessModel.EAction.SOLD, adminFXL, 1, date4, suit3));

        // 测试查询
        List<TypeModel> typeList = typeDao.listAll();
        List<FactoryModel> factoryList = factoryDao.listAll();
        List<SuitModel> suitList = suitDao.listAll();

        DepositoryModel depository;
        depository = depositoryDao.queryOneBySuit(suit1);
        depository = depositoryDao.queryOneBySuit(suit2);
        depository = depositoryDao.queryOneBySuit(suit3);

        List<DepositoryModel> depositoryList;
        depositoryList = depositoryDao.listAllByAmount(18);
        depositoryList = depositoryDao.listAllByAmountGreater(17);
        depositoryList = depositoryDao.listAllByAmountLess(20);
        depositoryList = depositoryDao.listAllByFactory(factory1);
        depositoryList = depositoryDao.listAllByFactory(factory2);
        depositoryList = depositoryDao.listAllByType(type1);
        depositoryList = depositoryDao.listAllByType(type2);

        List<AccessModel> accessList;
        accessList = accessDao.listAll();
        accessList = accessDao.listAllByAction(AccessModel.EAction.STORAGE);
        accessList = accessDao.listAllByAction(AccessModel.EAction.SOLD);
        accessList = accessDao.listAllByAdmin(adminFXL);
        accessList = accessDao.listAllByAdmin(adminDJL);
        accessList = accessDao.listAllBySuit(suit1);
        accessList = accessDao.listAllBySuit(suit2);
        accessList = accessDao.listAllBySuit(suit3);

//        date1 = DateConvert.dayParse("2013-3-19");
//        date2 = DateConvert.dayParse("2013-3-21");
//        AccessModel access = new AccessModel();
//        access.setDateCondition1(date1);
//        access.setDateCondition2(date2);
//        accessList = accessDao.listByModel(access);

        // 测试删除
        typeDao.delete(type1.getId());
        typeDao.delete(type2.getId());

        factoryDao.delete(factory1.getId());
        factoryDao.delete(factory2.getId());

        suitDao.delete(suit1.getId());
        depositoryDao.delete(suit1.getId());
        suitDao.delete(suit2.getId());
        depositoryDao.delete(suit2.getId());
        suitDao.delete(suit3.getId());
        depositoryDao.delete(suit3.getId());

        adminDao.delete(adminDJL.getId());
        adminDao.delete(adminFXL.getId());
    }


}
