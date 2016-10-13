package cn.zlion;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by zzs on 10/12/16.
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "postgresql")
    @Qualifier("postgresql")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.postgresql")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oracle")
    @Qualifier("oracle")
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresqlJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("postgresql")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("oracle")DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
