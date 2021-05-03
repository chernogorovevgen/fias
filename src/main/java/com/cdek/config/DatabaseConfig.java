package com.cdek.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
public class DatabaseConfig {

    private static final String MYBATIS_MAPPER_XML = "classpath:mybatis/mapper/*.xml";
    private static final String MYBATIS_CONFIG_PATH = "classpath:mybatis/mybatis-config.xml";
    private static final String SCHEMA_DATA_BASE = "public";
    private static final String FLYWAY_LOCATIONS = "classpath:db/migration";
    private static final String SCRIPTS_LOCATIONS = "/test-db/init_db.sql";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource(@Qualifier("hikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(ResourcePatternResolver applicationContext, DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(getMybatisResources(applicationContext));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(MYBATIS_CONFIG_PATH));
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        return new SqlSessionTemplate(Objects.requireNonNull(sqlSessionFactory));
    }

    private Resource[] getMybatisResources(ResourcePatternResolver applicationContext) throws Exception {
        Resource[] mainResources = applicationContext.getResources(MYBATIS_MAPPER_XML);
        List<Resource> result = new ArrayList<>(Arrays.asList(mainResources));
        return result.toArray(new Resource[]{});
    }

    /**
     * Настройка миграции flyway.
     *
     * @param dataSource источник данных
     * @param schema     схема источника данных
     * @return flyway
     */
    @Bean(name = "flyway", initMethod = "migrate")
    public Flyway flyway(
            @Qualifier("dataSource") DataSource dataSource,
            @Value("${spring.flyway.schema:public}") String schema,
            @Value("${spring.flyway.table:schema_version}") String table,
            @Value("${spring.flyway.locations:classpath:/db/migration}") String locations) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .table(table)
                .locations(locations)
                .load();
    }
}
