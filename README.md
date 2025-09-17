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

## 引出Eureka

### Eureka可以做什么

1. 在企业级项目中，服务消费访问请求往往会存在高并发
2. 如果只有一个服务提供方，可用性差
3. 所以服务提供方往往是一个集群，也就是有多个服务提供模块在运行
4. 那么这个时候，就存在一个问题，就是服务消费方，怎么去发现可以使用的服务
5. 当服务消费方，发现了可以使用的服务后(可能有多个，又存在一个问题就是到底调用A服务，还是B服务的问题，这就是引出了服务注册和负载均衡)
6. Eureka就可以解决上述问题

### Eureka项目架构分析

![Eureka-Architecture](/readme-assets/Eureka-Architecture.png)

1. 会员中心-提供服务的，在项目中，会做成集群，提供高可用
2. Eureka Server有必要的话，也可以做成集群
3. Eureka包含两个组件Eureka Server和Eureka Client
3. Eureka Server提供注册服务，各个微服务节点通过配置启动后，会在Eureka Server中进行注册，这样Eureka Server中的服务注册表中将存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到
3. Eureka Client通过注册中心进行访问，是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器，在启动应用后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除(默认90秒)

### 服务治理介绍

1. 服务治理就是服务管理，Eureka就可以实现服务治理
1. 在传统的rpc远程调用框架中，管理每个服务与服务之间依赖的关系比较复杂，管理困难，所以需要服务治理管理服务之间的依赖关系
1. 服务治理实现服务调用、负载均衡、容错等，实现服务发现和注册

### 服务注册和发现

1. Eureka采用CS的设计架构，Eureka Server作为服务注册功能的服务器，它是服务注册中心
2. 系统中的其他微服务，使用Eureka的客户端连接到Eureka Server并维持心跳连接，通过Eureka Server来监控系统中各个微服务是否运行正常
3. 在服务注册和发现中，有一个注册中心，当服务器启动的时候，会把当前自己服务的信息，比如服务器地址通讯地址等以别名方式注册到注册中心上
4. 服务消费者或者服务提供者，以服务别名的方式去注册中心上获取到实际的服务提供者通讯地址，然后通过RPC调用服务

### 注册中心的维护机制

服务消费、服务提供和注册中心的维护机制

1. 服务注册：将服务信息注册到Server
2. 服务发现：从注册中心获取注册服务
3. 服务信息：key-服务名，value-远程调用地址

工作流程：

1. 启动Eureka Server
2. 启动服务提供者
3. 服务提供者把自己的信息(比如服务地址、别名、端口)注册到Eureka Server
4. 通过心跳机制来维护状态
5. 服务消费者需要调用接口时，使用服务别名去注册中心，获取实际RPC的远程调用地址
6. 消费者得到调用地址，底层实际是使用了HttpClient技术来实现
7. 消费者通过获取到的调用地址，会缓存到本地的JVM内存，默认每隔30秒去更新一次服务调用地址

![Eureka-Server-mechanism](/readme-assets/Eureka-Server-mechanism.png)

### Eureka自我保护机制

1. 自我保证机制/模式说明
   * 默认情况下Eureka Client定时向Eureka Server端发送心跳包
   * 如果Eureka在server端的一定时间内(默认90秒)没有收到Eureka Client发送心跳包，便会直接从服务注册列表中删除该服务
   * 如果Eureka开启了自我保护模式/机制，那么短时间(90秒)内丢失大量的服务实例心跳，这是Eureka Server会开启自我保护机制，不会剔除该服务（该现象可能出现在如果网络不通或者阻塞），因为客户端还能正常发送心跳，只是网络延迟问题，而保护机制是为了解决此问题而产生的
2. 自我保护是属于CAP里面的AP分支，保证高可用和分区容错性，但是可能有一致性问题
   * CAP理论：
3. 自我保护模式是一种应对网络异常的安全保护措施，它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留），也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定
4. 生产环境中，一般不禁用自我保护机制

