package com;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: Ding
 * Date: 13-3-21
 * Time: 下午9:38
 */
public class taseTable extends Application {
    private Window mw;//应用的主界面

    private Label rows;//现实当前批量数据的记录数
    private JDBCConnectionPool connectionPool = null;//数据库连接池
    private SQLContainer htContainer = null;//数据组件，类似于PowerBuilder的DataStore
    boolean editMode = false;//数据组件当前处于编辑状态，还是数据浏览状态

    //初始化数据组件
    private void initContainers() {
        try {
            //建立数据库连接池，这里只是演示的目的，实际生产系统可以使用其他数据库连接池，例如c3p0
            connectionPool = new SimpleJDBCConnectionPool(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/test?Unicode=true&characterEncoding=UTF-8",
                    "root", "admin", 2, 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //设置数据库中一个table作为数据组件的数据源，这里是hugetable
        try {
            TableQuery q1 = new TableQuery("hugetable", connectionPool);
            htContainer = new SQLContainer(q1);
            //应用服务器上数据组件的数据缓冲大小，通常设置为用户界面中显示行数的5倍，默认是100
            //该设置影响访问数据库的次数，如果数据库服务器压力大，这个数可以调大，减少数据库的访问次数
            htContainer.setPageLength(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //这个方法是Application必须的，用于初始化Vaadin应用系统
    @Override
    public void init() {
        //初始化数据组件
        initContainers();
        //建立应用的主界面，这个窗口就是浏览器中的主界面
        mw = new Window("快速浏览批量数据-QuickGrid");
        //界面顶部显示一个标题和一个按钮
        com.vaadin.ui.HorizontalLayout h = new HorizontalLayout();
        h.setSpacing(true);
        h.setMargin(true);
        Label title = new Label("批量数据浏览、编辑实例");
        title.setStyleName("bigfont");
        h.addComponent(title);
        NativeButton helloButton = new NativeButton("你好");
        helloButton.setWidth("40px");
        helloButton.setHeight("40px");
        helloButton.setStyleName("bigfont");
        helloButton.addListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getMainWindow().showNotification("Hello, 我是批量浏览、编辑数据的Portal。");
            }
        });
        h.addComponent(helloButton);
        mw.addComponent(h);
        //构造一个表格组件，用于显示或编辑批量数据
        final Table ht = new Table();
        ht.setSizeFull();//宽度100%
        ht.setPageLength(25);//表格显示25行数据
        ht.setReadOnly(false);//不是只读
        ht.setSelectable(true);//表格中的行可以选择
        //设置UI组件的数据源
        ht.setContainerDataSource(htContainer);
        //设置表格的标题
        ht.setCaption("快速数据浏览");
        //设置表格的栏目标题
        ht.setColumnHeaders(new String[] { "ID", "姓名", "年龄", "住址", "身份证号码" });
        //将表格加入的主界面
        mw.addComponent(ht);
        //插入数据按钮
        Button b = new Button("insert 10000 rows");
        b.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                for (int i = 0; i < 10000; i++) {
                    Object itemid = htContainer.addItem();
                    Item item = htContainer.getItem(itemid);
                    double r = Math.random() * 10000;
                    item.getItemProperty("name").setValue("里斯" + r);
                    item.getItemProperty("age").setValue(
                            Math.round(100 * Math.random()));
                    item.getItemProperty("address").setValue("山东大学");
                    item.getItemProperty("idno").setValue("371012198887122217");
                }
                try {
                    htContainer.commit();
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                rows.setValue("rows:" + htContainer.size());
            }
        });
        //按钮放到一行上
        HorizontalLayout vl = new HorizontalLayout();
        vl.addComponent(b);
        //删除所有数据的按钮
        Button b2 = new Button("删除所有数据");
        b2.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    Connection c = connectionPool.reserveConnection();
                    Statement st = c.createStatement();
                    st.executeUpdate("delete from hugetable");
                    c.commit();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                htContainer.refresh();
                rows.setValue("rows:" + htContainer.size());
            }
        });
        vl.addComponent(b2);
        //保存按钮
        final Button b4 = new Button("保存");
        b4.setEnabled(false);
        b4.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    htContainer.commit();
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        vl.addComponent(b4);
        //撤销修改的按钮
        final Button b5 = new Button("撤销修改");
        b5.setEnabled(false);
        b5.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    htContainer.rollback();
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        vl.addComponent(b5);
        //刷新按钮
        Button b6 = new Button("刷新");
        b6.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                //htContainer.refresh();
                ht.refreshRowCache();
            }
        });
        vl.addComponent(b6);
        //进入编辑模式的按钮
        final Button b3 = new Button("进入编辑数据模式");
        vl.addComponent(b3);
        b3.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (editMode) {
                    ht.setEditable(false);
                    b3.setCaption("进入编辑数据模式");
                } else {
                    ht.setEditable(true);
                    b3.setCaption("进入浏览数据模式");
                }
                editMode = !editMode;
                if (editMode){
                    b4.setEnabled(true);
                    b5.setEnabled(true);
                }else{
                    b4.setEnabled(false);
                    b5.setEnabled(false);
                }
            }
        });

        //显示数据的总行数
        rows = new Label("rows:" + htContainer.size());
        vl.addComponent(new Label(rows));
        //将按钮行加入界面
        mw.addComponent(vl);
        ht.addListener(new ItemClickEvent.ItemClickListener(){
            @Override
            public void itemClick(ItemClickEvent event) {
                getMainWindow().showNotification(""+event.getItem().getItemProperty("name"));
            }
        });
        //设置应用的主界面
        setMainWindow(mw);
        //设置应用的外观主题
        this.setTheme("quickgrid");
    }

    //这里是设置vaadin的session timeout信息
    public static SystemMessages getSystemMessages() {
        return customizedSystemMessages;
    }

    static CustomizedSystemMessages customizedSystemMessages = new CustomizedSystemMessages();
    static {//不让vaadin显示超时信息，这种设置会产生一个很好的效果：当超时时，点击界面的一个控件（可点击的）会引起刷新
        customizedSystemMessages.setSessionExpiredCaption(null);
        customizedSystemMessages.setSessionExpiredMessage(null);
    }
}
