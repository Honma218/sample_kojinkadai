package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

//@EnableWebSecurity
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//	  @Bean
//	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http.authorizeHttpRequests(authorize -> authorize
//	          .requestMatchers("/h2-console/**").hasRole("ADMIN").requestMatchers("/board").hasRole("USER").anyRequest().authenticated())
//	    .formLogin(form -> form
//	          .loginPage("/user").permitAll().defaultSuccessUrl("/board"))
//	     .logout(logout -> logout
//	          .logoutUrl("/user/logout") 
//	          .logoutSuccessUrl("/user"))
//	      .csrf(csrf -> csrf
//	          .ignoringRequestMatchers("/h2-console/**"))
//	      .headers(headers -> headers
//	          .frameOptions().sameOrigin());
//	    return http.build();
//	  }
//
//	  @Bean
//	  public WebSecurityCustomizer webSecurityCustomizer() {
//	    return web->web
//	.ignoring().requestMatchers(PathRequest.toStaticResources()
//	.atCommonLocations());
//	  }
//
//	}
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	  http.authorizeHttpRequests((authorize) -> authorize
	            .requestMatchers("/h2-console/**").permitAll()
	            .requestMatchers("/user/signup", "/user", "/", "/css/**", "/js/**", "/images/**").permitAll()
	            .anyRequest().authenticated() // 認証が必要
	        )
	        .formLogin(form -> form
	            .loginPage("/user").permitAll() // ログインページをすべてのユーザーに許可
	            .defaultSuccessUrl("/board", true) // ログイン成功後にリダイレクトするデフォルトURL
	        )
	        .logout(logout -> logout
	            .logoutUrl("/user/logout")
	            .logoutSuccessUrl("/user")
	        )
	        .csrf(csrf -> csrf
	            .disable() // 開発用：CSRF完全無効化
	        )
	        .headers(headers -> headers
	            .frameOptions().disable() // H2コンソール用にフレームオプションを無効化
	        );
    return http.build();
  }

}
