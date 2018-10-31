package com.dida.facialtissue.config;

import com.dida.facialtissue.commons.RedisTemplateHelper;
import com.dida.facialtissue.util.RequestUtil;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {

	 // 资源DB
    @Bean(name = "facialTissueDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.facialtissue") // application.properteis中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
	
    @Bean
    public RequestUtil TaidiiRequestUtil() {
    	return new RequestUtil();
    }
    
    @Bean
	public RedisTemplateHelper getRedisTemplateHelper()
	{
		return new RedisTemplateHelper();
	}
    
}
