package unical.informatica.it.enterpriseapplicationbackend;

import io.github.bucket4j.Bucket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import unical.informatica.it.enterpriseapplicationbackend.api.security.RateLimitingFilter;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	@Bean
	public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilterRegistration(Bucket bucket) {
		FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RateLimitingFilter(bucket));
		registrationBean.addUrlPatterns("/auth/*");
		registrationBean.addUrlPatterns("/user/*");
		registrationBean.addUrlPatterns("/orders/*");
		registrationBean.addUrlPatterns("/product/*");
		registrationBean.addUrlPatterns("/wishlists/*");
		return registrationBean;
	}
}
