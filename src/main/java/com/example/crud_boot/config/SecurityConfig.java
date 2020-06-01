package com.example.crud_boot.config;

import com.example.crud_boot.service.UserService;
import com.example.crud_boot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    MySimpleUrlAuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                  .antMatchers( "/login/**").permitAll()
                  .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                  .antMatchers("/user/**").hasAnyAuthority("USER")
                  .anyRequest().authenticated()
                  .and()
                .formLogin()
                  .loginPage("/login")
                  .successHandler( new MySimpleUrlAuthenticationSuccessHandler())
                  .loginProcessingUrl("/login")
                // Указываем параметры логина и пароля с формы логина
                  .usernameParameter("username")
                  .passwordParameter("password")
                  .permitAll()
                  .and()
                  .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                  /*.logoutUrl("/logout")*/
                  .permitAll()

  //      .and()
   //     .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(200000)

        ;
    }

    @Autowired
    private UserService userService;


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
      /*  auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("root").password("root").roles("ADMIN","USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
*/
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}