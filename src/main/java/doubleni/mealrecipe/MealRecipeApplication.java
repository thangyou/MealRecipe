package doubleni.mealrecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EnableJpaAuditing // createdDate, updatedDate 자동 업데이트
@SpringBootApplication
@ComponentScan(basePackages = "doubleni")
@EnableJpaRepositories(basePackages = "doubleni") // jpaRepository를 상속한 repository scan
@EntityScan(basePackages = "doubleni") // @Entity 클래스 scan
@Configuration
public class MealRecipeApplication {
    // 스프링 부트를 실행할 용도의 클래스 - Application
    public static void main(String[] args) {
        SpringApplication.run(MealRecipeApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
