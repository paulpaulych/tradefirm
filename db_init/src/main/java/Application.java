import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
        log.error("hey pisya");
        //        Class.forName("org.postgres.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:postgres:localhost:5432/trade-firm", "user", "changeme");
    }
}