### 集群Eureka Server

#### 为什么需要集群Eureka Server

1. 微服务RPC远程服务调用最核心的是实现高可用
2. 如果注册中心只有一个，它出故障，会导致整个服务环境不可用
3. 解决办法：搭建Eureka注册中心集群，实现负载均衡+故障容错

![Erueka-Server-Cluster](/readme-assets/Erueka-Server-Cluster.png)

### 集群服务提供方服务

新建服务提供方微服务 ，和现有的服务提供方微服务组建为集群，并注册到Eureka Server集群

![Service-Provider-Cluster](/readme-assets/Service-Provider-Cluster.png)

1. 因为member-service-provider-10000和member-service-provider-10002作为一个集群提供服务，因此需要将spring.application.name进行统一
2. 这样消费方通过统一的别名进行负载均衡调用 

### 通过DiscoveryClient获取注册服务列表

如果希望在服务消费方/服务提供方Eureka Client获取到Eureka Server的服务注册信息，使用DiscoveryClient即可

### Spring Cloud Ribbon

1. Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
2. Ribbon的主要功能是提供客户端负载均衡算法和服务调用
3. Ribbon客户端组件提供一系列完善的配置项如连接超时、重试等
4. Ribbon会基于某种规则（如简单轮询、随机连接等）去连接指定服务
5. 程序员很容易使用Ribbon的负载均衡算法实现负载均衡
6. 一句话：Ribbon：负载均衡 + RestTemplate调用

![Ribbon-Architecture](/readme-assets/Ribbon-Architecture.png)

![Ribbon-Load-Algorithm](/readme-assets/Ribbon-Load-Algorithm.png)

### 负载均衡/Load Balancer/LB分类

1. 集中式LB
   * 即在服务的消费方和提供方之间使用独立的LB设施（可以是硬件，如F5，也可以是软件，如Nginx），由设施负责把访问请求通过某种策略转发至服务的提供方
   * LB（Load Balance 负载均衡）
2. 进程内LB
   * 将LB逻辑集成到消费方，消费方从注册中心获知有哪些地址可用（获取后缓存在消费方），然后再从这些地址中选择出一个合适的服务地址
   * Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址

学习这些组件就是为了解决分布式的各种问题

### Spring Cloud Open Feign

#### OpenFeign是什么

1. OpenFeign是一个声明式WebService客户端，使用OpenFeign让编写Web Service客户端更简单（WebService就是用来进行远程调用的）
2. 它的使用方法是定义一个服务接口，然后在上面添加注解
3. OpenFeign也支持可插拔式的编码器和解码器（所谓插拔式，就是加上注解就有用，去掉就没用）
4. Spring Cloud对OpenFeign进行了封装使其支持了Spring MVC标准注解和HttpMessageConverters
5. OpenFeign可以和Eureka和Ribbon组合使用以支持负载均衡

#### Feign和OpenFeign的区别

* Feign
  * Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端
  * Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务
  * Feign的使用方式是：使用Feign的注解定义接口，调用服务注册中心的服务
  * Feign支持的注解和用法参考官方文档
  * Feign本身不支持Spring MVC的注解，它有自己的一套注解
* OpenFeign
  * OpenFeign是Spring Cloud在Feign的基础上支持了Spring MVC的注解，如@RequestMapping等等
  * OpenFeign的@FeignClient可以解析Spring MVC的@RequestMapping注解下的接口
  * OpenFeign通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务
* 精简一句话：OpenFeign就是在Feign基础上做了加强，有些程序员为了方便，说Feign就是指的OpenFeign

#### Eureka + OpenFeign的架构

![Eureka-OpenFeign-Architecture](/readme-assets/Eureka-OpenFeign-Architecture.png)

### 什么是网关，为什么要有网关？

#### 没有使用网关服务的问题分析

1. 前端项目需要维护不同的后端服务（ip/访问接口），非常麻烦
2. 如果调用的是后端集群服务，存在负载均衡的问题
3. 没有断言和过滤的机制
4. 因此引出网关服务的必要性

