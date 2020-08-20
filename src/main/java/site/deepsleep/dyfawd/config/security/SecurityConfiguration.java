package site.deepsleep.dyfawd.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import site.deepsleep.dyfawd.advice.exception.security.CustomAuthenticationEntryPoint;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SensorJwtTokenProvider jwtTokenProvider;
    private final Environment env;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       if(isLocalMode()) {
           setLocalMode(http);
       } else {
           setRealMode(http);
       }
    }

    private boolean isLocalMode() {
        String profile = env.getActiveProfiles().length > 0? env.getActiveProfiles()[0] : "local";
        return profile.equals("local");
    }

    private void setLocalMode(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/me", "/h2-console/**", "/login/**", "/js/**", "/css/**", "/image/**", "/fonts/**", "/favicon.ico").permitAll()
                    .antMatchers("/api/v1/statistics/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/security/token").permitAll()
                    .antMatchers(HttpMethod.GET, "/exception/**", "/favicon.ico").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/sensor/data").hasRole("SENSOR")
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(new SensorJwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    private void setRealMode(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/api/v1/statistics/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/security/token").permitAll()
                    .antMatchers(HttpMethod.GET, "/exception/**", "/favicon.ico").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/sensor/data").hasRole("SENSOR")
                    .anyRequest().denyAll()
                .and()
                    .cors()
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(new SensorJwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/statistics/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
