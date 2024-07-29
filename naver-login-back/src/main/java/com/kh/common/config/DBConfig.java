package com.kh.common.config;

import java.io.IOException;

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

// classpath == src/main/resources 의 줄임말과 같다.
@Configuration
@PropertySource("classpath:/config.properties")
public class DBConfig {

	@Autowired
	// import org.springframework.boot.context.properties.ConfigurationProperties;
	private ApplicationContext applicationContext; // 연결되는 주소 관리자로, 나중에 xml 과 같은 경로를 보유하고 관리한다.
	
	@Bean // 객체 생성, Hikari 를 사용하겠다는 선언이다.
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		/*
			config.setJdbcUrl("jdbc:mysql://localhost:3306/KH_WORKBOOK");
			config.setUsername("root");
			config.setPassword("kh1234");
			config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		*/
		return new HikariConfig(); // hikari 라는, DataBase 연결을 도와주는 라이브러리 이다.
	}
	

	@Bean // 객체 생성, DataBase 연결을 해주겠다는 선언이다.
	public DataSource dataSource(HikariConfig config) {
		DataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
		sfb.setDataSource(dataSource); // HikariConfig 에서 받은 정보로 연결한 DataBase 연결 경로를 가져와서 사용한다는 것이다.
		sfb.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
		sfb.setTypeAliasesPackage("com.kh.dto"); // 나중에 본인의 dto 패키지명으로 변경해주어야 하고, DataBase 에 작성한 칼럼 값과 dto 에 작성한 변수명을 대조하는 데에 사용된다.
		
		// 나중에 dto 에서 칼럼 명을 camelCase 또는 dto 용법으로 작성했을 때, 작성 값 설정
		sfb.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		return sfb.getObject();
	}
	
	@Bean // SQL 에서 작성한 select insert update delete 를 이용한 DataBase 작업을 관리한다.
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sf) {
		return new SqlSessionTemplate(sf);
	}
	
	@Bean // commit 또는 rollback 과 같이, 변경 사항이 있을 때(삽입/수정/삭제) DB 에 완전하게 저장하거나, 되돌릴 수 있도록 도와준다.
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource ds) {
		return new DataSourceTransactionManager(ds);
		// insert update delete commit 을 하지 않으면, 완벽하게 저장되지 않은 상태에서 select 를 진행하게 되기 때문에,
		// 저장을 하지 않은 상태여서 보이지 않는 것이라 여기지 않고, 코드에 문제가 있다고 여겨질 수 있기 때문에 commit 진행 매니저를 생성해 준 것이다.
	}
}
