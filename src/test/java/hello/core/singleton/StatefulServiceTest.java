package hello.core.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

  @Test
  void statefulServiceSingleton() {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        TestConfig.class);
    StatefulService statefulService1 = ac.getBean(StatefulService.class);
    StatefulService statefulService2 = ac.getBean(StatefulService.class);

    // ThreadA: A사용자 10,000원 주문
    statefulService1.order("userA", 10_000);
    // ThreadB: B사용자 20,000원 주문
    statefulService1.order("userB", 20_000);

    // ThreadA: 사용자A 주문 금액 조회
    int price = statefulService1.getPrice();
    System.out.println("price = " + price);

    Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20_000);
  }

  static class TestConfig {

    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }
  }
}