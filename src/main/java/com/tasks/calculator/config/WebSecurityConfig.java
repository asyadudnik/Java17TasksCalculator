package com.tasks.calculator.config;


import com.tasks.calculator.dto.enums.RoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.sql.DataSource;

import static com.tasks.calculator.global.InstallConstants.PASS;
import static com.tasks.calculator.global.InstallConstants.USER;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableWebMvc
//implements WebMvcConfigurer
public class WebSecurityConfig {
    protected static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/static/css",
            "classpath:/static/js",
            "classpath:/public/"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers("/user/login/")
                .hasRole(RoleName.MANAGER.name()));

        return httpSecurity.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/rest/api/tasks/**", "/rest/api/task/operations");
    }

    /**
     * This section defines the user accounts which can be used for authentication as well as the roles each user has.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var builder = User.builder()
                .passwordEncoder(
                        PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

        UserDetails user = builder
                .username(USER)
                .password(PASS)
                .roles(RoleName.PROGRAMMER.name())
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        var builder = User.builder()
                .passwordEncoder(
                        PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode);

        UserDetails user = builder
                .username(USER)
                .password(PASS)
                .roles(RoleName.MANAGER.name(), RoleName.PROGRAMMER.name())
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        UserDetails userRoot = builder
                .username("root")
                .password(PASS)
                .roles(RoleName.MANAGER.name(), RoleName.PROGRAMMER.name(), RoleName.DEVOPS.name())
                .build();
        users.createUser(userRoot);
        return users;
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
