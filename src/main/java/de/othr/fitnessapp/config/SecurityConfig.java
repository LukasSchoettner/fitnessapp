package de.othr.fitnessapp.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@NoArgsConstructor
public class SecurityConfig {
	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.csrf(csrfConfigurer -> csrfConfigurer
                .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")));

        http.headers(headersConfigurer -> headersConfigurer
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/note/all")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/customer")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll());

        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/home")).hasAnyAuthority("CUSTOMER", "TRAINER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/customer/**")).hasAnyAuthority("CUSTOMER","ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/trainer/**")).hasAnyAuthority("TRAINER","ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/note/**")).hasAnyAuthority("TRAINER","ADMIN")

                .requestMatchers(new AntPathRequestMatcher("/course/**")).hasAnyAuthority("CUSTOMER", "TRAINER", "ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/workout/**")).hasAnyAuthority("CUSTOMER", "TRAINER", "ADMIN");
        
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));                
        
        http.formLogin(Customizer.withDefaults());
        http.formLogin().defaultSuccessUrl("/home");
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
