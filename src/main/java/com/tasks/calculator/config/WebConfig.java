package com.tasks.calculator.config;


import com.tasks.calculator.entities.enums.RoleName;
import lombok.var;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static com.tasks.calculator.global.InstallConstants.PASS;
import static com.tasks.calculator.global.InstallConstants.USER;
import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
@EnableWebMvc
//implements WebMvcConfigurer
public class WebConfig extends WebSecurityConfigurerAdapter {
    protected static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/static/css",
            "classpath:/static/js",
            "classpath:/public/"};

    //@Override
/*
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/rest/api/policy/**");
    }

 */

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(
                 requests -> requests.antMatchers("/api/tasks/**")
                ).build();
//                hasRole(RoleName.MANAGER.name());

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/tasks/**").permitAll();
    }

    /**
     * This section defines the user accounts which can be used for authentication as well as the roles each user has.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        var builder = User.builder()
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

        var springuser = ((User.UserBuilder) builder)
                .username(USER)
                .password(PASS)
                .roles(RoleName.MANAGER.name())
                .build();
        var root = builder
                .username("root")
                .password("Libra28091963!")
                .roles(RoleName.MANAGER.name(), RoleName.PROGRAMMER.name())
                .build();

        return new InMemoryUserDetailsManager(springuser, root);
    }

    /*
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.securityMatcher(EndpointRequest.toAnyEndpoint());
            http.authorizeHttpRequests(requests -> requests
                    .requestMatchers("/user/login/")
                    .hasRole("ADMIN"));
            return http.build();
        }
    */
    //@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
