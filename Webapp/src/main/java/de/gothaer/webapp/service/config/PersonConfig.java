package de.gothaer.webapp.service.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PersonConfig {


    @Bean
    //@Lazy
    //@Scope("prototype")
    @Qualifier("antipathen")
    public List<String> antipathen() {
        return List.of("Attila","Peter","Paul","Mary");
    }

    @Bean
    @Qualifier("fruits")
    public List<String> fruits() {
        return List.of("Apple","Banana","Cherry","Raspberry");
    }

    /*
    @Bean
    public PersonService createPersonService(final PersonRepository repo, final PersonMapper  mapper, @Qualifier("antipathen") List<String> antipathen) {
        return new PersonServiceImpl(repo, mapper, antipathen);
    }

     */
}
