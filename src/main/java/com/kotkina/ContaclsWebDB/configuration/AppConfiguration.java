package com.kotkina.ContaclsWebDB.configuration;

import com.kotkina.ContaclsWebDB.repositories.ContactRepository;
import com.kotkina.ContaclsWebDB.repositories.JdbcContactRepository;
import com.kotkina.ContaclsWebDB.repositories.JooqContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Slf4j
public class AppConfiguration {

    @Value("${app.jooq}")
    private boolean useJooq;

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public ContactRepository contactRepository() {
        if (useJooq) {
            log.info("JOOQ is used");
            return new JooqContactRepository(dslContext);
        }
        log.info("JDBC is used");
        return new JdbcContactRepository(jdbcTemplate);
    }
}
