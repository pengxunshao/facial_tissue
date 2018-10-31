package com.dida.facialtissue.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.dida.facialtissue.dao.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisDbConfig {
	
	@Autowired
    @Qualifier("facialTissueDataSource")
    private DataSource facialTissueDS;
	
	@Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(facialTissueDS); // 使用titan数据源, 连接titan库
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/mapper/**/*.xml"));
        return factoryBean.getObject();

    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }

    @Bean(name = "transactionManager2")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(facialTissueDS);
    }
}
