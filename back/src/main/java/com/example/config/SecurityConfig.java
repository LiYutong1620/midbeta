package com.example.config;

import com.example.security.AuthenticationEntryPointImpl;
import com.example.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 配置 CORS 跨域来源
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许你的前端地址（注意不要带结尾斜杠）
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3001"));
        // 允许的请求方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 允许的请求头（* 表示全部）
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 允许携带凭证（如 Cookie）
        configuration.setAllowCredentials(true);
        // 预检请求的缓存时间（秒）
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 开启 CORS 并使用上面定义的配置源
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 2. 禁用 CSRF
                .csrf(csrf -> csrf.disable())
                // 3. 认证失败处理
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // 4. 无状态 session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 5. 允许 iframe
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // 6. 授权规则 —— 全部使用 AntPathRequestMatcher
                .authorizeHttpRequests(auth -> auth
                        // 6.0 显式放行 /error（解决 PatternParseException 的关键）
                        .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                        // 6.1 放行 OPTIONS 预检请求
                        .requestMatchers(new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name())).permitAll()
                        // 6.2 放行不需要认证的路径
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/index.html"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/system/category/client/list"),
                                new AntPathRequestMatcher("/register"),
                                new AntPathRequestMatcher("/static/**"),
                                new AntPathRequestMatcher("/doc.html"),
                                new AntPathRequestMatcher("/webjars/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-resources/**")
                        ).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/system/news/public/**")).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/", HttpMethod.GET.name()),
                                new AntPathRequestMatcher("/*.html", HttpMethod.GET.name()),
                                new AntPathRequestMatcher("/**/*.html", HttpMethod.GET.name()),
                                new AntPathRequestMatcher("/**/*.css", HttpMethod.GET.name()),
                                new AntPathRequestMatcher("/**/*.js", HttpMethod.GET.name()),
                                new AntPathRequestMatcher("/profile/**", HttpMethod.GET.name())
                        ).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/common/upload")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/upload/**")).permitAll()
                        // 6.3 其余所有请求都需要认证
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.permitAll());

        // 添加 JWT 过滤器
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}