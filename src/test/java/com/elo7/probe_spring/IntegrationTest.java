package com.elo7.probe_spring;

import com.elo7.probe_spring.repositories.PlateauRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
public class IntegrationTest {
	private static final MySQLContainer MY_SQL_CONTAINER;

	@Autowired
	private PlateauRepository repository;

	static {
		MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:8.0.30"))
				.withDatabaseName("sonda-spring-db")
				.withPassword("123456")
				.withUsername("root");
		MY_SQL_CONTAINER.start();
	}

	@AfterEach
	public void clean() {
		repository.deleteAll();
	}

	@DynamicPropertySource
	public static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", () -> "root");
		registry.add("spring.datasource.password", () -> "123456");
	}
}
