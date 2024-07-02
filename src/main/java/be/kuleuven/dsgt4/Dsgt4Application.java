package be.kuleuven.dsgt4;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.HypermediaWebClientConfigurer;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.PostConstruct;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@SpringBootApplication
public class Dsgt4Application {
	@SuppressWarnings("unchecked")
	public static void main(String[] args)  {
		System.setProperty("server.port", System.getenv().getOrDefault("PORT", "8080"));
		SpringApplication.run(Dsgt4Application.class, args);
}

	public boolean isProduction() {
		return Objects.equals(System.getenv("GAE_ENV"), "standard");
	}

	@Bean
	public String projectId() {
		if (this.isProduction()) {
			return "btripagency";
		} else {
			return "demo-distributed-systems-kul";
		}
	}

	@Bean
	public FirebaseApp initializeFirebaseApp() throws IOException {
		if (isProduction()) {
			GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(credentials)
					.build();
			return FirebaseApp.initializeApp(options);
		} else {
			// Initialize Firebase emulator if running locally
			return FirebaseApp.initializeApp();
		}
	}

	@Bean
	public Firestore db() throws IOException {
		if (isProduction()) {
			String serviceAccountKeyPath = System.getenv("FIREBASE_SERVICE_ACCOUNT_KEY");
			if (serviceAccountKeyPath != null && !serviceAccountKeyPath.isEmpty()) {
				GoogleCredentials credentials = GoogleCredentials.fromStream(
						new ClassPathResource(serviceAccountKeyPath.replace("classpath:", "")).getInputStream())
						.createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

				return FirestoreOptions.getDefaultInstance()
						.toBuilder()
						.setProjectId(this.projectId())
						.setCredentials(credentials)
						.build()
						.getService();
			} else {
				throw new IllegalStateException("FIREBASE_SERVICE_ACCOUNT_KEY environment variable is not set.");
			}
		} else {
			return FirestoreOptions.getDefaultInstance()
					.toBuilder()
					.setProjectId(this.projectId())
					.setCredentials(new FirestoreOptions.EmulatorCredentials())
					.setEmulatorHost("localhost:8084")
					.build()
					.getService();
		}
	}

	/*
	 * You can use this builder to create a Spring WebClient instance which can be
	 * used to make REST-calls.
	 */
	@Bean
	WebClient.Builder webClientBuilder(HypermediaWebClientConfigurer configurer) {
		return configurer.registerHypermediaTypes(WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
				.codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
						.maxInMemorySize(100 * 1024 * 1024)));
	}

	@Bean
	HttpFirewall httpFirewall() {
		DefaultHttpFirewall firewall = new DefaultHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}

}
