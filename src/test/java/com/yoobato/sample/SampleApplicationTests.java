package com.yoobato.sample;

import com.yoobato.sample.controller.ItemController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SampleApplicationTests {

	@Autowired
	private ItemController itemController;

	@Test
	void contextLoads() {
		assert(itemController != null);
	}
}
