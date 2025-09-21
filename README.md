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

6. Zipkin的本地访问地址：http://localhost:9411/zipkin/

#### Sleuth工作原理

* 一条链路通过Trace Id唯一标识，Span标识发起的请求信息，各span通过parent id关联起来

* Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识

* Span：基本工作单元，表示调用链路来源，通俗的理解span就是一次请求信息

* spans的parent/child关系图形化

  ![Sleuth-Span-Structure](/readme-assets/Sleuth-Span-Structure.png)

* 通过Sleuth + Zipkin，可以查看一次调用的深度，以及该链路包含请求，各个请求的耗时，找到请求瓶颈，为优化提供依据（重要）

### Nacos

#### 什么是Nacos

1. 一句话：Nacos就是注册中心：替代Eureka + 配置中心：替代Config；
2. Nacos：Dynamic Naming and Configuration Service
3. Nacos：架构理论基础：CAP理论（支持API + CP，可以切换）

#### 引入Nacos的项目架构

1. 引入Nacos不需要像Eureka那样，单独启动一个项目来运行，直接下载运行即可，默认端口8848，http://localhost:8848/nacos/#/login

2. 新建两个服务提供微服务

   * 创建member-service-nacos-provider-10004，并注册到NacosServer8848
   * 创建member-service-nacos-provider-10006，并注册到NacosServer8848

   视为Nacos Client，注册到Nacos Server 8848

   ![Provider-With-Nacos](/readme-assets/Provider-With-Nacos.png)

3. 配置服务消费方，可以调用到服务提供方的集群，即需要注册服务，也需要发现服务

   ![Consumer-With-Nacos](/readme-assets/Consumer-With-Nacos.png)

#### 选择AP还是CP

1. CP：服务可以不能用，但必须要保证数据的一致性
2. AP：数据可以短暂不一致，但最终是需要一致的，无论如何都要保证服务的可用
3. 取舍：只能在CP和AP选择一个平衡点，大多数都是选择AP模式

#### Nacos切换AP或CP

* Nacos集群默认支持的是CAP原则中的AP原则，但是也可以切换为CP原则（一般不切换）
* CURL切换命令：$NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP
* 这个不能随意切换，建议保持默认的AP即可
* 集群环境下所有的服务都要切换
* 可以使用postman模拟，必须使用put请求，用get和post均无效

#### Nacos配置中心

1. Nacos Server既可以作为注册中心，也可以作为配置中心

2. 新建一个微服务模块作为配置客户端，用于测试获取Nacos Server的配置数据

3. 加入Nacos Server作为配置中心后的项目结构图

   ![ConfigCenter-With-Nacos](/readme-assets/ConfigCenter-With-Nacos.png)

4. 在Nacos Server加入配置

   ![Nacos-Server-Config](/readme-assets/Nacos-Server-Config.png)

5. 注意事项和细节

   * 在配置文件application.yml和bootstrap.yml结合会得到配置文件/资源的地址
   * 参考文档：https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
   * 注意在Nacos Server的配置文件的后缀是.yaml，而不是.yml
   * 在项目初始化时，要保证先从配置中心进行配置拉取，拉取配置之后，才能保证项目的正常启动，也就是说如果项目不能正常的获取到Nacos Server的配置数据，项目是启动不了的
   * spring boot中配置文件的加载是存在优先级顺序的，bootstrap.yml优先级高于application.yml
   * @RefreshScope是springcloud原生注解，实现配置信息自动刷新，如果在Nacos Server修改了配置数据，Client端就会得到最新配置

   #### Nacos配置隔离方案

   1. DataId方案：引入Nacos配置中心 + Data Id

      ![Nacos-Seperation-DataId](/readme-assets/Nacos-Seperation-DataId.png)

   2. Group方案

      ![Nacos-Seperation-Group](/readme-assets/Nacos-Seperation-Group.png)

   3. Namespace方案

      ![Nacos-Seperation-Namespace](/readme-assets/Nacos-Seperation-Namespace.png)

   4. Namespace/Group/Data Id关系

      ![Namespace-Group-DataId](/readme-assets/Namespace-Group-DataId.png)

      * Nacos默认的命名空间是public，Namespace主要用来实现配置隔离，隔离范围大
      * Group默认是DEFAULT_GROUP，Group可以把不同的微服务划分到同一个分组里去
      * Service就是微服务，相同的Service可以是一个Cluster，Instance就是微服务实例

      


