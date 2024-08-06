package com.kh.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // SpringBoot 설정하겠다는 애너테이션
@PropertySource("classpath:/config.properties")
public class DBConfig {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean // SpringBoot 에서 객체로 존재한다는 의미이다.
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hc() {
		return new HikariConfig();
	}
	/**
	 * application.properties 또는 config.properties 에 작성해야 할 
	 * hikariConfig 를 java 에서 작성해준 것이다.
	 * 
	 * prefix(시작)="spring.datasource.hikari"(로 시작하는 모든 정보 가져오기)
	 * suffix(끝)
	 * 
	 * .html .svg 등 확장자가 무엇인지 작성한다.
	 * **/
	
	@Bean
	// ▼ 연결에 대한 정보를 담고있다.
	public DataSource ds(HikariConfig hc) {
		DataSource ds = new HikariDataSource(hc);
		return ds;
	}
	
	@Bean
	// ▼ DB 와 Java 에 관련된 추가 설정을 작성해주는 공간이다.
	// DB 칼럼명과 Java 변수명이 서로 일치하지 않을 때, 서로 어떻게 연결시킬 것인지 설정
	// DTO 변수명과 테이블 칼럼명에 대한 설정
	public SqlSessionFactory sf(DataSource ds) throws Exception {
		SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
		sfb.setDataSource(ds);
		sfb.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
		sfb.setTypeAliasesPackage("com.kh.dto"); // model 이 작성되어있는 패키지 폴더의 위치 설정
		// ▼ DB 칼럼명과 Java 변수명이 서로 일치하지 않을 때, 서로 어떻게 연결시킬 것인지 설정
		// DB 칼럼명: member_id // DTO 변수명: memberId
		sfb.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		return sfb.getObject();
	}
	
	@Bean
	// Select Insert Update Delete SQL 관련 설정 작성
	public SqlSessionTemplate st(SqlSessionFactory sf) {
		return new SqlSessionTemplate(sf);
	}
	
	@Bean
	// Transaction: commit, rollback
	// DB 에서 CRUD 작업이 진행되면, 작업에 대한 결과를 관리하는 설정이다.
	public DataSourceTransactionManager dtm(DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}
}
