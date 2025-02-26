package com.juwon.sftp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SftpApplicationTests {

	@Test
	void contextLoads() {
	}
	@BeforeAll
	static void setup() {
		System.out.println("@BeforeAll - executes once before all test methods in this class");
	}

	@BeforeEach
	void init() {
		System.out.println("@BeforeEach - executes before each test method in this class");
	}

	@AfterEach
	void tearDown() {
		System.out.println("@AfterEach - executed after each test method.");
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("@AfterAll - executed after all test methods.");
	}

}
