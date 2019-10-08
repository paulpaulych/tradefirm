import config.DbConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private DbConfiguration dbConfiguration;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println(dbConfiguration.getUrl());
    }

    @Autowired
    public void setDbConfiguration(DbConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }
}