### SpringCloud Alibaba Sentinel

#### Sentinel基础

1. 什么是Sentinel

   随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性

   一句话：Sentinel是分布式系统的流量防卫兵，保护你的微服务

2. Sentinel的主要特性

   Sentinel可以完成的功能：绿色方框列出的部分

   ![Sentinel-Features](/readme-assets/Sentinel-Features.png)

#### Sentinel核心功能

1. 流量控制

   比如故宫景区每天只卖8万张票，超过八万的游客，就无法买票进入，只卖N张票，这就是一种限流的手段

2. 熔断降级

   在调用系统的时候，如果调用链路中的某个资源（比如I/O）出现了不稳定，最终会导致请求发生堆积

   熔断降级可以解决这个问题，所谓的熔断降级就是当检测到调用链路中某个资源出现不稳定的表现，例如请求相应时间长或异常比例升高的时候，则对这个资源的调用进行限制，让请求快速失败，避免影响到其他的资源而导致级联故障

3. 系统负载保护

   根据系统能够处理的请求，和允许进来的请求，来做平衡，追求的目标是在系统不被拖垮的情况下，提高系统的吞吐率

4. 消息削峰填谷

   某瞬时来了大流量的请求，而如果此时要处理所有请求，很可能会导致系统负载过高，影响稳定性。但其实可能后面几秒之内都没有消息投递，若直接把多余的消息丢掉则没有充分利用系统处理消息的能力

   Sentinel的Rate Limiter模式能在某一段时间间隔内以匀速方式处理这样的请求，充分利用系统的处理能力，也就是削峰填谷，保证资源的稳定性

#### Sentinel的两个组成部分

1. 核心库：（Java客户端）不依赖任何框架/库，能够运行在所有Java运行时环境（有版本对应关系），对Spring Cloud有较好的支持，用于集成到某个需要被保护的微服务上

2. 控制台：（Dashboard）基于Spring Boot开发，打包后可以直接运行，不需要额外的Tomcat等应用容器，是用来进行监控和展示被保护的微服务的，同时也可以进行限流策略修改等操作

   

#### 搭建Sentinel控制台

1. 下载jar包：sentinel-dashboard-1.8.0.jar

2. 进入目录，使用bash命令启动

   ```bash
   export JAVA_HOME=`/usr/libexec/java_home -v 1.8` #because default JDK is not JDK 8
   java -jar sentinel-dashboard-1.8.0.jar --server.port=9999
   ```

3. Sentinel Dashboard的默认端口为8080，使用--server.port=9999指定端口

   http://localhost:8080/#/login

   用户名密码默认均为：sentinel

#### 引入Sentinel的项目架构

1. 项目结构图

   ![Sentinel-In-Architecture](/readme-assets/Sentinel-In-Architecture.png)

2. 当调用member-service-nacos-provider-10004微服务时，可以监控到请求的url/QPS/响应时间/流量

#### Sentinel监控微服务/流量控制

1. QPS：Queries Per Second（每秒查询率/每秒请求数量），是服务器每秒响应的查询次数

2. Sentinel采用的是懒加载，只有调用了某个接口/服务，才能看到监控数据

