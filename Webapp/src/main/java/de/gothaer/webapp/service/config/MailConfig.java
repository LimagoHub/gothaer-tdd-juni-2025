package de.gothaer.webapp.service.config;


import de.gothaer.webapp.YamlPropertySourceFactory;
import de.gothaer.webapp.service.mail.MailConnector;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value= "classpath:mail.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "mail")
@Setter
public class MailConfig {

    private String smtp;
    private String username;
    private String password;

    @Bean
    public MailConnector mailConnector() {
        return MailConnector.builder().smtp(smtp).username(username).password(password).build();
    }
}
