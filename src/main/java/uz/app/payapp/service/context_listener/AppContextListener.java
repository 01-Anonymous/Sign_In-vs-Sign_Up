package uz.app.payapp.service.context_listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import uz.app.payapp.db.Database;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Database.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Database.getInstance().close();
    }
}
