package com.djl.jcx.ui;

import com.djl.jcx.ui.layout.*;
import com.spring.SpringContextHelperHandler;
import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.*;

/**
 * User: Administrator
 * Date: 13-3-13
 * Time: 下午1:29
 */
public class MyVaadinApplication extends Application {
    Window window;
    Button butPrint;
    TabSheet tabSheet;

    @Override
    public void init() {
        window = new Window("MyVaadinApplication");
//        subWindow = initSubWindow();
        setMainWindow(window);

        new SpringContextHelperHandler(this);
        butPrint = new Button("打印");
        butPrint.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                butPrint.getWindow().executeJavaScript("print();");

                Window window = new Window("Window to Print");
                window.addComponent(new Label("<style media=\"print\">" +
                        ".Noprint{display:none}</style>",
                        Label.CONTENT_XHTML));
                // Have some content to print
                window.addComponent(new Label(
                        "<h1>Here's some dynamic content</h1>\n" +
                                "<p>This is to be printed to the printer.</p>",
                        Label.CONTENT_XHTML));
                // Add the printing window as a new application-level
                // window
                butPrint.getApplication().addWindow(window);
                // Open it as a popup window with no decorations
                butPrint.getWindow().open(new ExternalResource(window.getURL()),
                        "_blank", 500, 600,  // Width and height
                        Window.BORDER_NONE); // No decorations
                // Print automatically when the window opens.
                // This call will block until the print dialog exits!
                window.executeJavaScript("print();");
                // Close the window automatically after printing
                window.executeJavaScript("self.close();");
            }
        });
        Layout layout = initTableSheet();

        window.addComponent(new Label("Hello, world!"));
        window.addComponent(layout);
    }

    /**
     * 初始化标签表
     * @return
     */
    private Layout initTableSheet() {
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(false);
        tabSheet = new TabSheet();

        vLayout.addComponent(tabSheet);

//        tabSheet.setHeight("600px");
//        tabSheet.setWidth("100%");
        vLayout.setSizeFull();

        Layout layFactory = new FactoryInfo();
        Layout laySuit = new SuitInfo();
        Layout layType = new TypeInfo();
        Layout layAddDepository = new DepositoryAddInfo();
        Layout layDepository = new DepositoryInfo();
        Layout laySellingInfo = new SellingInfo();
        Layout layAccessInfo = new AccessInfo();


        tabSheet.addTab(layFactory, FactoryInfo.S_TAB_CAPTION);
        tabSheet.addTab(laySuit, SuitInfo.S_TAB_CAPTION);
        tabSheet.addTab(layType, TypeInfo.S_TAB_CAPTION);
        tabSheet.addTab(layAddDepository, DepositoryAddInfo.S_TAB_CAPTION);
        tabSheet.addTab(layDepository, DepositoryInfo.S_TAB_CAPTION);
        tabSheet.addTab(laySellingInfo, SellingInfo.S_TAB_CAPTION);
        tabSheet.addTab(layAccessInfo, AccessInfo.S_TAB_CAPTION);

        tabSheet.addListener(new SelectTableListener());

        return vLayout;
    }

    private class SelectTableListener implements TabSheet.SelectedTabChangeListener {

        @Override
        public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
            TabSheet tabsheet = event.getTabSheet();
            TabSheet.Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
            Component component = tab.getComponent();
            if(component instanceof ITableInfo) {
                ((ITableInfo) component).refreshTable();
            }
        }
    }
}