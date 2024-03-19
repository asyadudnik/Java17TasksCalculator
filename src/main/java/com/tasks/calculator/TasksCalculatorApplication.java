package com.tasks.calculator;

import com.tasks.calculator.dto.User;
import com.tasks.calculator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import static com.tasks.calculator.global.InstallConstants.PASS;
import static com.tasks.calculator.global.InstallConstants.USER;

@Slf4j
@SpringBootApplication
public class TasksCalculatorApplication implements CommandLineRunner {

	private final ApplicationContext applicationContext;
	private final UserService userService;

    public TasksCalculatorApplication(ApplicationContext applicationContext, UserService userService) {
        this.applicationContext = applicationContext;
        this.userService = userService;
    }


    public static void main(String[] args) {
		SpringApplication.run(TasksCalculatorApplication.class, args);
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {

		var templateResolver = new SpringResourceTemplateResolver();

		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {

		var templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);

		return templateEngine;
	}


	@Override
	public void run(String... args){
		User user = new User(USER, PASS);

		User newUser = this.userService.save(user);
		if (newUser != null) {
			log.info("springUser saved...");
		}
	}
}
