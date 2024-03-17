package note.javadeveloper.configuration;

import note.javadeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@ComponentScan(value = "note.javadeveloper")
@PropertySource(value = "classpath:application.properties")
public class AppConfig {

}
