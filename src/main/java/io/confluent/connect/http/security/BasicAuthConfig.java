package io.confluent.connect.http.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1)
@Profile({"basic-auth", "ssl-auth"})
@EnableWebSecurity
@Configuration
public class BasicAuthConfig extends WebSecurityConfigurerAdapter {
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/api/**")
        .authorizeRequests()
        .anyRequest().hasRole("ADMIN")
        .and()
        .httpBasic()
        .and()
        .csrf().disable();
  }
}
