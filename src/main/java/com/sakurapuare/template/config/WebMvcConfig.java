package com.sakurapuare.template.config;

import com.sakurapuare.template.interceptor.AuthInterceptor;
import com.sakurapuare.template.interceptor.RequestInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    private final RequestInfoInterceptor requestInfoInterceptor;

    public WebMvcConfig(AuthInterceptor authInterceptor, RequestInfoInterceptor requestInfoInterceptor) {
        this.authInterceptor = authInterceptor;
        this.requestInfoInterceptor = requestInfoInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**",
                        "/error",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/favicon.ico",
                        "/swagger-ui*/**",
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/swagger-ui.html"
                );

        registry.addInterceptor(requestInfoInterceptor)
                .addPathPatterns("/**");
    }

    // 设置静态资源映射
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
} 