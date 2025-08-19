package AluraChallenge.ApiForoLeonardoCorales;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// imports necesarios:
 import org.springframework.boot.CommandLineRunner;
 import org.springframework.context.annotation.Bean;
 import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ApiForoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiForoApplication.class, args);




	}

	@Bean
	CommandLineRunner printAdminHash(PasswordEncoder encoder) {
		return args -> System.out.println("BCRYPT(admin) = " + encoder.encode("admin"));
	}


}