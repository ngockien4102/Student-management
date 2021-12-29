package com.example.spring.security.basic.security;

import com.example.spring.security.basic.service.AccountService;
import com.example.spring.security.basic.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        // Tạo ra user trong bộ nhớ
//        // lưu ý, chỉ sử dụng cách này để minh họa
//        // Còn thực tế chúng ta sẽ kiểm tra user trong csdl
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
//                        .username("kien")
//                        .password("123")
//                        .roles("USER") // phân quyền là người dùng.
//                        .build()
//        );
//        return manager;
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//                http.authorizeRequests()
//                .antMatchers("/", "/home").permitAll() // Cho phép tất cả mọi người truy cập vào 2 địa chỉ này
//                .anyRequest().authenticated() // Tất cả các request khác đều cần phải xác thực mới được truy cập
//                .and()
//                .formLogin() // Cho phép người dùng xác thực bằng form login
//                .defaultSuccessUrl("/hello")
//                .permitAll() // Tất cả đều được truy cập vào địa chỉ này
//                .and()
//                .logout() // Cho phép logout
//                .permitAll();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//                .antMatchers("/api/login").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
//                .anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được truy cập
        http.authorizeRequests().antMatchers("/accounts/login/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/accounts/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/accounts/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/accounts/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/accounts/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.logout(logout -> logout
                .logoutUrl("/accounts/logout")
                .invalidateHttpSession(true)
                .deleteCookies());
        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore((Filter) jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
