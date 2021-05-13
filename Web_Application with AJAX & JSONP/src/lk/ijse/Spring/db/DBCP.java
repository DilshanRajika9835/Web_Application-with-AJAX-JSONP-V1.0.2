package lk.ijse.Spring.db;/*@author:Dilshan Rajika Withanachchi*/

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBCP implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost/webapplication");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("ijse");
        basicDataSource.setMaxTotal(3);
        basicDataSource.setInitialSize(2);
        servletContextEvent.getServletContext().setAttribute("ds",basicDataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Stop =========================");
    }
}
