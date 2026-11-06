package com.company.api.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.company.test.TestApplication;

@SpringBootTest(
		classes = TestApplication.class,
		properties = "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
class TestApplicationTests {

	@Test
	void contextLoads() {
	}

}
