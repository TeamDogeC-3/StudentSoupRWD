package ProjectDoge.StudentSoup.config;

import ProjectDoge.StudentSoup.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .order(1)
                .addPathPatterns("/admin", "/admin/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/js/**");
    }
}