![Without-GateWay-Architecture](/readme-assets/Without-GateWay-Architecture.png)

#### 使用网关服务的架构分析

1. 网关服务提供的功能

   * 对外提供统一的调用接口，根据不同的请求url，转发到对应的后端服务[通过配置即可 断言+过滤]
   * 实现负载均衡
   * 实现限流
   * 实现熔断
   * 实现鉴权，判断请求的url是否合法，以及访问的用户的权限

2. 使用网关服务的架构图

   ![With-GateWay-Architecture](/readme-assets/With-GateWay-Architecture.png)

### Spring Cloud Gateway

#### 什么是Gateway

1. Gateway是在Spring生态系统之上构建的API网关服务，基于Spring、Spring Boot和Project Reactor等技术
2. Gateway旨在提供一种简单而有效的方式来对API进行路由，以及提供一些强大的过滤器功能，例如：熔断、限流、重试等

#### Gateway和Zuul的区别

1. Spring Cloud Gateway作为Spring Cloud生态系统中的网关，目标是替代Zuul
2. Spring Cloud Gateway是基于Spring WebFlux框架实现的
3. Spring WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty，提升了网关性能

#### Gateway的特性

Spring Cloud Gateway基于Spring Framework（支持Spring WebFlux），Project Reactor和Spring Boot进行构建，具有如下特性：

1. 动态路由
2. 可以对路由指定Predicate（断言，也就是判断）和Filter（过滤器）
3. 集成Hystrix的断路由功能
4. 集成Spring Cloud服务发现功能
5. 请求限流功能
6. 支持路径重写

#### 创建Gateway项目架构

1. 通过网关暴露的接口，实现调用真正的服务
2. 网关本身也是一个微服务模块

![GateWay-Project-Architecture](/readme-assets/GateWay-Project-Architecture.png)

#### Gateway项目加上动态路由

1. 配置好动态路由后Gateway会根据注册中心上的微服务名，为请求创建动态路由，实现动态路由功能
2. 使用的lb协议支持负载均衡-轮询算法
3. 配置自己的负载均衡算法

### Gateway Filter

#### Gateway是如何工作的

1. 客户端向Spring Cloud Gateway发出请求，然后在Gateway Handler Mapping中找到与请求相匹配的路由，将其发送到Gateway Web Handler
2. Handler再通过指定的过滤器链来将请求发送到实际的业务执行逻辑
3. 过滤器可能会在发送代理请求之前（pre）或之后（post）执行业务逻辑，即可以在请求被路由之前或者之后对请求进行处理，可以理解为：在对Http请求断言匹配成功后，可以通过网关的过滤机制，对Http请求处理
4. Filter在pre类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等
5. 在post类型的过滤器中可以做响应内容、响应头的修改、日志的输出、流量监控等有着非常重要的作用
6. 一句话总结：路由转发 + 执行过滤器链

### Sleuth/Zipkin

#### 概念

1. 在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的服务节点调用，来协同产生最后的请求结果，每一个请求都会形成一条复杂的分布式服务调用链路

2. 链路中的任何一环出现高延时或错误都会引起整个请求最后的失败，因此对整个服务的的调用进行链路追踪和分析就非常的重要

3. Sleuth和Zipkin的简单关系图

   ![Sleuth-Zipkin-Relation](/readme-assets/Sleuth-Zipkin-Relation.png)

4. Sleuth提供了一套完整的服务跟踪的解决方案 并兼容Zipkin

5. 总结：Sleuth做链路追踪，Zipkin做数据搜集/存储/可视化

#### Sleuth工作原理

* 一条链路通过Trace Id唯一标识，Span标识发起的请求信息，各span通过parent id关联起来

* Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识

* Span：基本工作单元，表示调用链路来源，通俗的理解span就是一次请求信息

* spans的parent/child关系图形化

  ![Sleuth-Span-Structure](/readme-assets/Sleuth-Span-Structure.png)

