#核心注解与方法
-UserDetailsService : 实现表单形式的用户认证
-PasswordEncoder : 密码加解密工具，默认使用BCryptPasswordEncoder，一般使用DelegatingPasswordEncoder生成
-UsernamePasswordAuthenticationFilter : 身份验证过滤器类
  -认证步骤①：https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
  -认证步骤②DaoAuthenticationProvider ：https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html
#本项目使用
-SecurityConfiguration 继承WebSecurityConfigurerAdapter类并添加@EnableWebSecurity激活springSecurity配置，重写configure完成权限与认证配置
-重写userDetailsService bean用于查询用户与权限数据返回userDetails
-通过http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)在校验用户名与密码前增加token验证。
#JWT知识(生成token,可在一定时间内直接访问无需登录)
JWT串总共有以下三个部分：JWT头、有效载荷和签名
依赖：            <groupId>io.jsonwebtoken</groupId>
                 <artifactId>jjwt</artifactId>
核心类：
Claims 有效载荷：主题、过期时间等信息的map集合也可增加自定义字段
DefaultJwtBuilder 通过Jwts.builder()创建，可设置有效负荷、过期时间、签名算法及固定密钥等生成token串。
DefaultJwtParser  解析token串。

