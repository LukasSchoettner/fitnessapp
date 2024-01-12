package de.othr.fitnessapp.config;

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
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {	
	
	public SecurityConfig() {
	}
	
	private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/logout",
            "/h2-console/**",
            "/console/**",
            "/note/all",
            "/api/**"
            
    };
	
	// My API starts from /api so this pattern is ok for me
    private static final String API_URL_PATTERN = "/api/**";
	
			
	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    		
			  
    
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http,
                                                      HandlerMappingIntrospector introspector) throws Exception {
        

        http.csrf().disable();
        
        http.csrf(csrfConfigurer ->			
                csrfConfigurer
                .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")));

        http.headers(headersConfigurer ->
                headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(auth ->
                auth
                		.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()		
                		.requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                		.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                		.requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                		.requestMatchers(new AntPathRequestMatcher("/note/all")).permitAll()
        				.requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll());
                		
        
                		
        http.authorizeHttpRequests()
        
        
        
        .requestMatchers(new AntPathRequestMatcher("/trainer/**")).hasAnyAuthority("TRAINER","ADMIN")
        .requestMatchers(new AntPathRequestMatcher("/note/**")).hasAnyAuthority("TRAINER","ADMIN");
        
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable));                
        
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
     

	@Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }	
	
}
