package dcatano.micrometer_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MicrometerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrometerDemoApplication.class, args);
	}

}
