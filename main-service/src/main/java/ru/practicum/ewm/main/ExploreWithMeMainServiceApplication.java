package ru.practicum.ewm.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"ru.practicum.ewm.stats.httpclient",
		"ru.practicum.ewm.main"
})
public class ExploreWithMeMainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExploreWithMeMainServiceApplication.class, args);
	}

}
