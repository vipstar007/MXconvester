//package com.vn.napas.security;
//import com.vn.napas.config.PropertiesConfig;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//public class SecurityConfig {
//
//    private final PropertiesConfig propertiesConfig;
//
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//		// InMemoryUserDetailsManager
//		UserDetails admin = User.withUsername(propertiesConfig.getUsernameAuth())
//				.password(encoder.encode(propertiesConfig.getPasswordAuth()))
//				.build();
//
//		return new InMemoryUserDetailsManager(admin);
//	}
//
//	// Configuring HttpSecurity
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf().disable()
//				.authorizeHttpRequests()
//				.requestMatchers("/v1/welcome").permitAll()
//				.and()
//				.authorizeHttpRequests().requestMatchers("/v1/**").authenticated()
//				.and().httpBasic()
//				.and().build();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//}
