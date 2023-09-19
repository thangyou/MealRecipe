package doubleni.mealrecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // created_at, updated_at 자동 업데이트
@SpringBootApplication
public class MealRecipeApplication {
    // 스프링 부트를 실행할 용도의 클래스 - Application
    public static void main(String[] args) {
        SpringApplication.run(MealRecipeApplication.class, args);
    }

}
