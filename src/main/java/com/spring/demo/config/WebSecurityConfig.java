package com.spring.demo.config;

import com.spring.demo.security.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan({"com.spring.demo.service.serviceImpl"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final static String SESSION_KEY = "user";

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("属性是：{}",securityProperties.getBrowser().getLoginPage());
        http
                .authorizeRequests()
                .antMatchers("/webjars/**", "/resources/**",
                        "/**/*.css",
                        securityProperties.getBrowser().getLoginPage(),
                        "/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js",
                        "/welcome",
                        "/register",
                        "/isExistUsername",
                        "/isExistEmail",
                        "/registerPost",
                        "/**/*.js",
                        "/static/js/jquery.js",
                        "/static/js/bootstrap-datetimepicker.min.js",
                        "/static/messages_zh.js",
                        "/static/js/jquery.validate.min.js").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/home")

                .loginPage("/login")
                .loginProcessingUrl("/loginPost")
//                    .successHandler(myAuthenticationSuccessHandler)
//                    .failureHandler(myAuthenticationFailureHandler)
                .permitAll()
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//            tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}