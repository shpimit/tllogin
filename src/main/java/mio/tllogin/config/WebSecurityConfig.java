package mio.tllogin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/login", "/registration", "/h2/**").permitAll()
                    .antMatchers("/home/admin").hasAuthority("ADMIN")
                    .antMatchers("/home/user").hasAuthority("MANAGER")
                    .antMatchers("/home/guest").hasAuthority("USER")
                    .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                    .csrf()
                        .disable()
                .headers()
                    .frameOptions().disable()
                .and()
                    .formLogin() // 7
                        .loginPage("/login") // 로그인 페이지 링크
                        .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                        .failureUrl("/login?error=true")
                        .loginProcessingUrl("/login.do")
                        .usernameParameter("username")
                        .passwordParameter("password")
                .and()
                    .logout() // 8
                    .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                    .invalidateHttpSession(true) // 세션 날리기
        ;
    }
}
