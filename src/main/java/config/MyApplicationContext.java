package config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.my.notice.dao", "com.my.notice.service",
							   "com.my.customer.dao", "com.my.customer.service",
							   "com.my.qna.dao", "com.my.qna.service",
							   "com.my.rank.dao", "com.my.rank.service",
							   "com.my.task.dao", "com.my.task.service",
							   "com.my.team.dao", "com.my.team.service",
							   "com.my.util"
							   })
@EnableTransactionManagement
public class MyApplicationContext {

//	@Bean
//	public SimpleDriverDataSource dataSource() {
//		SimpleDriverDataSource sdds = new SimpleDriverDataSource();
//		
//		sdds.setDriverClass(oracle.jdbc.OracleDriver.class);
////		sdds.setUrl("jdbc:oracle:thin:@STUDYPROJECT_medium?TNS_ADMIN=C://opt//OracleCloudWallet//VFX");
//		
//		sdds.setUsername("admin");
//		sdds.setPassword("Kosaproject2023");
//		
//		return sdds;
//		
//	} // SimpleDriverDataSource()
	
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@STUDYPROJECT_medium?TNS_ADMIN=C://opt//OracleCloudWallet//VFX");
		config.setUsername("admin");
		config.setPassword("Kosaproject2023");
		config.setMinimumIdle(3);
		
		return config;
		
	} // hikariconfig()
	
	@Bean 
	public HikariDataSource dataSourceHikari() {
		return new HikariDataSource(hikariConfig());
	} // dataSourceHikari()
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		
		ssfb.setDataSource(dataSourceHikari());
		Resource resource = new ClassPathResource("mybatis-config.xml");
		
		ssfb.setConfigLocation(resource);
		
		return (SqlSessionFactory)ssfb.getObject();
		
	} // sqlSessionFactory()
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSourceHikari());
		
		return tx;
	} // DataSourceTransactionManager()
	
} // end class

/*
@ComponentScan(basePackages = {"com.my.notice.dto", "com.my.notice.dao", "com.my.notice.service",
							   "com.my.customer.dto", "com.my.customer.dao", "com.my.customer.service",
							   "com.my.qna.dto", "com.my.qna.dao", "com.my.qna.service",
							   "com.my.rank.dto", "com.my.rank.dao", "com.my.rank.service",
							   "com.my.task.dto", "com.my.task.dao", "com.my.task.service",
							   "com.my.team.dto", "com.my.team.dao", "com.my.team.service",
							   "com.my.util"
							   })*/