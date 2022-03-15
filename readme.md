# aliyun-ddns-client

## 功能
利用阿里云的“云解析DNS“服务，定期更新目标域名（如x.y.com）的解析目标为当前客户端所在环境的外网ip. 从而实现动态域名解析的目的。

## 使用方法
1. 获取阿里云的accessKeyId和secret
   
   ![获取accessKeyId和secret](./.assets/阿里云accessKey截图.png)
2. 在application.yml中填入第1步获取的id和secret, 以及region
```yaml
aliyun-auth-key-id: 你的阿里云authKeyId
aliyun-auth-key-security: 你的阿里云authKeySecurity
aliyun-region-id: cn-hangzhou
```
3. 填入需要自己的二级域名和需要定时更新的三级域名名称到application.yml
```yaml
ddns:
  target-domain-name: yourdomain.com
  # 目标记录名  自动配置 xxx.yourdomain.com的dns解析
  target-record-name: xxx
```
4. 打包及运行

*jar*
```shell
mvn clean package
java -jar ddns-cient-1.0-SNAPSHOT.jar
```

*docker*
```shell
sh ./package-docker.sh
sudo docker run -d --name ddns-client --restart always ddns-client:1.0
```
