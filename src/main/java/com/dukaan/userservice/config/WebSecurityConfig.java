//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Disable CSRF protection
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**").permitAll() // Allow all access to H2 console
//                        .anyRequest().authenticated() // Authenticate all other endpoints
//                )
//                .headers(headers -> headers.frameOptions().sameOrigin()) // Allow H2 console frames
//                .formLogin().disable(); // Disable login for dev environment
//        return http.build();
//    }
//}
