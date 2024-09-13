package me.zhukov.hogwarts.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HogwartsConfig {
    @Bean
    public GroupedOpenApi facultyGroup() {
        return GroupedOpenApi.builder()
                .group("контроллеры факультетов")
                .pathsToMatch("/faculty/**")
                .build();
    }

    @Bean
    public GroupedOpenApi studentGroup() {
        return GroupedOpenApi.builder()
                .group("контроллеры студентов")
                .pathsToMatch("/student/**")
                .build();
    }

    @Bean
    public GroupedOpenApi group() {
        return GroupedOpenApi.builder()
                .group("все контроллеры")
                .pathsToMatch("/**")
                .build();
    }
}
