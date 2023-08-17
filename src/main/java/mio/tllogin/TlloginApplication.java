package mio.tllogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@ServletComponentScan    // Filter 사용
@EnableRedisHttpSession  // Redis Session Cluster 환경을 구성
@SpringBootApplication
public class TlloginApplication {

	public static void main(String[] args) {
		SpringApplication.run(TlloginApplication.class, args);
	}

}
