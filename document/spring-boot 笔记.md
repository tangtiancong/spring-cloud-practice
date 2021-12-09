#源码阅读常用注解
-@Target 修饰注解对象范围，描述注解可以使用的范围，取值ElementType枚举
-@Retention 定义了该Annotation被保留的时间长短，表示需要在什么级别保存该注释信息，用于描述注解的生命周期，取值RetentionPolicy枚举
-@Documented 用于描述其它类型的annotation应该被作为被标注的程序成员的公共API
-@Inherited 允许子类继承父类的注解。
-@ComponentScans 包扫描注解，用于获取加载自动匹配组件等。
-@Repeatable 声明一个注解可以被多次重复使用，避免了类似 例如@ComponentScan注解加上@Repeatable(ComponentScans.class)可达到@ComponentScans效果
-@AliasFor ①同意注解中可指定两个变量互为别名②给元注解的属性设置别名
#开发常用注解
- @Configuration注解：表示一个类为配置类=spring的配置文件，
        @bean注解的方法名为配置组件的id,返回类型为组件的类型，返回的值为组件在容器中的实例。
- @Resource 与 @Autowired ：@Autowired默认按byType加载，可通过required=false属性 避免类型为空时报错，可通过配合@Qualifier注解来显式指定name值。
                            @Resource默认按byName加载，未找到按byType加载。
- @Conditional注解 ：根据@Conditional子注解实现在满足指定条件下加载配置类或组件。
- @RequestBody：适用于解析格式为json格式请求参数，解析成object  @RequestParam适用于解析 类似get请求附带参数
- @PathVariable(id): 获取类似restful方式的请求数据如baidu.com/id
- @Repository：持久层注解，一般使用于mapper接口上
- @Scope： 作用域注解，标注bean的作用域，控制单例还是多例，是否使用代理及代理方式。

--导入配置文件及属性
    - @ImportResource注解：一般用于导入spring xml配置文件达到加载容器组件。
    - @PropertySource：引入properties文件
    - @ConfigurationProperties注解：引入配置属性，需配合@Component或者@EnableConfigurationProperties使用
    
--事务相关
    -@Transactional  声明式事务注解
    
--异常相关
    -@ControllerAdvice 注解定义全局异常处理类，@ExceptionHandler 注解声明指定异常处理方法

--启动类相关解读
    
    
#基础知识
-spring.factories :可设置启动类
