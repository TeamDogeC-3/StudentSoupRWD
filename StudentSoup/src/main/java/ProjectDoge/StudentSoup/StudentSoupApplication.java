package ProjectDoge.StudentSoup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentSoupApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentSoupApplication.class, args);
	}

}
