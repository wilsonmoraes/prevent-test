package br.com.crdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static java.lang.String.format;

/**
 * @author romeh
 * the db spring configuration to use in production , to be replaced with actual production configuration , that is for local run only
 */
@Configuration
@EnableTransactionManagement
public class DbConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        /*ds.setUrl(format("jdbc:postgresql://%s:%s/%s", "192.168.10.22", 5433, "teste_wilson"));*/
        ds.setUrl(format("jdbc:postgresql://%s:%s/%s", "localhost", 5432, "prevent_log"));
        ds.setUsername("mymixtapez");
        ds.setPassword("xpto1234");
        return ds;
    }

}
