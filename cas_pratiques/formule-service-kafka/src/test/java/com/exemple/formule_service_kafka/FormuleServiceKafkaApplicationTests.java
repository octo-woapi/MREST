package com.exemple.formule_service_kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(SpringTestConfiguration.class)
class FormuleServiceKafkaApplicationTests {

  @Test
  void contextLoads() {}
}
