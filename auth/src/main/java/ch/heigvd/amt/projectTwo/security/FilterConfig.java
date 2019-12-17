package ch.heigvd.amt.projectTwo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    JwtTokenProvider provider;

    @Bean
    public FilterRegistrationBean<JwtTokenFilter> loggingFilter() {
        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtTokenFilter(provider));

        registrationBean.addUrlPatterns("/users");
        registrationBean.addUrlPatterns("/users/*");

        return registrationBean;

    }

}