package cn.kobelee.ipresolve.client.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author Kobe Lee
 * @email kobe663@gmail.com
 * @date 11/17/2021
 */
@Configuration
public class AliyunClientConfig {
    @Value("${aliyun-auth-key-id}")
    private String aliyunAuthKeyId;
    @Value("${aliyun-auth-key-security}")
    private String aliyunAuthKeySecurity;

    @Bean
    public IAcsClient acsClient(){
        String regionId = "cn-hangzhou";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, aliyunAuthKeyId, aliyunAuthKeySecurity);
        return new DefaultAcsClient(profile);
    }
}
