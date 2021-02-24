package ducnguyen.springredditproject;

import ducnguyen.springredditproject.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SpringredditprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringredditprojectApplication.class, args);
	}

}