3. 通过QPS来进行流量控制/注意事项和细节

   * 流量规则改动，实时生效，不需要重启微服务，Sentinel控制台

   * 在Sentinel配置流量规则时，如何配置通配符问题，比如/member/get/1 /member/get/2统一使用一个规则

     * 方案1：在Sentinel中，/member/get?id=1 和 /member/get?id=2 被统一认为是/member/get 所以只要对/member/get限流就ok了

       对应也要修改服务提供方provider中controller中的方法

       修改为

       ```java
       @GetMapping(value = "/member/get", params = "id")
           public Result getMemberById(Long id) {{}
       ```

       

     * 方案2：URL资源清洗

       可以通过UrlCleaner接口来实现资源清洗，也就是对于/member/get/{id}这个URL，可以统一归集到/member/get/*资源下，实现UrlCleaner接口，并重写clean方法即可

   * 如果Sentinel流控规则没有持久化，当重启被调用API所在微服务模块后，规则会丢失，需要重新添加

4. 通过线程数来进行流量控制

   要求：通过Sentinel实现流量控制，当调用member-service-nacos-provider-10004的/member/get/*接口/API时，限制只有一个工作线程，否则直接失败，抛异常

   注意事项和细节：

   1. 当请求一次微服务的API接口时，后台会启动一个线程
   2. 阈值类型 QPS 和 线程数 的区别讨论
      * 如果一个线程平均执行时间为0.05秒，就说明在1秒内，可以执行20次，相当于QPS为20
      * 如果一个线程平均执行时间为2秒，说明2秒钟内，才能执行1次请求

5. 通过关联来进行流量控制

   关联的含义：当关联的资源达到阈值时，就限流自己

   需求：通过Sentinel实现流量控制，当调用member-service-nacos-provider-10004的/t2 API接口时，如果QPS超过1，这时调用/t1 API接口 直接失败，抛出异常，此时/t2就是关联的资源，限流的资源就是/t1

   ![Sentinel-Flow-Limitation-Relation](/readme-assets/Sentinel-Flow-Limitation-Relation.png)

   测试：因为测试效果需要同时访问/t1 和 /t2，因此使用postman模拟高并发，访问/t2

   ![Postman-Simulation-High-Concurrency](/readme-assets/Postman-Simulation-High-Concurrency.png)

   在postman执行高并发访问/t2没有结束时，去访问/1才能看到流控异常出现

6. 通过Warm up来进行流量控制

   Warm Up介绍

   1. 概述
      * 当流量突然增大的时候，我们常常会希望系统从空闲状态到繁忙状态的切换的时间长一些。即如果系统在此之前长期处于空闲的状态，我们希望处理请求的数量是缓步的增多，经过预期的时间以后，到达系统处理请求个数的最大值。Warm Up（冷启动/预热）模式就是为了实现这个目的的
      * 应用场景：秒杀在开启瞬间，大流量很容易造成冲垮系统，Warm up可慢慢的把流量翻入，最终将阈值增长到设置阈值
   2. 梳理
      * 通常冷启动的过程系统允许通过的QPS曲线
      * 默认coldFactor为3，即请求的QPS从threshold/3开始，经预热时长主键升至设定的QPS阈值
      * 这里的threshold就是最终要达到的QPS阈值

   需求

   1. 通过Sentinel实现 流量控制，使用Warm up

   2. 调用member-service-nacos-provider-10004的/t2 API接口，将QPS设置为9，设置Warm up值为3

   3. 含义为 请求/t2的QPS从threshold / 3（9 / 3 = 3）开始，经预热时长（3秒）逐渐升至设定的QPS阈值（9）

   4. 为什么是9 / 3，这个3是默认冷启动加载因子 coldFactor=3

   5. 测试预期效果：在前3秒，如果访问/t2的QPS超过3，会直接报错，在3秒后，访问/t2的QPS，小于等于9，是正常访问

      ![Sentinel-Flow-Limitation-Warmup](/readme-assets/Sentinel-Flow-Limitation-Warmup.png)

   注意事项和细节

   1. 测试Warm up不是很好测试，如果出不来可以尝试，调整流控规则：比如将QPS调整为11，Warm up预热时间6秒
   2. 如果请求停止（即：一段时间内没有达到阈值），Warm up过程将重复，可以理解这是一个弹性过程

7. 通过排队来进行流量控制

   基本介绍：

   1. 排队方式：这种方式严格控制了请求通过的间隔时间，也即是让请求匀速通过，对应的是漏桶算法
   2. 例如：阈值QPS=2时，每隔500ms才允许通过下一个请求
   3. 这种方式主要用于处理间隔性突发的流量，例如消息队列。比如这样的场景，在某一秒有大量的请求到来，而接下来的几秒则处于空闲状态，我们希望系统能够在接下来的空闲期间逐渐处理这些请求，而不是在第一秒直接拒绝多余的请求，类似于前面说的削峰填谷
   4. 匀速排队，阈值必须设置为QPS 

   需求

   1. 需求：通过Sentinel实现 流量控制-排队

   2. 调用member-service-nacos-provider-10004的 /t2 API接口，将QPS设置为1

   3. 当调用/t2的QPS超过1时，不拒绝请求，而是排队等待，依次执行

   4. 当等待时间超过10秒，则为等待超时

      ![Sentinel-Flow-Limit-Queue](/readme-assets/Sentinel-Flow-Limit-Queue.png)

#### Sentinel熔断降级

1. 线程堆积引出熔断降级

   * 一个服务常常会调用别的模块，可能是另一个远程服务、数据库、或者第三方API等
   * 例如：支付的时候，可能需要远程调用银联提供的API，查询某个商品的价格，可能需要进行数据库查询。然后，这个被依赖的服务的稳定性是不能保证的。如果依赖的服务出现了不稳定的情况，请求的相应时间变长，那么调用服务的方法的响应时间也会变长，线程会在服务调用方堆积，最终可能耗尽业务自身的线程池，服务调用方本身作为服务也变得不可用
   * 这是，就需要对不稳定的服务（被调用的服务）进行熔断降级，让其快速返回结果，不要造成线程堆积
   * 熔断是熔断，降级是降级，限流是限流，三者互相作用，但是不是一个东西

2. 熔断降级存在的作用

   * 现代微服务架构都是分布式的，有非常多的服务组成，不同服务之间相互调用，组成复杂的调用链路
   * 链路调用中会产生放大的效果。复杂链路上的某一环不稳定，就可能会层层级联，最终导致整个链路都不可用
   * 因此需要对不稳定的弱依赖服务（即非核心服务）调用进行熔断降级，暂时切断不稳定调用，避免局部不稳定因素导致整体的雪崩

3. 熔断、降级、限流三者的关系

   * 熔断强调的是服务之间的调用能实现自我恢复的状态，熔断是一个暂时的状态，还是可以恢复的，熔断是降级的一种方式
   * 限流是从系统的流量入口考虑，从进入的流量上进行限制，达到保护系统的作用
   * 降级，是从业务系统的维度考虑，流量大了或者频繁异常，可以牺牲一些非核心业务，保护核心流程正常使用
   * 梳理：熔断是降级方式的一种，降级又是限流的一种方式，三者都是为了通过一定的方式在流量过大或者出现异常时，保护系统的手段

4. 熔断策略

   * 慢调用比例

     1. 慢比例调用（SLOW_REQUEST_RATIO）：选择以慢调用比例为阈值，需要设置允许的慢调用RT（即最大的响应时间），请求的响应时间大于该值则统计为慢调用
     2. 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断
     3. 熔断时长后，熔断器会进入探测恢复状态（HALF-OPEN(半开)状态），若接下来的一个请求响应时间小于设置的慢调用RT，则结束熔断，若大于设置的慢调用RT则会再次被熔断

   * 异常比例

     1. 异常比例（ERROR_RATIO）：当单位统计时长（statIntervalMs）内请求数目大于配置的最小请求数目，并且异常的比例大于阈值，则接下来的熔断时长内请求会自动熔断

     2. 经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN状态）

     3. 若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断

     4. 异常比率的阈值范围是[0.0, 1.0]，代表0% - 100%

        ![Error-Ratio-Mechanism](/readme-assets/Error-Ratio-Mechanism.png)

   * 异常数

     1. 异常数（ERROR_COUNT）：当单位统计时长内（1分钟）的请求数目大于配置的最小请求数目，且异常数目超过阈值之后会自动进行熔断
     2. 经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN状态）
     3. 若接下来的一个请求完成完成（没有错误）则结束熔断，否则会再次被熔断

5. 熔断策略实例

   1. 慢调用比例

      需求：

      * 通过Sentinel实现熔断降级控制-慢调用比例

      * 当调用member-service-nacos-provider-10004 的t3 API接口时，如果在1s内持续进入了5个请求，并且请求的平均响应时间超过200ms，那么就在未来10s内，断路器打开，让t3 API接口 微服务不可用

      * 后面对/t3 API接口 访问降到1s内1个请求，降低访问量的，短路器关闭，微服务恢复

        ![Sentinel-Fuse-SlowRequestRatio](/readme-assets/Sentinel-Fuse-SlowRequestRatio.png)

      * 平均响应时间 超出阈值 且 在1s内通过的请求 >= 5，两个条件同时触发后触发降级

      * 熔断时间过后，关闭熔断器，访问恢复正常，之后再次触发，则再次熔断

   2. 异常比例

      需求：

      * 通过Sentinel实现熔断降级控制，通过异常比例

      * 当调用member-service-nacos-provider-10004的/t4 API接口时，当资源的每秒请求量>=5，并且每秒异常总数占通过量的比值超过20%（即异常比例到20%），断路器打开（即：进入降级状态），让/t4 API接口 微服务不可用

      * 当对/t4 API接口 访问降到1s内1个请求，降低访问量了，断路器关闭，5秒后微服务恢复

        ![Sentinel-Fuse-ErrorRate](/readme-assets/Sentinel-Fuse-ErrorRate.png)

        Blocked by Sentinel (flow limiting)

      * 当资源的每秒请求数 >= 5，并且每秒异常总数占通过量的比值超过阈值，资源进入降级状态，需要两个条件都满足

      * 测试时，如果熔断等级和恢复服务两个状态切换不明显，将两个窗口值调整大一些比如60，就可以了

   3. 异常数

      需求：

      * 通过Sentinel实现熔断降级控制

      * 当调用member-service-nacos-provider-10004的/t5 API接口时，当资源的每分钟请求量>=5，并且每分钟异常总数>=5，断路器打开（即：进入降级状态），让/t5 API接口微服务不可用

      * 当熔断时间（比如20s）结束后，断路器关闭，微服务恢复

      * 降级规则配置

        ![Sentinel-Fuse-ErrorNum](/readme-assets/Sentinel-Fuse-ErrorNum.png)

      * 资源在1分钟的异常数目超过阈值之后会进行熔断升级

      * 异常数统计是分钟级别的，若设置的时间窗口小于60s，则结束熔断状态后仍可能再进入熔断状态，测试时，最好将时间窗口设置超过60s

#### Sentinel热点规则

1. 一个问题引出热点key限流

   * 热点：热点即经常访问的数据，很多时候我们希望统计热点数据中，访问频次最高的Top K数据，并对其访问进行限制
   * 比如某条新闻上热搜，在某段时间内高频访问，为了防止系统雪崩，可以对该条新闻进行热点限流，当然限制可以设置的大一些，比如普通新闻的QPS为100，热点新闻的QPS是300
   * 比起流量控制，热点限流是针对某个具体的数据，比如某个新闻的id，某个用户的id，相比之下粒度更低

2. 热点限流介绍

   ![Hot-Point-Flow-Limitation](/readme-assets/Hot-Point-Flow-Limitation.png)

   * 热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值和模式，对包含热点参数的资源进行限流
   * 热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效
   * Sentinel利用LRU策略统计最近最常访问的热点参数，结合令牌桶算法来进行参数级别的流控
   * 热点参数限流支持集群模式

3. 热点限流实例

   需求：

   * 通过Sentinel实现 热点key限流
   * 对member-service-nacos-provider-10004的/news?id=x&type=x API接口进行热点限流
   * 假定id=10 这一条新闻是当前的热点新闻，当查询新闻时，对通常的id（非热点新闻）请求OPS限定为2，如果id=10，QPS限定为100
   * 如果访问超出了规定的QPS，触发热点限流机制，调用自定义的方法，给出提示信息
   * 当对/news?id=x&type=x API接口 降低访问量，QPS达到规定范围，服务恢复

   实现：

   * 配置

     ![Hot-Point-Configuration](/readme-assets/Hot-Point-Configuration.png)

   * 独立设置热点id=10的QPS阈值（即添加例外）

     ![Hot-Point-Configuration-Exception](/readme-assets/Hot-Point-Configuration-Exception.png)

   * 浏览器访问id不是10的新闻，仍然遵守QPS不能超过2的热点限制

4. 注意事项和细节

   * 热点参数类型可以是：byte/int/long/float/double/char/String
   * 热点参数值，可以配置多个
   * 热点规则只对指定的参数生效（比如本实例对id生效，对type不生效）

#### Sentinel系统规则/系统自适应限流

1. 一个问题引出系统规则

   * 如果系统的最大性能能抗 100QPS，如何分配/t1 /t2的QPS
   * 方案1：/t1分配QPS=50 /t2分配QPS=50，问题，如果/t1当前的QPS达到50，而/t2的QPS才10，会造成没有充分利用服务器性能
   * 方案2：/t1分配QPS=100 /t2分配QPS=100，问题，容易造成系统没有流量保护，造成请求线程堆积，形成雪崩
   * 有没有对各个资源请求的QPS弹性设置，只要总数不超过系统最大QPS的流量保护规则？==> 系统规则/系统自适应限流
   * 一句话：系统规则作用，在系统稳定的前提下，保持系统的吞吐量

2. 基本介绍

   * 系统处理请求的过程想象为一个水管，到来的请求是往这个水管灌水，当系统处理顺畅的时候，请求不需要排队，直接从水管中穿过，这个请求的RT是最短的；
   * 反之， 当请求堆积的时候， 那么处理请求的时间则会变为： 排队时间 + 最短处理时间
   * 系统规则：
     * Load 自适应 （仅对 Linux/Unix-like 机器生效） ： 系统的 load1 作为启发指标， 进行自适应系统保护。当系统 load1 超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护 （BBR 阶段） 。 系统容量由系统的 maxQps * minRt 估算得出。设定参考值一般是 CPU cores * 2.5。
     * CPU usage（1.5.0+ 版本） ：当系统 CPU 使用率超过阈值即触发系统保护（取值范围0.0-1.0） ，比较灵敏。
     * 平均 RT： 当单台机器上所有入口流量的平均 RT 达到阈值即触发系统保护， 单位是毫秒。
     * 并发线程数：当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护。
     * 入口 QPS：当单台机器上所有入口流量的 QPS 达到阈值即触发系统保护。 

3. 需求实例

   需求：

   * 通过Sentinel实现系统规则-入口QPS
   * 对member-service-nacos-provider-10004的所有API接口实现流量保护，不论访问哪个API接口，系统入口总的QPS不能大于2，大于2，就进行限流控制
   * 提示：上面的QPS只是为了方便看效果，所以才设置的很小

   实例：

   ![System-Rule-Entry-QPS](/readme-assets/System-Rule-Entry-QPS.png)

#### @SentinelResource

##### 自定义全局限流处理类

验证配置

![Custom-Global-Block-Handler](/readme-assets/Custom-Global-Block-Handler.png)

验证地址：http://localhost:10004/t6

















