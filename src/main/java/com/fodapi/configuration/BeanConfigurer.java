package com.fodapi.configuration;

import com.fodapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BeanConfigurer {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(env.getProperty("db.driver"));
        source.setUrl(env.getProperty("db.host"));
        source.setUsername(env.getProperty("db.user"));
        source.setPassword(env.getProperty("db.pass"));
        return source;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource());
        return namedParameterJdbcTemplate;
    }

    @Bean
    public JwtService jwtService(){
        return new JwtService();
    }

    @Bean
    public JavaMailSenderImpl mailSenderBean() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(1025);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "false");

        return mailSender;
    }
}
