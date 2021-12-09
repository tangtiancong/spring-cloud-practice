package com.tang.userserver.configuration;
import com.tang.userserver.component.SecurityAccessDeniedHandler;
import com.tang.userserver.component.SecurityAuthEntryPoint;
import com.tang.userserver.filter.JwtTokenFilter;
import com.tang.userserver.service.UserService;
import com.tang.userserver.util.UsualUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tangtiancong
 * @Date 2021/11/10:15:25
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${userServer.loginUrl}")
    private  String loginUrl;
    @Value("${password.encode.type}")
    private String passwordEncoderType;
    @Autowired
    private SecurityIgnoreUrls securityIgnoreUrls;
    @Autowired
    UserService userService;
    @Autowired
    SecurityAccessDeniedHandler securityAccessDeniedHandler;
    @Autowired
    SecurityAuthEntryPoint securityAuthEntryPoint;
    @Override
        protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                registry=http.authorizeRequests();
        http.formLogin(form ->{
            form.loginPage(loginUrl);
//            form.loginProcessingUrl("/login");
//            form.failureForwardUrl("/common/error");
        });
        //指定不需验证用户密码的url
        for (String url: securityIgnoreUrls.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        //需要2级权限指定权限的的url
        for (String url: securityIgnoreUrls.getAuthorityUrls()) {
            registry.antMatchers(url).hasRole("2");
        }
        //注意前后顺序
        registry.anyRequest() //其他请求
                .authenticated() //需要认证
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling()
                //认证拒绝访问
                .accessDeniedHandler(securityAccessDeniedHandler)
                //认证过期
                .authenticationEntryPoint(securityAuthEntryPoint);
        //将校验token放到 校验密码之前
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 获取密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        if(UsualUtil.isEmpty(passwordEncoderType)){
            passwordEncoderType = "bcrypt";
        }
        Map encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        PasswordEncoder passwordEncoder =
                new DelegatingPasswordEncoder(passwordEncoderType, encoders);
        return passwordEncoder;
    }
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return  new JwtTokenFilter();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userService.loadUserByUsername(username);
    }
}
