package oxyac.shopping.config;

import jakarta.servlet.Filter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "app")
@EnableScheduling
@EnableAsync
@Data
@Slf4j
public class AppConfig  implements WebMvcConfigurer {

    @Value("${UI_STATIC_FILES_DIR}")
    private String staticFileDir;

    @Value("${ALLOWED_CORS_ORIGINS}")
    private List<String> allowedCorsOrigins;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("file:" + staticFileDir + "/")
        ;
    }

    /**
     * Filter to forward all non /api and non-static file requests to index.html of the UI dist
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter((request, response, chain) -> {
            HttpServletRequest request1 = (HttpServletRequest) request;
            if (!request1.getRequestURI().startsWith("/ws")
                    && !request1.getRequestURI().startsWith("/api/")
                    && !request1.getRequestURI().contains(".")
            ) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.html");
                requestDispatcher.forward(request, response);
                return;
            }
            chain.doFilter(request, response);
        });
        return filterFilterRegistrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedCorsOrigins.toArray(String[]::new))
                .allowedMethods("POST", "PUT", "GET", "DELETE", "HEAD")
        ;
        log.info("Allowed CORS origins: {}", allowedCorsOrigins);
    }

}
