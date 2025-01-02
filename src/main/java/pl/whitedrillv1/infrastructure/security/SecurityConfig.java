package pl.whitedrillv1.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pl.whitedrillv1.domain.usercrud.UserRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserDetailsServiceImpl(userRepository, passwordEncoder);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());
        http.cors(corsConfigurerCustomizer());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/users/register/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/dentist/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/appointments/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/appointments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/appointments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/schedule/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/schedule/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/patients/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/patients/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        return http.build();
    }

    //            , JwtAuthTokenFilter jwtAuthTokenFilter) throws Exception {
//
//        //http.csrf(Customizer.withDefaults());
//        http.cors(corsConfigurerCustomizer());
//        http.formLogin(c -> c.disable());
//        http.httpBasic(c -> c.disable());
//        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        http.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/swagger-ui/**").permitAll()
//                .requestMatchers("/swagger-resources/**").permitAll()
//                .requestMatchers("/v3/api-docs/**").permitAll()
//                .requestMatchers("/users/register/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/token/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/artist/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/genres/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PATCH, "/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/songs/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/artists/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PATCH, "/artists/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/artists/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/albums/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/albums/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
//                .anyRequest().authenticated());
//        return http.build();
    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("https://localhost:3000"));
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                return config;
            };
            c.configurationSource(source);
        };
    }
}
