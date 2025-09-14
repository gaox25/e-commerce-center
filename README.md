## 什么是spring boot

第一句话：Spring Boot可以轻松创建独立的，生产级的基于Spring的应用程序

第二句话：Spring Boot直接嵌入Tomcat、Netty或者Undertow，可以“直接运行”Spring Boot程序

Spring Boot做了很多封装和自动配置，所以隐藏了许多细节

## 关于Spring Cloud的解读

1.Spring Cloud是微服务的落地

2.Spring Cloud体现了微服务的弹性设计

3.微服务的工作方法一般是基于分布式的

4.Spring Cloud仍然是Spring家族的一员，可以解决微服务的分布式工作方式带来的各种问题

5.Spring Cloud提供很多组件，比如服务发现，复杂均衡，链路中断，分布式追踪和监控，甚至提供API gateway功能

6.Spring Boot往往和Spring Cloud结合使用，两者有严格的版本对应关系

## Spring Cloud 组件选型

### 服务注册中心

* Eureka(很经典，但是目前已很少使用，不推荐)
* Nacos(推荐，主流)
* Zookeeper(推荐)
* Consul(推荐)

### 服务负载均衡

* Ribbon(推荐)
* LoadBalancer(推荐)

### 服务熔断降级

* Hystrix(用的很少了)
* Sentinel(推荐，主流)

### 服务调用

* Feign(用的少)
* Open Feign(推荐，主流)

### 服务网关

* Zuul(用得少)
* GateWay(推荐，主流)

### 服务配置

* Config
* Nacos(推荐)

### 服务总线

* Bus
* Nacos(推荐)

分布式是一种工作方式，各种微服务组件最终都是为了分布式应用系统服务的

## 分布式微服务技术选型

### Spring Cloud原生组件的几大痛点

1. Spring Cloud部分组件停止维护和更新，给开发带来不便
2. Spring Cloud部分环境搭建复杂，没有完善的可视化界面，需要大量的二次开发和定制
3. Spring Cloud配置复杂，难以上手

### Spring Cloud Alibaba的优势

1. 阿里使用过的组件经历了考验，性能强悍，设计合理，现在开源出来供大家使用
2. 搭配完善的可视化界面给开发运维带来极大的便利，搭建简单，学习曲线低

### 分布式微服务技术选型建议

1. Spring Cloud Alibaba组件为主
2. Spring Cloud为辅，比如Spring Cloud - Ribbon：负载均衡、Spring Cloud-OpenFeign：远程服务调用、Spring Cloud-Gateway：API网关、Spring Cloud-Sleuth：调用链监控 等，还是非常不错的

## dependencyManagement细节说明

1. Maven使用dependencyManagement元素来提供一种管理依赖版本号的方式，通常在项目packaging为pom，中使用dependencyManagement元素

2. 使用pom.xml中的dependencyManagement元素能让所有在子项目中引用一个依赖，Maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement元素的项目，然后它就会使用dependencyManagement元素中指定的版本号

3. 好处：如果有多个子项目都引用同一个依赖，则可以避免在每个使用的子项目里都声明一个版本号，当升级或切换到另一个版本时，只需要在顶层父容器里更新，而不需要分别在子项目中修改；另外如果某个子项目需要另外的一个版本，只需要申明version就行

4. dependencyManagement里只是声明依赖，并不实现引入，因此子项目需要显式地声明需要的依赖

5. 如果不在子项目中声明依赖，是不会从父项目中继承下来的，只有在子项目中写了该依赖项，并且没有指定具体版本，才会从父项目中继承该项，并且version和scope都读取自父pom

   ![image-20250912162533672](/Users/firespite/Library/Application Support/typora-user-images/image-20250912162533672.png)

6. 如果子项目中指定了版本号，那么会使用子项目中指定的jar版本

## 创建会员中心微服务注意事项

1. 前端如果是以json格式来发送添加信息member，那么需要使用@RequestBody，才能将数据封装到
     对应的bean中，同时保证http的请求头的content-type是对应

2. 如果前端是以表单形式或parameter形式提交了，则不需要使用@RequestBody，才会进行对象参数封装，
   同时保证http的请求头的content-type是对应

3. 在进行SpringBoot应用程序测试时，引入的JUnit是org.junit.jupiter.api.Test

4. 运行程序时，一定要确保XxxxMapper.xml文件 被自动放到的target目录的classes指定目录，这里规定的路径在resources/application.yml中规定

   ```yaml
   mybatis:
     mapper-locations: classpath:mapper/*.xml #指定mapper.xml文件位置，在target/classes/mapper下
     type-aliases-package: com.gaoxi.springcloud.entity #指定实体类所在的包，这样就只通过类名就可以访问
   ```

http://localhost:10000/member/save?name=张飞&pwd=123&mobile=12345678&email=zf@gmail.com&gender=1

