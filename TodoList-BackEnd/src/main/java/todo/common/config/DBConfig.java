package todo.common.config;

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

// 기능 설정 어노테이션
@Configuration
@PropertySource("classpath:/config.properties") 
/** github 에 올리지 않고, 이메일, 이름, 비밀번호와 같이 암호화하여 사용해야 하는 설정을 가지고 오는 것이다.
 *  Property: 자산. 개발자가 사용자에게 인증번호를 보낼 이메일이나, 이메일 비밀번호 또는 DB id, pw, 주소 등과 같이,
 *  개발 주체가 비공개로 보호해야 하는 자산을 작성하는 공간이다.**/
public class DBConfig {
	
	@Autowired
	private ApplicationContext applicationContext; 
	// 현재 만든 TodoList-BackEnd 라는 폴더 흐름을 잡아주는 것이다.
	// TodoList-BackEnd 폴더 == Application == 폴더 안에 작성한 파일이 하나의 app 이나 웹에서 작동하는 파일이 되는 것이다.
	// Java, JavaScript 코드로 작성한 파일을 exe 와 같은 확장자로 만들어, 사용자들이 다운로드 하고 실행할 수 있는 프로그램을 만들 수 있다.
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() { // hikari == DataBase 를 연결하기 위해 이용하는 기능이다.
		return new HikariConfig(); // hikari 와 같은 외부 기능을 사용하지 않으면 코드가 불필요하게 길어진다.
	}
	
	// 연결된 DataBase 를 Spring 에서 인지하고, 관리할 것임을 표기한 것이다.
	@Bean
	public DataSource dataSource(HikariConfig config) {
		DataSource dataSource = new HikariDataSource(config);
		return dataSource;
	}
	
	/** myBatis 설정 추가
	 * @throws Exception **/
	@Bean
	public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
		sfb.setDataSource(dataSource);
		
		// Select Insert Update Delete 가 작성된 mapper 파일이 모여있는 폴더 경로 설정
		// src/main/resources 바로 하위에 있는 mappers 폴더 안에 작성된
		// xml 로 끝나는 모든 파일을 가리키겠다는 **(모두 가리키기) 표기 작성
		// classpath == src/main/resources
		sfb.setMapperLocations(applicationContext.getResources("classpath:/mappers.**xml"));
		
		// DTO 모델이 모여있는 패키지 설정
		// Alias == 별칭, Aliases == 별칭들
		// DataBase 에 작성한 칼럼명과, DTO 에 작성한 칼럼명이 서로 다를 때, 특정 별칭과 특정 칼렴명이 일치한다는 것을 명시하기 위해
		// DTO 가 위치한 폴더(Package) 를 작성해 주는 것이다. 
		sfb.setTypeAliasesPackage("todo.dto"); // 내 dto 패키지명으로 설정해 주어야 한다.
		
		// myBatis 에서, DB 와 칼럼에 특정 설정을 하고, 설정에 대한 정보를 어느 위치에 작성했는지
		// myBatis 설정 경로와 파일명을 작성한다.
		sfb.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml")); 
		// 경로 또는 파일명이 변경된다면, 여기서도 변경해주어야 한다.
		
		return sfb.getObject();
	}
	
	// SQL 을 실행한 다음, insert update delete 실행
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sf) {
		return new SqlSessionTemplate(sf);
	}
	
	// 전반적인 commit rollback 같은 관리를 해주는 트랜잭션 매니저
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	/**
	 * SqlSessionTemplate ◀ insert select update delete 실행
	 * DataSourceTransactionManager ◀ SqlSessionTemplate 을 실행한 결과를 commit 또는 rollback 해준다.
	 *                                DB 에 완벽히 저장하거나, 되돌리는 작업을 해주는 것이다.
	 * **/
}
