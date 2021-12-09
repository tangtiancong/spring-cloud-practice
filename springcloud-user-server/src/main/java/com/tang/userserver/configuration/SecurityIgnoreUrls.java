package com.tang.userserver.configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Author tangtiancong
 * @Date 2021/12/2:9:52
 */
@Component
@ConfigurationProperties(prefix = "security.ignore")
public class SecurityIgnoreUrls {
    private  List<String> urls ;
    private List<String> authorityUrls;
    public  List<String> getUrls() {
        return urls;
    }
    public  void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getAuthorityUrls() {
        return authorityUrls;
    }

    public void setAuthorityUrls(List<String> authorityUrls) {
        this.authorityUrls = authorityUrls;
    }
}
