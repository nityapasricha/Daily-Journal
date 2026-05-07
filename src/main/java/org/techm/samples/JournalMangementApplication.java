package org.techm.samples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.techm.samples.entity.User;
import org.techm.samples.service.UserService;
@SpringBootApplication
public class JournalMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalMangementApplication.class, args);
	}
	@Bean
    public CommandLineRunner createAdmin(UserService userService, PasswordEncoder encoder) {
        return args -> {
            if (!userService.checkEmail("admin@example.com")) {
                User admin = new User();
                admin.setName("admin");
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setMobile("7482673827");
                admin.setPass(encoder.encode("adminlogin"));
                admin.setRole("ADMIN");
                userService.create(admin);
            }      
        };
    }

}
