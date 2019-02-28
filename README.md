# File Server（基于springboot+mysql的文件服务器）

It's using some very popular technology like:

* MyBaits
* Mysql 5.7
* Spring Boot 1.5.10.RELEASE
* Thymeleaf 3.0.3.RELEASE
* Thymeleaf Layout Dialect 2.2.0
* Maven 3.5.0

## APIs
Here are useful APIs.
* POST /upload : Upload file.(上传文件)
* GET  /files/{pageIndex}/{pageSize} :(分页查询文件列表)
* GET  /files/{id} : Download file.(下载某个文件)
* DELETE /delete/{id} : Delete file.(删除某个文件)


访问http://localhost:8081.（可根据需求自定义）

## Configuration （配置）
The default configuration is （默认配置如下） :

# port
server.port=8081

# Thymeleaf 
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.cache=false
spring.thymeleaf.mode=HTML5

# upload file size
spring.http.multipart.max-file-size=10MB
spring.http.multipart.max-request-size=100MB

# UploadUrl
global.upload.path=/upload/
# SavePath
global.upload.docBase=/FileHouse/

# Mybatis
mybatis.mapper-locations=classpath:com/kevin/mapper/mapping/*Mapper.xml
mybatis.config-location=classpath:config/mybatis-config.xml
mybatis.type-aliases-package=com.kevin.entity

# pagehelper
pagehelper.helper-dialect=mysql
pagehelper.reasonable=true
pagehelper.support-methods-arguments=true
pagehelper.params=count=countSql

# datasource
spring.datasource.url=jdbc:mysql://localhost:3306/***?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC 
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=*****