# 参考文档

https://www.sofastack.tech/projects/sofa-boot/quick-start/ 《SOFABoot 快速开始》

https://help.aliyun.com/document_detail/133243.html 《SOFABoot 快速入门》

https://www.sofastack.tech/projects/sofa-tracer/usage-of-mvc/ 《SOFATracer 日志 Spring MVC 埋点接入》

https://help.aliyun.com/document_detail/151860.html 《SOFATracer 日志说明》

https://help.aliyun.com/document_detail/151854.html 《SOFATracer 日志采样模式》

https://github.com/sofastack/sofa-tracer 《SOFATracer 源码地址》



# 快速开始


SOFABoot 版本和 Spring Boot 版本对应关系如下：

| SOFABoot 版本  | Spring Boot 版本 |
| -------------- | ---------------- |
| 3.3.0～3.3.1   | 2.1.11.RELEASE   |
| 3.3.2～3.6.0   | 2.1.13.RELEASE   |
| 3.7.0～3.10.0  | 2.3.9.RELEASE    |
| 3.11.0～3.11.1 | 2.3.12.RELEASE   |
| 3.12.0～3.13.0 | 2.4.13           |
| 3.14.0～3.14.1 | 2.6.9            |
| 3.15.0～3.16.3 | 2.7.3            |
| 3.17.0         | 2.7.8            |
| 3.18.0～3.19.1 | 2.7.10           |
| 4.0.0          | 3.0.7            |



为顺利从中央仓库拉取 SNAPSHOT 包，需要在本地 maven setting.xml 文件增加如下 profile 配置:

```xml
<profile>
    <id>default</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <repositories>
        <repository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>maven-snapshot</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>maven-snapshot</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </pluginRepository>
    </pluginRepositories>
</profile>
```



## 创建工程

创建好一个 Spring Boot 的工程

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.12.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-sample</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-sample</name>
    <description>sofaboot-quickstart-sample</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



## 依赖管理

引入 SOFABoot 的依赖

修改 maven 项目的配置文件 `pom.xml`，将

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>${spring.boot.version}</version>
    <relativePath/> 
</parent>
```

替换为：

```xml
<parent>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofaboot-dependencies</artifactId>
    <version>${sofa.boot.version}</version>
</parent>
```



添加 SOFABoot 健康检查扩展能力的依赖及 Web 依赖(方便查看健康检查结果)：

```xml
<!-- import SOFABoot Dependency healthcheck and infra -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>healthcheck-sofa-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



最后，在工程的 `application.properties` 文件下添加 SOFABoot 工程常用的参数配置，其中 `spring.application.name` 是必需的参数，用于标示当前应用的名称；`logging path` 用于指定日志的输出目录。

```properties
# Application Name
spring.application.name=sofaboot-quickstart-sample
# logging path
logging.path=./logs
management.endpoint.health.show-details=always
```



## 启动应用

可以将工程导入到 IDE 中运行生成的工程里面中的 `main` 方法（一般上在 XXXApplication 这个类中）启动应用，也可以直接在该工程的根目录下运行 `mvn spring-boot:run`，将会在控制台中看到启动打印的日志：

```
 ,---.    ,-----.  ,------.   ,---.     ,-----.                     ,--.
'   .-'  '  .-.  ' |  .---'  /  O  \    |  |) /_   ,---.   ,---.  ,-'  '-.
`.  `-.  |  | |  | |  `--,  |  .-.  |   |  .-.  \ | .-. | | .-. | '-.  .-'
.-'    | '  '-'  ' |  |`    |  | |  |   |  '--' / ' '-' ' ' '-' '   |  |
`-----'   `-----'  `--'     `--' `--'   `------'   `---'   `---'    `--'


Spring Boot Version: 2.3.12.RELEASE (v2.3.12.RELEASE)
SOFABoot Version: 3.11.1 (v3.11.1)
Powered By Ant Group

2024-05-12 03:12:22.840  INFO 31384 --- [main] : SOFABoot Runtime Starting!
2024-05-12 03:12:22.849  INFO 31384 --- [main] : Starting SofabootQuickstartApplication on LAPTOP-0UIAERQR with PID 99999 (C:\xxx\xxx\sofaboot-quickstart\target\classes started by root in C:\xxx\xxx\sofaboot-quickstart)
2024-05-12 03:12:22.850  INFO 31384 --- [main] : No active profile set, falling back to default profiles: default
2024-05-12 03:12:23.934  INFO 31384 --- [main] : Tomcat initialized with port(s): 8080 (http)
2024-05-12 03:12:23.944  INFO 31384 --- [main] : Starting service [Tomcat]
2024-05-12 03:12:23.944  INFO 31384 --- [main] : Starting Servlet engine: [Apache Tomcat/9.0.46]
2024-05-12 03:12:24.066  INFO 31384 --- [main] : Initializing Spring embedded WebApplicationContext
2024-05-12 03:12:24.066  INFO 31384 --- [main] : Root WebApplicationContext: initialization completed in 1173 ms
2024-05-12 03:12:24.329  INFO 31384 --- [main] : Initializing ExecutorService 'applicationTaskExecutor'
2024-05-12 03:12:24.656  INFO 31384 --- [main] : Exposing 4 endpoint(s) beneath base path '/actuator'
2024-05-12 03:12:24.710  INFO 31384 --- [main] : Tomcat started on port(s): 8080 (http) with context path ''
```



## 版本查看

可以通过在浏览器中输入 http://localhost:8080/actuator/versions 来查看当前 SOFABoot 中使用 Maven 插件生成的版本信息汇总，结果类似如下：

**注: 在 SOFABoot 3.x 中调整了 endpoint 路径，sofaboot/versions 更改为 actuator/versions**

```json
[
    {
        "GroupId": "com.alipay.sofa",
        "Doc-Url": "http://www.sofastack.tech/sofa-boot/docs/Home",
        "ArtifactId": "sofa-boot",
        "Commit-Time": "2022-04-01T11:40:27+0800",
        "Commit-Id": "44ebf7230ed0239f948e83c2ab4310cdb21400e2",
        "Version": "3.11.1",
        "Build-Time": "2022-04-01T13:13:33+0800"
    }
]
```



## 健康检查

可以通过在浏览器中输入 http://localhost:8080/actuator/readiness 查看应用 Readiness Check 的状况，类似如下：

**注: 在 SOFABoot 3.x 中调整了 endpoint 路径，health/readiness 更改为 actuator/readiness**

```json
{
    "status": "UP",
    "details": {
        "SOFABootReadinessHealthCheckInfo": {
            "status": "UP",
            "details": {
                "HealthChecker": {
                    "sofaComponentHealthChecker": {
                        "status": "UP"
                    }
                }
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 294973861888,
                "free": 228125249536,
                "threshold": 10485760,
                "exists": true
            }
        },
        "pingHealthContributor": {
            "status": "UP"
        }
    }
}
```

`status: "UP"` 表示应用 Readiness Check 健康的。



可以通过在浏览器中输入 http://localhost:8080/actuator/health 来查看应用的运行时健康状态（可能会随着时间发生变化）。

**注: 在 SOFABOOT 3.X 中调整了 endpoint 路径，/health 更改为 /actuator/health**

```json
{
    "status": "UP",
    "groups": [
        "liveness"
    ]
}
```



## 扩展 Readiness Check 能力

在 Readiness Check 的各个阶段，SOFABoot 都提供了扩展的能力，应用可以根据自己的需要进行扩展，在 2.x 版本中，可供扩展的点如下：

| 回调接口                                                     | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| org.springframework.context.ApplicationListener              | 如果想要在 Readiness Check 之前做一些事情，那么监听这个 Listener 的 SofaBootBeforeHealthCheckEvent 事件。 |
| org.springframework.boot.actuate.health.HealthIndicator      | 如果想要在 SOFABoot 的 Readiness Check 里面增加一个检查项，那么可以直接扩展 Spring Boot 的这个接口。 |
| com.alipay.sofa.healthcheck.startup.SofaBootAfterReadinessCheckCallback | 如果想要在 Readiness Check 之后做一些事情，那么可以扩展 SOFABoot 的这个接口。 |

在 3.x 版本中，可供扩展点如下：

| 回调接口                                                     | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| com.alipay.sofa.healthcheck.core.HealthChecker               | 如果想要在 SOFABoot 的 Readiness Check 里面增加一个检查项，可以直接扩展该接口。相较于 Spring Boot 本身的 HealthIndicator 接口，该接口提供了一些额外的参数配置，比如检查失败重试次数等。 |
| org.springframework.boot.actuate.health.HealthIndicator      | 如果想要在 SOFABoot 的 Readiness Check 里面增加一个检查项，那么可以直接扩展 Spring Boot 的这个接口。 |
| org.springframework.boot.actuate.health.ReactiveHealthIndicator | 在 WebFlux 中，如果想要在 SOFABoot 的 Readiness Check 里面增加一个检查项，那么可以直接扩展 Spring Boot 的这个接口。 |
| com.alipay.sofa.healthcheck.startup.ReadinessCheckCallback   | 如果想要在 Readiness Check 之后做一些事情，那么可以扩展 SOFABoot 的这个接口。 |

需要指出的是，上述四个扩展接口均可以通过 Spring Boot 标准的 `Ordered`, `PriorityOrdered` 和注解 `@Order` 实现执行顺序的设置。



## Readiness Check 配置项

应用在引入 SOFABoot 的健康检查扩展之后，可以在 Spring Boot 的配置文件 `application.properties` 中添加相关配置项来定制 Readiness Check 的相关行为。

| Readiness Check 配置项                                     | 说明                                        | 默认值          | 开始支持版本                  |
| ---------------------------------------------------------- | ------------------------------------------- | --------------- | ----------------------------- |
| com.alipay.sofa.healthcheck.skip.all                       | 是否跳过整个 Readiness Check 阶段           | false           | 2.4.0                         |
| com.alipay.sofa.healthcheck.skip.component                 | 是否跳过 SOFA 中间件的 Readiness Check      | false           | 2.4.0                         |
| com.alipay.sofa.healthcheck.skip.indicator                 | 是否跳过 HealthIndicator 的 Readiness Check | false           | 2.4.0                         |
| com.alipay.sofa.healthcheck.component.check.retry.count    | 组件健康检查重试次数                        | 20              | 2.4.10 (之前版本重试次数为 0) |
| com.alipay.sofa.healthcheck.component.check.retry.interval | 组件健康检查重试间隔时间                    | 1000 (单位：ms) | 2.4.10 (之前版本重试间隔为 0) |
| com.alipay.sofa.healthcheck.module.check.retry.count       | sofaboot 模块健康检查重试次数               | 0               | 2.4.10                        |
| com.alipay.sofa.healthcheck.module.check.retry.interval    | sofaboot 模块健康检查重试间隔时间           | 1000 (单位：ms) | 2.4.10 (之前版本重试间隔为 0) |



## 查看日志

在上面的 `application.properties` 里面，我们配置的日志打印目录是 `./logs` 即当前应用的根目录（我们可以根据自己的实践需要配置），在当前工程的根目录下可以看到类似如下结构的日志文件：

```
./logs
├── health-check
│   ├── sofaboot-common-default.log
│   └── sofaboot-common-error.log
├── infra
│   ├── common-default.log
│   └── common-error.log
└── spring.log
```

如果应用启动失败或者健康检查返回失败，可以通过相应的日志文件找到错误的原因，有些需要关注 `common-error.log` 日志。



# 启动加速

SOFABoot 提供了模块并行加载以及 Spring Bean 异步初始化能力，用于加快应用启动速度。下面介绍如何使用 SOFABoot 异步初始化 Spring Bean 能力来提高应用启动速度。



## 使用场景

在实际使用 Spring/Spring Boot 开发中，一些 Bean 在初始化过程中执行准备操作，如拉取远程配置、初始化数据源等等。在应用启动期间，这些 Bean 会增加 Spring 上下文刷新时间，导致应用启动耗时变长。

为了加速应用启动，SOFABoot 通过配置可选项，将 Bean 的初始化方法（init-method）使用单独线程异步执行，加快 Spring 上下文加载过程，提高应用启动速度。



## 引入依赖

SOFABoot 在 v2.6.0 开始提供异步初始化 Spring Bean 能力，引入如下 Starter 即可：

```xml
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>runtime-sofa-boot-starter</artifactId>
</dependency>
```



## 使用方法

异步初始化 Bean 的原理是开启单独线程负责执行 Bean 的初始化方法(init-method)，因此在使用过程中，除了引入上述依赖管理，还需要在 Bean 的 xml 定义中配置 `sofa:async-init="true"` 属性，用于指定是否异步执行该 Bean 的初始化方法，例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:sofa="http://sofastack.io/schema/sofaboot"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                   http://sofastack.io/schema/sofaboot   http://sofastack.io/schema/sofaboot.xsd"
       default-autowire="byName">

    <!-- async init test -->
    <bean id="testBean" class="com.alipay.sofa.runtime.beans.TimeWasteBea" init-method="init" sofa:async-init="true"/>
</beans>
```



## 属性配置

SOFABoot 异步初始化能力提供两个属性配置，用于指定负责异步执行 Bean 初始化方法（init-method）的线程池大小：

- `com.alipay.sofa.boot.asyncInitBeanCoreSize`：线程池基本大小，默认值为 CPU 核数加一。
- `com.alipay.sofa.boot.asyncInitBeanMaxSize`：线程池中允许的最大线程数大小，默认值为 CPU 核数加一。

此配置可以通过 VM -D 参数或者 Spring Boot 配置文件 application.yml 设置。



# 模块化开发

参考：https://github.com/sofastack-guides/sofa-boot-guides/tree/master/sofaboot-sample-with-isle



- service-facade: 演示 JVM 服务发布与引用的 API 包；
- service-provider: 演示 XML 方式、Annotation 方式、API 方式发布 JVM 服务；
- service-consumer: 演示 XML 方式、Annotation 方式、API 方式引用 JVM 服务；
- service-run: 启动包含 SOFABoot 模块的 SOFA Boot 应用。



## 定义服务 API

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>sofaboot-quickstart-module</artifactId>
        <groupId>com.sofaboot.quickstart</groupId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>sofaboot-quickstart-service-facade</artifactId>

    <name>sofaboot-quickstart-service-facade</name>
    <description>sofaboot-quickstart-service-facade</description>
</project>
```



service-facade 模块包含用于演示 JVM 服务发布与引用的 API :

```java
package com.sofaboot.quickstart.facade;

/**
 * @author: ljt
 * @version: $Id: SampleJvmService.java, v 0.1 2024/05/12, ljt Exp $
 */
public interface SampleJvmService {
    String message();
}
```



## 发布 JVM 服务

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>sofaboot-quickstart-module</artifactId>
        <groupId>com.sofaboot.quickstart</groupId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>sofaboot-quickstart-service-provider</artifactId>

    <name>sofaboot-quickstart-service-provider</name>
    <description>sofaboot-quickstart-service-provider</description>

    <dependencies>
        <dependency>
            <groupId>com.sofaboot.quickstart</groupId>
            <artifactId>sofaboot-quickstart-service-facade</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alipay.sofa</groupId>
            <artifactId>runtime-sofa-boot-starter</artifactId>
        </dependency>
    </dependencies>
</project>
```

service-provider 是一个 SOFABoot 模块，用于演示 XML 方式、Annotation 方式、API 方式发布 JVM 服务。



### 定义 SOFABoot 模块

为 service-provider 模块增加 sofa-module.properties 文件，将其定义为 SOFABoot 模块:

```properties
Module-Name=com.sofaboot.quickstart.provider
```



### XML 方式发布服务

实现 SampleJvmService 接口:

```java
package com.sofaboot.quickstart.provider;

import com.sofaboot.quickstart.facade.SampleJvmService;

/**
 * @author: ljt
 * @version: $Id: SampleJvmServiceImpl.java, v 0.1 2024/05/12, ljt Exp $
 */
public class SampleJvmServiceImpl implements SampleJvmService {
    private String message;

    public SampleJvmServiceImpl(String message) {
        this.message = message;
    }

    public SampleJvmServiceImpl() {
    }

    @Override
    public String message() {
        System.out.println(message);
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```



增加 META-INF/spring/service-provide.xml 文件，将 SampleJvmServiceImpl 发布为 JVM 服务:

```xml
<bean id="sampleJvmService" class="com.sofaboot.quickstart.provider.SampleJvmServiceImpl">
	<property name="message" value="Hello, jvm service xml implementation."/>
</bean>

<sofa:service ref="sampleJvmService" interface="com.sofaboot.quickstart.facade.SampleJvmService">
	<sofa:binding.jvm/>
</sofa:service>
```



### Annotation 方式发布服务

实现 SampleJvmService 接口并增加 @SofaService 注解:

```java
package com.sofaboot.quickstart.provider;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.sofaboot.quickstart.facade.SampleJvmService;

/**
 * @author: ljt
 * @version: $Id: SampleJvmServiceAnnotationImpl.java, v 0.1 2024/05/12, ljt Exp $
 */
@SofaService(uniqueId = "annotationImpl")
public class SampleJvmServiceAnnotationImpl implements SampleJvmService {
    @Override
    public String message() {
        String message = "Hello, jvm service annotation implementation.";
        System.out.println(message);
        return message;
    }
}
```



为了区分 XML 方式发布的 JVM 服务，注解上需要增加 uniqueId 属性。

将 SampleJvmServiceAnnotationImpl 配置成一个 Spring Bean，保证 @SofaService 注解生效:

```xml
<bean id="sampleJvmServiceAnnotation" class="com.sofaboot.quickstart.provider.SampleJvmServiceAnnotationImpl"/>
```



### API 方式发布服务

增加 PublishServiceWithClient 类，演示 API 方式发布服务:

```java
package com.sofaboot.quickstart.provider;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ServiceClient;
import com.alipay.sofa.runtime.api.client.param.ServiceParam;
import com.sofaboot.quickstart.facade.SampleJvmService;

/**
 * @author: ljt
 * @version: $Id: PublishServiceWithClient.java, v 0.1 2024/05/12, ljt Exp $
 */
public class PublishServiceWithClient implements ClientFactoryAware {
    private ClientFactory clientFactory;

    public void init() {
        ServiceClient serviceClient = clientFactory.getClient(ServiceClient.class);
        ServiceParam serviceParam = new ServiceParam();
        serviceParam.setInstance(new SampleJvmServiceImpl("Hello, jvm service service client implementation."));
        serviceParam.setInterfaceType(SampleJvmService.class);
        serviceParam.setUniqueId("serviceClientImpl");
        serviceClient.service(serviceParam);
    }

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
```



将 PublishServiceWithClient 配置为 Spring Bean，并设置 init-method ，使PublishServiceWithClient 在 Spring 刷新时发布服务:

```xml
<bean id="publishServiceWithClient" class="com.sofaboot.quickstart.provider.PublishServiceWithClient" init-method="init"/>
```



## 引用 JVM 服务

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>sofaboot-quickstart-module</artifactId>
        <groupId>com.sofaboot.quickstart</groupId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>sofaboot-quickstart-service-consumer</artifactId>

    <name>sofaboot-quickstart-service-consumer</name>
    <description>sofaboot-quickstart-service-consumer</description>

    <dependencies>
        <dependency>
            <groupId>com.sofaboot.quickstart</groupId>
            <artifactId>sofaboot-quickstart-service-facade</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alipay.sofa</groupId>
            <artifactId>runtime-sofa-boot-starter</artifactId>
        </dependency>
    </dependencies>
</project>
```

service-consumer 是一个 SOFABoot 模块，用于演示 XML 方式、Annotation 方式、API 方式引用 JVM 服务。



### 定义 SOFABoot 模块

为 service-consumer 模块增加 sofa-module.properties 文件，将其定义为 SOFABoot 模块:

```properties
Module-Name=com.sofaboot.quickstart.consumer
Require-Module=com.sofaboot.quickstart.provider
```

在 sofa-module.properties 文件中需要指定 Require-Module，保证 service-provider 模块在 service-consumer 模块之前刷新。



### XML 方式引用服务

增加 META-INF/spring/service-consumer.xml 文件，引用 service-provider 模块发布的服务:

```xml
<sofa:reference id="sampleJvmService" interface="com.sofaboot.quickstart.facade.SampleJvmService">
    <sofa:binding.jvm/>
</sofa:reference>
```



### Annotation 方式引用服务

定义 JvmServiceConsumer 类，并在其 sampleJvmServiceAnnotationImpl 属性上增加 @SofaReference 注解:

```java
public class JvmServiceConsumer implements ClientFactoryAware {
    @SofaReference(uniqueId = "annotationImpl")
    private SampleJvmService sampleJvmServiceAnnotationImpl;
}
```

将 JvmServiceConsumer 配置成一个 Spring Bean，保证 @SofaReference 注解生效:

```xml
<bean id="consumer" class="com.sofaboot.quickstart.consumer.JvmServiceConsumer" init-method="init"/>
```



### API 方式引用服务

JvmServiceConsumer 实现 ClientFactoryAware 接口，并在其 init 方法中引用 JVM 服务:

```java
package com.sofaboot.quickstart.consumer;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import com.sofaboot.quickstart.facade.SampleJvmService;

/**
 * @author: ljt
 * @version: $Id: JvmServiceConsumer.java, v 0.1 2024/05/12, ljt Exp $
 */
public class JvmServiceConsumer implements ClientFactoryAware {
    private ClientFactory clientFactory;

    public void init() {
        ReferenceClient referenceClient = clientFactory.getClient(ReferenceClient.class);
        ReferenceParam<SampleJvmService> referenceParam = new ReferenceParam<SampleJvmService>();
        referenceParam.setInterfaceType(SampleJvmService.class);
        referenceParam.setUniqueId("serviceClientImpl");
        SampleJvmService sampleJvmServiceClientImpl = referenceClient.reference(referenceParam);
        sampleJvmServiceClientImpl.message();
    }

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
```



## 启动 SOFABoot 应用

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>sofaboot-quickstart-module</artifactId>
        <groupId>com.sofaboot.quickstart</groupId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>sofaboot-quickstart-service-run</artifactId>

    <name>sofaboot-quickstart-service-run</name>
    <description>sofaboot-quickstart-service-run</description>

    <dependencies>
        <!-- import SOFABoot Dependency healthcheck and infra -->
        <dependency>
            <groupId>com.alipay.sofa</groupId>
            <artifactId>healthcheck-sofa-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alipay.sofa</groupId>
            <artifactId>isle-sofa-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.sofaboot.quickstart</groupId>
            <artifactId>sofaboot-quickstart-service-provider</artifactId>
        </dependency>
        <dependency>
            <groupId>com.sofaboot.quickstart</groupId>
            <artifactId>sofaboot-quickstart-service-consumer</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- http://docs.spring.io/spring-boot/docs/current/maven-plugin/usage.html -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <!-- http://docs.spring.io/spring-boot/docs/current/maven-plugin/usage.html -->
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!-- executable fat jar -->
                    <outputDirectory>../target/boot</outputDirectory>
                    <classifier>executable</classifier>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```



将模块 parent 配置为 SOFABoot:

```xml
<parent>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofaboot-dependencies</artifactId>
    <version>3.11.1</version>
</parent>
```



为模块增加 isle-sofa-boot-starter 及 service-provider 、 service-consumer 依赖:

```xml
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>isle-sofa-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-service-provider</artifactId>
</dependency>
<dependency>
    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-service-consumer</artifactId>
</dependency>
```



启动 ApplicationRun 类，控制台将看到以下输出:

```text
Hello, jvm service xml implementation.
Hello, jvm service annotation implementation.
Hello, jvm service service client implementation.
```



## 编写测试用例

SOFABoot 模块化测试方法与 Spring Boot 测试方法一致，只需在测试用例上增加 @SpringBootTest 注解及 @RunWith(SpringRunner.class) 注解即可。在测试用例中，还可以使用 @SofaReference 注解，对 SOFABoot 模块发布的服务进行测试：

```java
package com.sofaboot.quickstart.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sofaboot.quickstart.facade.SampleJvmService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ModuleControllerTests {
    @SofaReference
    private SampleJvmService sampleJvmService;

    @SofaReference(uniqueId = "annotationImpl")
    private SampleJvmService sampleJvmServiceAnnotationImpl;

    @SofaReference(uniqueId = "serviceClientImpl")
    private SampleJvmService sampleJvmServiceClientImpl;

    @Test
    public void test() {
        Assert.assertEquals("Hello, jvm service xml implementation.", sampleJvmService.message());
        Assert.assertEquals("Hello, jvm service annotation implementation.", sampleJvmServiceAnnotationImpl.message());
        Assert.assertEquals("Hello, jvm service service client implementation.", sampleJvmServiceClientImpl.message());
    }
}
```



# 依赖管理

SOFABoot 是在 Spring Boot 的基础上提供的功能扩展。基于 Spring Boot 的机制，SOFABoot 管理了 SOFA 中间件的依赖，并且提供了 Spring Boot 的 Starter，方便用户在 Spring Boot 中使用 SOFA 中间件。



## SOFABoot 依赖管理 – Maven

在使用 SOFA 中间件之前，需要引入 SOFABoot 依赖管理。类似 Spring Boot 引入方式，在工程中增加如下 `<parent/>` 标签配置的方式:

```xml
<parent>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofaboot-dependencies</artifactId>
    <version>${sofa.boot.version}</version>
</parent>
```

其中 `${sofa.boot.version}` 为具体的 SOFABoot 版本，参考 [sofa-boot 发布历史](https://github.com/sofastack/sofa-boot/releases)。



## 引入 SOFA 中间件

SOFABoot 使用一系列后缀为 `-sofa-boot-starter` 来标示一个中间件组件，如果想要使用某个中间件，直接添加对应的依赖即可。例如，如果期望使用 SOFARPC，只需增加下面的 Maven 依赖即可：

```xml
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>rpc-sofa-boot-starter</artifactId>
</dependency>
```

注意上面的 Maven 依赖中并没有声明版本，这个是因为版本已经在 `sofaboot-dependencies` 里面声明好。这样做的好处是对于 SOFA 中间件，用户统一进行升级即可，不需要单独升级一个中间件的版本，防止出现依赖冲突以及兼容性的问题。目前管控的 SOFABoot 中间件列表如下:

| 中间件      | starter                   |
| ----------- | ------------------------- |
| SOFARPC     | rpc-sofa-boot-starter     |
| SOFATracer  | tracer-sofa-boot-starter  |
| SOFALookout | lookout-sofa-boot-starter |



## 引入 SOFABoot 扩展组件

SOFABoot 基于 Spring Boot 提供了健康检查，模块隔离，类隔离等扩展能力。遵循 Spring Boot 依赖即服务的理念，添加相关组件依赖之后，扩展能力即可生效。目前提供的扩展组件如下：

| 扩展组件   | starter                     |
| ---------- | --------------------------- |
| 健康检查   | actuator-sofa-boot-starter  |
| 模块化隔离 | isle-sofa-boot-starter      |
| 类隔离     | sofa-ark-springboot-starter |
| 测试扩展   | test-sofa-boot-starter      |



## 引入 SOFA 中间件 ark 插件

SOFABoot 提供了类隔离组件 [SOFAArk](https://www.sofastack.tech/projects/sofa-boot/sofa-ark-readme)，借助 SOFAArk 容器，用户可以将依赖冲突的三方包打包成 ark 插件。运行时，ark 插件使用单独的类加载器加载，可以和其他 ark 插件以及业务依赖隔离，解决类冲突问题。SOFABoot 官方提供了 SOFARPC 和 SOFATracer 的 ark 插件，例如在应用中引入 SOFARPC ark 插件依赖替代 SOFARPC starter，从而隔离应用和 SOFARPC 及其间接依赖。目前管控的 ark 插件列表如下:

| Ark插件    | plugin                  |
| ---------- | ----------------------- |
| SOFARPC    | rpc-sofa-boot-plugin    |
| SOFATracer | tracer-sofa-boot-plugin |



## 引入 SOFABoot 命名空间

使用 SOFA 中间件时，需要在 `XML` 中根据中间件的具体使用方式添加相应的配置，这个时候需要引入 SOFABoot 的命名空间 `xmlns:sofa="http://sofastack.io/schema/sofaboot"` 以能够正确解析相应的配置标签，示例：

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:sofa="http://sofastack.io/schema/sofaboot"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
            http://sofastack.io/schema/sofaboot   http://sofastack.io/schema/sofaboot.xsd"
       default-autowire="byName">
</beans>
```



# SOFATracer

## 什么是 SOFATracer

SOFATracer 是蚂蚁金服基于 OpenTracing 规范开发的分布式链路跟踪系统，其核心理念就是通过一个全局的 TraceId 将分布在各个服务节点上的同一次请求串联起来。通过统一的 TraceId 将调用链路中的各种网络调用情况以日志的方式记录下来，同时也提供远程汇报到 Zipkin 进行展示的能力，以此达到透视化网络调用的目的。



### 功能描述

**基于 OpenTracing 规范提供分布式链路跟踪解决方案**

基于 [OpenTracing 规范](http://opentracing.io/documentation/pages/spec.html) 并扩展其能力提供链路跟踪的解决方案。各个框架或者组件可以基于此实现，通过在各个组件中埋点的方式来提供链路跟踪的能力。



**提供异步落地磁盘的日志打印能力**

基于 [Disruptor](https://github.com/LMAX-Exchange/disruptor) 高性能无锁循环队列，提供异步打印日志到本地磁盘的能力。框架或者组件能够在接入时，在异步日志打印的前提下可以自定义日志文件的输出格式。SOFATracer 提供两种类似的日志打印类型即摘要日志和统计日志：

- 摘要日志：每一次调用均会落地磁盘的日志。
- 统计日志：每隔一定时间间隔进行统计输出的日志。



**支持日志自清除和滚动能力**

异步落地磁盘的 SOFATracer 日志支持自清除和滚动能力，支持按照天清除和按照小时或者天滚动的能力。



**基于 SLF4J MDC 的扩展能力**

SLF4J 提供了 MDC（Mapped Diagnostic Contexts）功能，可以支持用户定义和修改日志的输出格式以及内容。SOFATracer 集成了 SLF4J MDC 功能，方便用户在只简单修改日志配置文件即可输出当前 Tracer 上下文的 TraceId 和 SpanId。



**界面展示能力**

SOFATracer 可以将链路跟踪数据远程上报到开源产品 [Zipkin](https://zipkin.io/) 做分布式链路跟踪的展示。



**统一配置能力**

配置文件中提供丰富的配置能力以定制化应用的个性需求。



### 应用场景

解决在实施大规模微服务架构时的链路跟踪问题，达到透视化网络调用的目的，并可用于故障的快速发现、服务治理等。



### 组件埋点

目前 SOFATracer 支持 Spring MVC、标准 JDBC 接口实现的数据库连接池（DBCP、Druid、c3p0、tomcat、HikariCP、BoneCP）、HttpClient、Dubbo、Spring Cloud OpenFeign 等开源组件，其他开源组件（如 MQ、Redis）埋点支持在开发中。



## TraceId 和 SpanId 生成规则

SOFATracer 通过 TraceId 来将一个请求在各个服务器上的调用日志串联起来，TraceId 一般由接收请求经过的第一个服务器产生。



### TraceId 生成规则

SOFATracer 通过 TraceId 来将一个请求在各个服务器上的调用日志串联起来，TraceId 一般由接收请求经过的第一个服务器产生。

产生规则是： 服务器 IP + ID 产生的时间 + 自增序列 + 当前进程号 ，比如：

```dns
0ad1348f1403169275002100356696
```

前 8 位 `0ad1348f` 即产生 TraceId 的机器的 IP，这是一个十六进制的数字，每两位代表 IP 中的一段，我们把这个数字，按每两位转成 10 进制即可得到常见的 IP 地址表示方式 `10.209.52.143`，您也可以根据这个规律来查找到请求经过的第一个服务器。

后面的 13 位 `1403169275002` 是产生 TraceId 的时间。之后的 4 位 `1003` 是一个自增的序列，从 1000 涨到 9000，到达 9000 后回到 1000 再开始往上涨。最后的 5 位 `56696` 是当前的进程 ID，为了防止单机多进程出现 TraceId 冲突的情况，所以在 TraceId 末尾添加了当前的进程 ID。

**说明**：TraceId 目前的生成的规则参考了阿里的鹰眼组件。



### SpanId 生成规则

SOFATracer 中的 SpanId 代表本次调用在整个调用链路树中的位置。

假设一个 Web 系统 A 接收了一次用户请求，那么在这个系统的 SOFATracer MVC 日志中，记录下的 SpanId 是 0，代表是整个调用的根节点，如果 A 系统处理这次请求，需要通过 RPC 依次调用 B、C、D 三个系统，那么在 A 系统的 SOFATracer RPC 客户端日志中，SpanId 分别是 0.1，0.2 和 0.3，在 B、C、D 三个系统的 SOFATracer RPC 服务端日志中，SpanId 也分别是 0.1，0.2 和 0.3；如果 C 系统在处理请求的时候又调用了 E，F 两个系统，那么 C 系统中对应的 SOFATracer RPC 客户端日志是 0.2.1 和 0.2.2，E、F 两个系统对应的 SOFATracer RPC 服务端日志也是 0.2.1 和 0.2.2。

根据上面的描述可以知道，如果把一次调用中所有的 SpanId 收集起来，可以组成一棵完整的链路树。

假设一次分布式调用中产生的 TraceId 是 `0a1234`（实际不会这么短），那么根据上文 SpanId 的产生过程。



## Spring MVC 埋点接入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.alipay.sofa</groupId>
        <artifactId>sofaboot-dependencies</artifactId>
        <version>3.11.1</version>
        <relativePath/>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-tracer-mvc</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-tracer-mvc</name>
    <description>sofaboot-quickstart-tracer-mvc</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



### 引入 Tracer 依赖

在 SOFABoot 的 Web 项目中引入如下 Tracer 依赖：

```xml
<!-- import SOFABoot Dependency Tracer starter -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
</dependency>
```

添加 Tracer starter 依赖后，可在 SOFABoot 的全局配置文件中添加配置项目以定制 Tracer 的行为。详情见 [Tracer 配置项说明](https://help.aliyun.com/document_detail/151843.html?spm=a2c4g.280407.0.0.65896f45o7OjVg#h2-tracer-5)。



### 添加 properties

```properties
# Application Name
spring.application.name=sofaboot-quickstart-tracer-mvc
# 日志输出目录，默认输出到 ${user.home}
logging.path=./logs
```



### 添加 Controller

如果您的 Web 工程中没有基于 Spring MVC 框架构建的 Controller，那么可以按照如下方式添加一个 Controller；如果已经有 Controller，那么可直接访问相应的服务。

```java
package com.sofaboot.quickstart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerMvcRestController.java, v 0.1 2024/05/15, ljt Exp $
 */
@RestController
public class TracerMvcRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerMvcRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * http://localhost:8080/greeting
     *
     * @param name name
     * @return map
     */
    @RequestMapping("/greeting")
    public Map<String, Object> greeting(@RequestParam(value = "name", defaultValue = "SOFATracer SpringMVC DEMO") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("id", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        return resultMap;
    }

    /**
     * http://localhost:8080/asyncGreeting
     *
     * @param name name
     * @return map
     */
    @RequestMapping("/asyncGreeting")
    public Map<String, Object> asyncGreeting(@RequestParam(value = "name", defaultValue = "SOFATracer SpringMVC DEMO") String name) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("id", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        Thread.sleep(2000);
        return resultMap;
    }
}
```



### 运行工程

可以将 SOFABoot 工程导入到 IDE 中，工程编译正确后，运行工程里面中的 main 方法启动应用。以上面添加的 Controller 为例，可以通过在浏览器中输入 http://localhost:8080/greeting 来访问 REST 服务，结果类似如下：

```json
{
    "success": true,
    "id": 1,
    "content": "Hello, SOFATracer SpringMVC DEMO!"
}
```



### 查看日志

在 SOFABoot 的配置文件 `application.properties` 中可定义日志打印目录。假设配置的日志打印目录是 `./logs`，即当前应用的根目录，应用名设置为 `spring.application.name=sofaboot-quickstart-tracer-mvc`，那么在当前工程的根目录下可以看到类似如下结构的日志文件：

```
./logs
├── spring.log
└── tracelog
    ├── spring-mvc-digest.log
    ├── spring-mvc-stat.log
    ├── static-info.log
    └── tracer-self.log
```



#### 查看 Spring MVC 摘要日志

`spring-mvc-digest.log` 是 Spring MVC 摘要日志，以 JSON 格式输出。

打开 `spring-mvc-digest.log` 可看到具体的输出内容。下面是一条日志记录的日志样例如下：

```json
{"time":"2024-05-15 00:00:00.000","local.app":"sofaboot-quickstart-tracer-mvc","traceId":"c0a818011715886893986100117568","spanId":"0","span.kind":"server","result.code":"200","current.thread.name":"http-nio-8080-exec-1","time.cost.milliseconds":"40ms","request.url":"http://localhost:8080/greeting","method":"GET","req.size.bytes":-1,"resp.size.bytes":0,"error":"","sys.baggage":"","biz.baggage":""}
```

对应 key 的说明如下：

| key                    | 说明                                                         |
| ---------------------- | ------------------------------------------------------------ |
| time                   | 日志打印时间                                                 |
| local.app              | 当前应用名                                                   |
| traceId                | 请求的 TraceId。详细信息，请参见 [TraceId 生成规则](https://help.aliyun.com/document_detail/151840.html#h2-traceid-1)。 |
| spanId                 | 请求的 SpanId。详细信息，请参见 [SpanId 生成规则](https://help.aliyun.com/document_detail/151840.html#h2-spanid-2)。 |
| span.kind              | Span 类型                                                    |
| result.code            | 结果码                                                       |
| current.thread.name    | 当前线程名                                                   |
| time.cost.milliseconds | Span 耗时，单位：ms。                                        |
| request.url            | 请求 URL                                                     |
| method                 | 调用方法                                                     |
| req.size.bytes         | 请求数据大小，单位：Byte。                                   |
| resp.size.bytes        | 响应数据大小，单位：Byte。                                   |
| sys.baggage            | 系统透传的 baggage 数据                                      |
| biz.baggage            | 业务透传的 baggage 数据                                      |



#### 查看 Spring MVC 统计日志

`spring-mvc-stat.log` 是 Spring MVC 统计日志。其中，`stat.key` 为本段时间内的统计关键字集合，统计关键字集合唯一确定一组统计数据，包含 `method`、`local.app` 和 `request.url` 字段。日志样例如下：

```json
{"time":"2024-05-15 00:00:00.000","stat.key":{"method":"GET","local.app":"sofaboot-quickstart-tracer-mvc","request.url":"http://localhost:8080/greeting"},"count":1,"total.cost.milliseconds":82,"success":"Y","load.test":"F"}
```

对应 key 的说明如下：

| key                     | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| time                    | 日志打印时间                                                 |
| stat.key.method         | 调用方法                                                     |
| stat.key.local.app      | 当前应用名                                                   |
| stat.key.request.url    | 请求 URL                                                     |
| count                   | 请求次数                                                     |
| total.cost.milliseconds | 请求总耗时，单位：ms。                                       |
| success                 | 请求结果：true：表示请求成功。1XX、2XX、301、302 表示请求成功，其他状态码表示请求失败。false：表示请求失败。 |
| load.test               | 判断当前是否为全链路压测：T：表示当前为全链路压测。当前线程中能获取到日志上下文，且上下文中有压测信息。F：表示当前非全链路压测。当前线程中不能获取到日志上下文，或上下文中没有压测信息。 |



### Tracer 配置项说明

| SOFATracer 配置项                                         | 说明                                                         | 默认值                                                       |
| :-------------------------------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `logging.path`                                            | 日志输出目录                                                 | SOFATracer 会优先输出到 `logging.path` 目录下；如果没有配置日志输出目录，那默认输出到 `${user.home}` |
| `com.alipay.sofa.tracer.disableDigestLog`                 | 是否关闭所有集成 SOFATracer 组件摘要日志打印                 | false                                                        |
| `com.alipay.sofa.tracer.disableConfiguration[${logType}]` | 关闭指定 `${logType}` 的 SOFATracer 组件摘要日志打印。`${logType}` 是指具体的日志类型，如：`spring-mvc-digest.log` | false                                                        |
| `com.alipay.sofa.tracer.tracerGlobalRollingPolicy`        | SOFATracer 日志的滚动策略                                    | `.yyyy-MM-dd`：按照天滚动；`.yyyy-MM-dd_HH`：按照小时滚动。默认不配置按照天滚动。 |
| `com.alipay.sofa.tracer.tracerGlobalLogReserveDay`        | SOFATracer 日志的保留天数                                    | 默认保留 7 天                                                |
| `com.alipay.sofa.tracer.statLogInterval`                  | 统计日志的时间间隔，单位：秒                                 | 默认 60 秒统计日志输出一次                                   |
| `com.alipay.sofa.tracer.baggageMaxLength`                 | 透传数据能够允许存放的最大长度                               | 默认值 1024                                                  |
| `com.alipay.sofa.tracer.springmvc.filterOrder`            | SOFATracer 集成在 Spring MVC 的 Filter 生效的 Order          | -2147483647（org.springframework.core.Ordered#HIGHEST_PRECEDENCE + 1） |
| `com.alipay.sofa.tracer.springmvc.urlPatterns`            | SOFATracer 集成在 SpringMVC 的 Filter 生效的 URL Pattern 路径 | /* 全部生效                                                  |



## HttpClient 埋点接入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.alipay.sofa</groupId>
        <artifactId>sofaboot-dependencies</artifactId>
        <version>3.11.1</version>
        <relativePath/>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-tracer-httpclient</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-tracer-httpclient</name>
    <description>sofaboot-quickstart-tracer-httpclient</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



### 引入 Tracer 依赖

1. 在 SOFABoot 的 Web 项目中引入如下 Tracer 依赖：

```xml
<!-- import SOFABoot Dependency Tracer starter -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
</dependency>
```

添加 Tracer starter 依赖后，可在 SOFABoot 的全局配置文件中添加配置项目以定制 Tracer 的行为。详情见 [Tracer 配置项说明](https://help.aliyun.com/document_detail/151843.html?spm=a2c4g.280407.0.0.65896f45o7OjVg#h2-tracer-5)。



2. 基于 SOFATracer 的 HttpClient 插件

为了使得 HttpClient 这个第三方开源组件能够支持 SOFATracer 的链路调用，SOFATracer 提供了 HttpClient 的插件扩展，即 `sofa-tracer-httpclient-plugin`：

```xml
<!-- 基于 SOFATracer 的 HttpClient 插件 -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofa-tracer-httpclient-plugin</artifactId>
</dependency>
```



3. HttpClient 依赖

```xml
<!-- HttpClient 依赖 -->
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
    <!-- 版本 4.3.x - 4.5.x -->
    <version>4.5.3</version>
</dependency>
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpasyncclient</artifactId>
    <!-- 版本 4.1.x -->
    <version>4.1.3</version>
</dependency>
```



### 添加 properties

```properties
# Application Name
spring.application.name=sofaboot-quickstart-tracer-httpclient
# 日志输出目录，默认输出到 ${user.home}
logging.path=./logs

# 参考文档：https://help.aliyun.com/document_detail/151854.html
# 采样率 (0~100)%
com.alipay.sofa.tracer.samplerPercentage=100
# 采样模式类型名称
#com.alipay.sofa.tracer.samplerName=PercentageBasedSampler

# 统计日志的时间间隔，默认 60，这里为了方便快速看统计设置成 1
com.alipay.sofa.tracer.statLogInterval=1
com.alipay.sofa.tracer.zipkin.enabled=false

# 是否以 JSON 格式输出日志，使用非 JSON 格式输出，期望较少日志空间占用
#com.alipay.sofa.tracer.JSONOutput=false
```



### 构造 HttpClient 并发起对 RESTful 服务的调用

为了使得工程中使用 SOFATracer 的 HttpClient 能够正确埋点和打印日志，请根据 SOFA Boot 版本选择实现方法：

- sofaboot-enterprise 3.1.0（即 tracer-parent 3.0.2 ）及之前的版本，使用`com.alipay.sofa.tracer.enterprise.plugins.SofaTracerEnterpriseHttpClientBuilder` 类去构造 HttpClient 的实例，并显式调用 `clientBuilder` 方法。
- sofaboot-enterprise 3.1.1（即 tracer-parent 3.0.3 ）及之后的版本，使用`com.alipay.sofa.tracer.plugins.httpclient.SofaTracerHttpClientBuilder` 类去构造 HttpClient 的实例，并显式调用 `clientBuilder` 方法。

`SofaTracerEnterpriseHttpClientBuilder` 类提供了 `clientBuilder`（同步）和 `asyncClientBuilder`（异步）方法，以构造出一个经过 SOFATracer 埋点的`org.apache.http.impl.client.HttpClientBuilder` 实例。方法签名如下：

```java
// 同步调用构造方法
public static HttpClientBuilder clientBuilder(HttpClientBuilder clientBuilder);

// 同步调用构造方法，并在日志中显示当前应用和目标应用的字段
public static HttpClientBuilder clientBuilder(HttpClientBuilder clientBuilder,
String currentApp, String targetApp);

// 异步调用构造方法
public static HttpAsyncClientBuilder asyncClientBuilder(HttpAsyncClientBuilder httpAsyncClientBuilder);

// 异步调用构造方法，并在日志中显示当前应用和目标应用的字段
public static HttpAsyncClientBuilder asyncClientBuilder(HttpAsyncClientBuilder httpAsyncClientBuilder,
String currentApp, String targetApp);
```



构造 HttpClient 同步调用代码块示例：

```java
HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
// SOFATracer
// SofaTracerHttpClientBuilder.clientBuilder(httpClientBuilder);
SofaTracerHttpClientBuilder.clientBuilder(httpClientBuilder, "testSyncClient", "testSyncServer");
CloseableHttpClient httpClient=httpClientBuilder.setConnectionManager(connManager).disableAutomaticRetries()
        .build();
```



构造 HttpClient 异步调用代码块示例：

```java
RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).setConnectionRequestTimeout(6000).build();
HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClientBuilder.create();
// SOFATracer
// SofaTracerHttpClientBuilder.asyncClientBuilder(httpAsyncClientBuilder);
SofaTracerHttpClientBuilder.asyncClientBuilder(httpAsyncClientBuilder, "testAsyncClient", "testAsyncServer");
CloseableHttpAsyncClient asyncHttpclient = httpAsyncClientBuilder.setDefaultRequestConfig(requestConfig).build();
```



通过 `SofaTracerEnterpriseHttpClientBuilder` 构造的 HttpClient 实例在发起对上文的 RESTful 服务调用的时候，就会埋点 SOFATracer 的链路数据。



### 添加 Controller

如果您的 Web 工程中没有基于 Spring MVC 框架构建的 Controller，那么可以按照如下方式添加一个 Controller；如果已经有 Controller，那么可直接访问相应的服务。

```java
package com.sofaboot.quickstart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerHttpclientRestController.java, v 0.1 2024/05/20, ljt Exp $
 */
@RestController
public class TracerHttpclientRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerHttpclientRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/httpclient?name=
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/httpclient")
    public Map<String, Object> httpclient(@RequestParam(value = "name", defaultValue = "httpclient") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        return resultMap;
    }
}
```



### 运行工程

可以将 SOFABoot 工程导入到 IDE 中，工程编译正确后，运行工程里面中的 main 方法启动应用。以上面添加的 Controller 为例，可以

通过在浏览器中输入 http://localhost:8080/sync 来访问 REST 服务，结果类似如下：

```json
{"success":true,"count":1,"content":"Hello, httpclient!"}
```



通过在浏览器中输入 http://localhost:8080/async 来访问 REST 服务，结果类似如下：

```json
{"success":true,"count":2,"content":"Hello, httpclient!"}
```



在 SOFABoot 的配置文件 `application.properties` 中可定义日志打印目录。假设配置的日志打印目录是 `./logs`，即当前应用的根目录，应用名设置为 `spring.application.name=sofaboot-quickstart-tracer-httpclient`，那么在当前工程的根目录下可以看到类似如下结构的日志文件：

```
./logs
├── spring.log
└── tracelog
    ├── httpclient-digest.log
    ├── httpclient-stat.log
    ├── spring-mvc-digest.log
    ├── spring-mvc-stat.log
    ├── static-info.log
    └── tracer-self.log
```



#### 查看 HttpClient 摘要日志

以 HttpClient 同步调用和异步调用为例，摘要日志 `httpclient-digest.log` 如下：

```json
{"time":"2024-05-20 00:00:00.000","local.app":"testSyncClient","traceId":"c0a818011716227269771100121584","spanId":"0.1","span.kind":"client","result.code":"200","current.thread.name":"http-nio-8080-exec-1","time.cost.milliseconds":"149ms","request.url":"http://localhost:8080/httpclient","method":"GET","req.size.bytes":0,"resp.size.bytes":-1,"remote.app":"testSyncServer","sys.baggage":"","biz.baggage":""}
{"time":"2024-05-20 00:00:00.000","local.app":"testAsyncClient","traceId":"c0a818011716227273488100221584","spanId":"0.1","span.kind":"client","result.code":"200","current.thread.name":"I/O dispatcher 1","time.cost.milliseconds":"23ms","request.url":"http://localhost:8080/httpclient","method":"GET","req.size.bytes":0,"resp.size.bytes":-1,"remote.app":"testAsyncServer","sys.baggage":"","biz.baggage":""}
```

对应 key 的说明如下：

| key                    | 说明                    |
| ---------------------- | ----------------------- |
| time                   | 日志打印时间            |
| local.app              | 当前应用名              |
| traceId                | TraceId                 |
| spanId                 | SpanId                  |
| span.kind              | Span 类型               |
| result.code            | 结果码                  |
| current.thread.name    | 当前线程名称            |
| time.cost.milliseconds | Span 耗时               |
| request.url            | 请求 URL                |
| method                 | 调用方法                |
| req.size.bytes         | 请求数据大小            |
| resp.size.bytes        | 响应数据大小            |
| remote.app             | 目标应用名称            |
| sys.baggage            | 系统透传的 baggage 数据 |
| biz.baggage            | 业务透传的 baggage 数据 |



#### 查看 HttpClient 统计日志

以 HttpClient 同步调用和异步调用为例，统计日志 `httpclient-stat.log` 如下：

```json
{"time":"2024-05-20 00:00:00.000","stat.key":{"method":"GET","local.app":"testSyncClient","request.url":"http://localhost:8080/httpclient"},"count":1,"total.cost.milliseconds":149,"success":"Y","load.test":"F"}
{"time":"2024-05-20 00:00:00.000","stat.key":{"method":"GET","local.app":"testAsyncClient","request.url":"http://localhost:8080/httpclient"},"count":1,"total.cost.milliseconds":23,"success":"Y","load.test":"F"}
```

对应 key 的说明如下：

| key                     | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| time                    | 日志打印时间                                                 |
| stat.key.method         | 调用方法                                                     |
| stat.key.local.app      | 当前应用名                                                   |
| stat.key.request.url    | 请求 URL                                                     |
| count                   | 请求次数                                                     |
| total.cost.milliseconds | 请求总耗时                                                   |
| success                 | 请求结果：true：表示请求成功。false：表示请求失败。          |
| load.test               | 判断当前是否为全链路压测：T：表示当前为全链路压测。当前线程中能获取到日志上下文，且上下文中有压测信息。F：表示当前非全链路压测。当前线程中不能获取到日志上下文，或上下文中没有压测信息。 |



## DataSource 埋点接入

SOFATracer 2.2.0 基于标准的 JDBC 接口实现，支持对标准的数据库连接池（如 DBCP、Druid、c3p0、tomcat、HikariCP、BoneCP）埋点。本文档将介绍如何使用 SOFATracer 对 DataSource 进行埋点。

- 已升级 SOFABoot 至 3.4.11 及以上版本。
- 已基于 SOFABoot 构建了一个 Spring Web 工程。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.alipay.sofa</groupId>
        <artifactId>sofaboot-dependencies</artifactId>
        <version>3.11.1</version>
        <relativePath/>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-tracer-datasource</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-tracer-datasource</name>
    <description>sofaboot-quickstart-tracer-datasource</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



### 引入 Tracer 依赖

3.11.1 sofaboot-dependencies 的 sofa-tracer-datasource-plugin 生成的 统计日志 datasource-client-stat 的 resultCode 不兼容，所以用高版本的 sofa-tracer-datasource-plugin。

```xml
<!-- import SOFABoot Dependency Tracer starter -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>com.alipay.sofa</groupId>
            <artifactId>sofa-tracer-datasource-plugin</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 基于 SOFATracer 的 Datasource 插件 -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofa-tracer-datasource-plugin</artifactId>
    <version>3.1.3</version>
</dependency>
```



### **引入 数据库依赖 和 连接池依赖**

H2 数据库 或者 Mysql 驱动包 二选一

Druid 数据库连接池

```xml
<!-- Druid 数据库连接池 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.20</version>
</dependency>
<!-- H2 数据库 -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- Spring Boot Starter for JDBC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<!-- Mysql 驱动包 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```



### 添加 properties

```properties
# Application Name
spring.application.name=sofaboot-quickstart-tracer-datasource
# 日志输出目录，默认输出到 ${user.home}
logging.path=./logs

server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
server.servlet.encoding.force=true

# h2 web consloe 路径
spring.h2.console.path=/h2-console
# 开启 h2 web consloe，默认为 false
spring.h2.console.enabled=true
# 允许远程访问 h2 web consloe
spring.h2.console.settings.web-allow-others=true

# h2 数据源配置
spring.datasource.url=jdbc:h2:~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa123456

# mysql 数据源配置
# allowMultiQueries=true 来启用多语句支持，注意会增加SQL注入的风险
#spring.datasource.url=jdbc:mysql://localhost:3306/test?allowMultiQueries=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
#spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.datasource.username=root
#spring.datasource.password=123456

# Druid 配置
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.initial-size=5
spring.datasource.druid.max-active=20
spring.datasource.druid.min-idle=5
spring.datasource.druid.max-wait=60000
spring.datasource.druid.time-between-eviction-runs-millis=60000
spring.datasource.druid.min-evictable-idle-time-millis=300000
spring.datasource.druid.validation-query=SELECT 1 FROM DUAL
spring.datasource.druid.test-while-idle=true
spring.datasource.druid.test-on-borrow=false
spring.datasource.druid.test-on-return=false
spring.datasource.druid.pool-prepared-statements=true
spring.datasource.druid.max-pool-prepared-statement-per-connection-size=20
# Druid 提供了多种内置 Filter，如用于 SQL 监控的 StatFilter、用于防御 SQL 注入攻击的 WallFilter
#spring.datasource.druid.filters=stat,wall,slf4j
spring.datasource.druid.filters=stat,slf4j
spring.datasource.druid.connection-properties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
spring.datasource.druid.web-stat-filter.enabled=true
spring.datasource.druid.stat-view-servlet.enabled=true
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.stat-view-servlet.allow=127.0.0.1
spring.datasource.druid.filter.stat.enabled=true
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=1000
spring.datasource.druid.filter.stat.merge-sql=true
spring.datasource.druid.filter.wall.config.multi-statement-allow=true
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=admin123456

# 参考文档：https://help.aliyun.com/document_detail/151854.html
# 采样率 (0~100)%
com.alipay.sofa.tracer.samplerPercentage=100
# 采样模式类型名称
#com.alipay.sofa.tracer.samplerName=PercentageBasedSampler

# 统计日志的时间间隔，默认 60，这里为了方便快速看统计设置成 1
com.alipay.sofa.tracer.statLogInterval=1
com.alipay.sofa.tracer.zipkin.enabled=false

# 是否以 JSON 格式输出日志，使用非 JSON 格式输出，期望较少日志空间占用
#com.alipay.sofa.tracer.JSONOutput=false

com.alipay.sofa.tracer.datasource.enable=true
```



### 添加 Controller

新建一个 REST 服务，触发 SQL 语句执行，便于查看 SQL 的 Tracer 记录。在以下 REST 服务创建中，触发了一个建表操作。

```java
package com.sofaboot.quickstart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ljt
 * @version: $Id: TracerDatasourceRestController.java, v 0.1 2024/05/22, ljt Exp $
 */
@RestController
public class TracerDatasourceRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerDatasourceRestController.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate template;

    /**
     * Request http://localhost:8080/create
     *
     * @return Map of Result
     */
    @RequestMapping("/create")
    public Map<String, Object> create() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        try (Connection cn = dataSource.getConnection();
             Statement st = cn.createStatement()) {

            final String sql = "DROP TABLE IF EXISTS TEST;"
                    + "CREATE TABLE TEST("
                    + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                    + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                    + ");"
                    + "INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (1, '张三', CURRENT_TIMESTAMP);"
                    + "INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (2, '李四', CURRENT_TIMESTAMP);"
                    + "INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (3, '王五', CURRENT_TIMESTAMP);";
            st.execute(sql);

            resultMap.put("success", true);
            resultMap.put("result", sql);
        } catch (Throwable throwable) {
            resultMap.put("success", false);
            resultMap.put("error", throwable.getMessage());
        }
        return resultMap;
    }

    /**
     * Request http://localhost:8080/createStep
     *
     * @return Map of Result
     */
    @RequestMapping("/createStep")
    public Map<String, Object> createStep() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 假设您有一个已经设置好的 Connection 对象叫 conn
        try {
            Connection conn = dataSource.getConnection();

            // 1. 先执行 DROP TABLE IF EXISTS
            String dropTableSql = "DROP TABLE IF EXISTS TEST;";
            Statement stmt = conn.createStatement();
            stmt.execute(dropTableSql);
            stmt.close(); // 关闭 Statement

            // 2. 再执行 CREATE TABLE
            String createTableSql = "CREATE TABLE TEST("
                    + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                    + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                    + ");";
            stmt = conn.createStatement();
            stmt.execute(createTableSql);
            stmt.close(); // 关闭 Statement

            // 3. 接着执行 INSERT 语句（可以循环或分别执行）
            String insertSqlTemplate = "INSERT INTO TEST (USER_NAME, CREATE_TIME) VALUES (?, CURRENT_TIMESTAMP);";
            PreparedStatement pstmt = conn.prepareStatement(insertSqlTemplate);

            // 插入张三
            pstmt.setString(1, "张三");
            pstmt.executeUpdate();

            // 插入李四
            pstmt.setString(1, "李四");
            pstmt.executeUpdate();

            // 插入王五
            pstmt.setString(1, "王五");
            pstmt.executeUpdate();

            pstmt.close(); // 关闭 PreparedStatement

            resultMap.put("success", true);
            resultMap.put("result", dropTableSql + createTableSql + insertSqlTemplate);

        } catch (Throwable throwable) {
            resultMap.put("success", false);
            resultMap.put("error", throwable.getMessage());
        }

        return resultMap;
    }

    /**
     * Request http://localhost:8080/execute
     *
     * @return Map of Result
     */
    @RequestMapping("/execute")
    public Map<String, Object> execute() {
        Map<String, Object> resultMap = new HashMap<>();

        final String sql = "DROP TABLE IF EXISTS TEST;"
                + "CREATE TABLE TEST("
                + "USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                + "USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',"
                + "CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime'"
                + ");"
                + "INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (1, '张三', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (2, '李四', CURRENT_TIMESTAMP);"
                + "INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (3, '王五', CURRENT_TIMESTAMP);";
        template.execute(sql);

        resultMap.put("success", true);
        resultMap.put("result", sql);

        return resultMap;
    }

    /**
     * Request http://localhost:8080/selectOne/1
     *
     * @return Map of Result
     */
    @RequestMapping("/selectOne/{userId}")
    public Map<String, Object> selectOne(@PathVariable(value = "userId") int userId) {
        Map<String, Object> resultMap = new HashMap<>();

        final String sql = "SELECT * FROM TEST WHERE USER_ID = ?";
        Map<String, Object> item = template.queryForObject(sql, new Object[]{userId}, (result, rowNum) ->
                new HashMap<String, Object>() {{
                    put("userId", result.getInt("USER_ID"));
                    put("userName", result.getString("USER_NAME"));
                    put("createTime", result.getDate("CREATE_TIME"));
                }}
        );

        resultMap.put("success", true);
        resultMap.put("result", item);

        return resultMap;
    }

    /**
     * Request http://localhost:8080/selectAll
     *
     * @return Map of Result
     */
    @RequestMapping("/selectAll")
    public Map<String, Object> selectAll() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        final String sql = "SELECT * FROM TEST;";
        List<Map> items = template.query(sql, (result, rowNum) ->
                new HashMap<String, Object>() {{
                    put("userId", result.getInt("USER_ID"));
                    put("userName", result.getString("USER_NAME"));
                    put("createTime", result.getDate("CREATE_TIME"));
                }}
        );

        resultMap.put("success", true);
        resultMap.put("result", items);

        return resultMap;
    }
}
```



### 运行工程

可以将 SOFABoot 工程导入到 IDE 中，工程编译正确后，运行工程里面中的 main 方法启动应用。以上面添加的 Controller 为例，可以

通过在浏览器中输入 http://localhost:8080/create 来访问 REST 服务，结果类似如下：

```json
{
    "result": "DROP TABLE IF EXISTS TEST;CREATE TABLE TEST(USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,USER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName',CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime');INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (1, '张三', CURRENT_TIMESTAMP);INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (2, '李四', CURRENT_TIMESTAMP);INSERT INTO TEST (USER_ID, USER_NAME, CREATE_TIME) VALUES (3, '王五', CURRENT_TIMESTAMP);",
    "success": true
}
```

在 SOFABoot 的配置文件 `application.properties` 中可定义日志打印目录。假设配置的日志打印目录是 `./logs`，即当前应用的根目录，应用名设置为 `spring.application.name=sofaboot-quickstart-tracer-datasource`，那么在当前工程的根目录下可以看到类似如下结构的日志文件：

```
./logs
├── spring.log
└── tracelog
    ├── datasource-client-digest.log
    ├── datasource-client-stat.log
    ├── spring-mvc-digest.log
    ├── spring-mvc-stat.log
    ├── static-info.log
    └── tracer-self.log
```

可以在 `./logs/datasource-client-digest.log` 和 `./logs/datasource-client-stat.log` 看到 SQL 执行的 Tracer 日志。



#### 查看 DataSource 摘要日志

以 DataSource 同步调用为例，摘要日志 `datasource-client-digest` 如下：

```json
{"time":"2024-05-24 00:00:00.000","local.app":"sofaboot-quickstart-tracer-datasource","traceId":"c0a818011716653054033100122820","spanId":"0.1","span.kind":"client","result.code":"success","current.thread.name":"http-nio-8080-exec-2","time.cost.milliseconds":"37ms","database.name":"test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE","sql":"DROP TABLE IF EXISTS TEST;CREATE TABLE TEST(USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY%2CUSER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName'%2CCREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime');INSERT INTO TEST (USER_ID%2C USER_NAME%2C CREATE_TIME) VALUES (1%2C '张三'%2C CURRENT_TIMESTAMP);INSERT INTO TEST (USER_ID%2C USER_NAME%2C CREATE_TIME) VALUES (2%2C '李四'%2C CURRENT_TIMESTAMP);INSERT INTO TEST (USER_ID%2C USER_NAME%2C CREATE_TIME) VALUES (3%2C '王五'%2C CURRENT_TIMESTAMP);","connection.establish.span":"0ms","db.execute.cost":"37ms","database.type":"h2","database.endpoint":"jdbc:h2:~/test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE:-1","sys.baggage":"","biz.baggage":""}
```

对应 key 的说明如下：

| key                       | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| time                      | 日志打印时间                                                 |
| local.app                 | 当前应用名                                                   |
| traceId                   | TraceId                                                      |
| spanId                    | SpanId                                                       |
| span.kind                 | Span 类型                                                    |
| result.code               | 结果码，取值如下：00：请求成功。03：请求超时。99：请求失败。 |
| current.thread.name       | 当前线程名                                                   |
| time.cost.milliseconds    | Span 耗时                                                    |
| database.name             | 数据库名称                                                   |
| SQL                       | SQL 执行语句                                                 |
| connection.establish.span | SQL 执行建连时间                                             |
| db.execute.cost           | SQL 执行时间                                                 |
| database.type             | 数据库类型                                                   |
| database.endpoint         | 数据库 URL                                                   |
| sys.baggage               | 系统透传的 baggage 数据，以 KV（key-value）格式展示。        |
| biz.baggage               | 业务透传的 baggage 数据，以 KV 格式展示。                    |



#### 查看 DataSource 统计日志

以 DataSource 同步调用为例，统计日志 `datasource-client-stat.log` 如下：

```json
{"time":"2024-05-24 00:00:00.000","stat.key":{"local.app":"sofaboot-quickstart-tracer-datasource","database.name":"test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE","sql":"DROP TABLE IF EXISTS TEST;CREATE TABLE TEST(USER_ID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY%2CUSER_NAME VARCHAR(255) DEFAULT '' COMMENT 'userName'%2CCREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime');INSERT INTO TEST (USER_ID%2C USER_NAME%2C CREATE_TIME) VALUES (1%2C '张三'%2C CURRENT_TIMESTAMP);INSERT INTO TEST (USER_ID%2C USER_NAME%2C CREATE_TIME) VALUES (2%2C '李四'%2C CURRENT_TIMESTAMP);INSERT INTO TEST (USER_ID%2C USER_NAME%2C CREATE_TIME) VALUES (3%2C '王五'%2C CURRENT_TIMESTAMP);"},"count":1,"total.cost.milliseconds":37,"success":"Y","load.test":"F"}
```

对应 key 的说明如下：

| key                     | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| time                    | 日志打印时间                                                 |
| stat.key.local.app      | 当前应用名                                                   |
| stat.key.database.name  | 数据库名称                                                   |
| stat.key.sql            | SQL 执行语句                                                 |
| count                   | SQL 执行次数                                                 |
| total.cost.milliseconds | SQL 执行总耗时。单位：ms。                                   |
| success                 | 请求结果：true：表示请求成功。false：表示请求失败。          |
| load.test               | 判断当前是否为全链路压测：T：表示当前为全链路压测。当前线程中能获取到日志上下文，且上下文中有压测信息。F：表示当前非全链路压测。当前线程中不能获取到日志上下文，或上下文中没有压测信息。 |



## RestTemplate 埋点接入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.alipay.sofa</groupId>
        <artifactId>sofaboot-dependencies</artifactId>
        <version>3.11.1</version>
        <relativePath/>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-tracer-resttemplate</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-tracer-resttemplate</name>
    <description>sofaboot-quickstart-tracer-resttemplate</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



### 引入 Tracer 依赖

1. 在 SOFABoot 的 Web 项目中引入如下 Tracer 依赖：

```xml
<!-- import SOFABoot Dependency Tracer starter -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
</dependency>
```

添加 Tracer starter 依赖后，可在 SOFABoot 的全局配置文件中添加配置项目以定制 Tracer 的行为。详情见 [Tracer 配置项说明](https://help.aliyun.com/document_detail/151843.html?spm=a2c4g.280407.0.0.65896f45o7OjVg#h2-tracer-5)。



2. 基于 SOFATracer 的 RestTemplate 插件

为了使得 RestTemplate 这个第三方开源组件能够支持 SOFATracer 的链路调用，SOFATracer 提供了 RestTemplate 的插件扩展，即 `sofa-tracer-resttmplate-plugin`：

```xml
<!-- 基于 SOFATracer 的 RestTemplate 插件 -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofa-tracer-resttmplate-plugin</artifactId>
</dependency>
```



### 添加 properties

```properties
# Application Name
spring.application.name=sofaboot-quickstart-tracer-resttemplate
# 日志输出目录，默认输出到 ${user.home}
logging.path=./logs

# 参考文档：https://help.aliyun.com/document_detail/151854.html
# 采样率 (0~100)%
com.alipay.sofa.tracer.samplerPercentage=100
# 采样模式类型名称
#com.alipay.sofa.tracer.samplerName=PercentageBasedSampler

# 统计日志的时间间隔，默认 60，这里为了方便快速看统计设置成 1
com.alipay.sofa.tracer.statLogInterval=1
com.alipay.sofa.tracer.zipkin.enabled=false

# 是否以 JSON 格式输出日志，使用非 JSON 格式输出，期望较少日志空间占用
#com.alipay.sofa.tracer.JSONOutput=false
```



### 添加 Controller

如果您的 Web 工程中没有基于 Spring MVC 框架构建的 Controller，那么可以按照如下方式添加一个 Controller；如果已经有 Controller，那么可直接访问相应的服务。

```java
package com.sofaboot.quickstart.controller;

import com.sofa.alipay.tracer.plugins.rest.SofaTracerRestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerResttemplateRestController.java, v 0.1 2024/05/27, ljt Exp $
 */
@RestController
public class TracerResttemplateRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerResttemplateRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/syncRest?name=syncRest
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/syncRest")
    public Map<String, Object> syncRest(@RequestParam(value = "name", defaultValue = "syncRest") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

        logger.info("syncRest execute finish ...");

        return resultMap;
    }

    /**
     * Request http://localhost:8080/asyncRest?name=asyncRest
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/asyncRest")
    public Map<String, Object> asyncRest(@RequestParam(value = "name", defaultValue = "asyncRest") String name) throws InterruptedException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

        Thread.sleep(2000);
        logger.info("asyncRest execute finish ...");

        return resultMap;
    }

    /**
     * Request http://localhost:8080/sync
     *
     * @return Map of Result
     */
    @RequestMapping("/sync")
    public ResponseEntity sync() {
        String httpGetUrl = "http://localhost:8080/syncRest";
        // sync
        RestTemplate restTemplate = SofaTracerRestTemplateBuilder.buildRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(httpGetUrl, String.class);
        logger.info("Response is {}", responseEntity);

        return responseEntity;
    }

    /**
     * Request http://localhost:8080/async
     *
     * @return Map of Result
     */
    @RequestMapping("/async")
    public ResponseEntity async() {
        String httpGetUrl = "http://localhost:8080/asyncRest";
        // async
        RestTemplate restTemplate = SofaTracerRestTemplateBuilder.buildRestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(httpGetUrl, String.class);
        logger.info("Response is {}", responseEntity);

        return responseEntity;
    }
}
```



### 运行工程

可以将 SOFABoot 工程导入到 IDE 中，工程编译正确后，运行工程里面中的 main 方法启动应用。以上面添加的 Controller 为例，可以

通过在浏览器中输入 http://localhost:8080/sync 来访问 REST 服务，结果类似如下：

```json
{ "success": true, "count": 1, "content": "Hello, syncRest!" }
```



通过在浏览器中输入 http://localhost:8080/async 来访问 REST 服务，结果类似如下：

```json
{ "success": true, "count": 2, "content": "Hello, asyncRest!" }
```



调用成功的控制台输出日志如下：

```
2024-05-27 00:00:00.000  INFO 28924 --- [nio-8080-exec-3] c.s.q.c.TracerResttemplateRestController : syncRest execute finish ...
2024-05-27 00:00:00.000  INFO 28924 --- [nio-8080-exec-2] c.s.q.c.TracerResttemplateRestController : Response is <200,{"success":true,"count":1,"content":"Hello, syncRest!"},[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Sun, 26 May 2024 18:44:41 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]>
2024-05-27 00:00:00.000  INFO 28924 --- [nio-8080-exec-5] c.s.q.c.TracerResttemplateRestController : asyncRest execute finish ...
2024-05-27 00:00:00.000  INFO 28924 --- [nio-8080-exec-4] c.s.q.c.TracerResttemplateRestController : Response is <200,{"success":true,"count":2,"content":"Hello, asyncRest!"},[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Sun, 26 May 2024 18:44:54 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]>
```



在 SOFABoot 的配置文件 `application.properties` 中可定义日志打印目录。假设配置的日志打印目录是 `./logs`，即当前应用的根目录，应用名设置为 `spring.application.name=sofaboot-quickstart-tracer-resttemplate`，那么在当前工程的根目录下可以看到类似如下结构的日志文件：

```
./logs
├── spring.log
└── tracelog
    ├── resttemplate-digest.log
    ├── resttemplate-stat.log
    ├── spring-mvc-digest.log
    ├── spring-mvc-stat.log
    ├── static-info.log
    └── tracer-self.log
```



#### 查看 RestTemplate 摘要日志

以 RestTemplate 同步调用和异步调用为例，摘要日志 `resttemplate-digest.log` 如下：

```json
{"time":"2024-05-27 00:00:00.000","local.app":"sofaboot-quickstart-tracer-resttemplate","traceId":"c0a818011716749081129100128924","spanId":"0.1","span.kind":"client","result.code":"200","current.thread.name":"http-nio-8080-exec-2","time.cost.milliseconds":"59ms","request.url":"http://localhost:8080/syncRest","method":"GET","resp.size.bytes":0,"resp.size.bytes":0,"error":"","sys.baggage":"","biz.baggage":""}
{"time":"2024-05-27 00:00:00.000","local.app":"sofaboot-quickstart-tracer-resttemplate","traceId":"c0a818011716749092476100228924","spanId":"0.1","span.kind":"client","result.code":"200","current.thread.name":"http-nio-8080-exec-4","time.cost.milliseconds":"2016ms","request.url":"http://localhost:8080/asyncRest","method":"GET","resp.size.bytes":0,"resp.size.bytes":0,"error":"","sys.baggage":"","biz.baggage":""}
```

对应 key 的说明如下：

| key                    | 说明                    |
| ---------------------- | ----------------------- |
| time                   | 日志打印时间            |
| local.app              | 当前应用名              |
| traceId                | TraceId                 |
| spanId                 | SpanId                  |
| span.kind              | Span 类型               |
| result.code            | 结果码                  |
| current.thread.name    | 当前线程名              |
| time.cost.milliseconds | Span 耗时               |
| request.url            | 请求 URL                |
| method                 | 调用方法                |
| req.size.bytes         | 请求数据大小            |
| resp.size.bytes        | 响应数据大小            |
| sys.baggage            | 系统透传的 baggage 数据 |
| biz.baggage            | 业务透传的 baggage 数据 |



#### 查看 RestTemplate 统计日志

以 RestTemplate 同步调用和异步调用为例，统计日志 `resttemplate-stat.log` 如下：

```json
{"time":"2024-05-27 00:00:00.000","stat.key":{"method":"GET","local.app":"sofaboot-quickstart-tracer-resttemplate","request.url":"http://localhost:8080/syncRest"},"count":1,"total.cost.milliseconds":59,"success":"Y","load.test":"F"}
{"time":"2024-05-27 00:00:00.000","stat.key":{"method":"GET","local.app":"sofaboot-quickstart-tracer-resttemplate","request.url":"http://localhost:8080/asyncRest"},"count":1,"total.cost.milliseconds":2016,"success":"Y","load.test":"F"}
```

对应 key 的说明如下：

| key                     | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| time                    | 日志打印时间                                                 |
| stat.key.method         | 调用方法                                                     |
| stat.key.local.app      | 当前应用名                                                   |
| stat.key.request.url    | 请求 URL                                                     |
| count                   | 请求次数                                                     |
| total.cost.milliseconds | 请求总耗时                                                   |
| success                 | 请求结果：true：表示请求成功。false：表示请求失败。          |
| load.test               | 判断当前是否为全链路压测：T：表示当前为全链路压测。当前线程中能获取到日志上下文，且上下文中有压测信息。F：表示当前非全链路压测。当前线程中不能获取到日志上下文，或上下文中没有压测信息。 |



## OkHttp 埋点接入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.alipay.sofa</groupId>
        <artifactId>sofaboot-dependencies</artifactId>
        <version>3.11.1</version>
        <relativePath/>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-tracer-okhttp</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-tracer-okhttp</name>
    <description>sofaboot-quickstart-tracer-okhttp</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



### 引入 Tracer 依赖

1. 在 SOFABoot 的 Web 项目中引入如下 Tracer 依赖：

```xml
<!-- import SOFABoot Dependency Tracer starter -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
</dependency>
```

添加 Tracer starter 依赖后，可在 SOFABoot 的全局配置文件中添加配置项目以定制 Tracer 的行为。详情见 [Tracer 配置项说明](https://help.aliyun.com/document_detail/151843.html?spm=a2c4g.280407.0.0.65896f45o7OjVg#h2-tracer-5)。



2. 基于 SOFATracer 的 OkHttp 插件

为了使得 OkHttp 这个第三方开源组件能够支持 SOFATracer 的链路调用，SOFATracer 提供了 OkHttp 的插件扩展，即 `sofa-tracer-okhttp-plugin`：

```xml
<!-- 基于 SOFATracer 的 OkHttp 插件 -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofa-tracer-okhttp-plugin</artifactId>
</dependency>
```



3. OkHttp 依赖

```xml
<!-- OkHttp 依赖 -->
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>3.12.1</version>
</dependency>
```



### 添加 properties

```properties
# Application Name
spring.application.name=sofaboot-quickstart-tracer-okhttp
# 日志输出目录，默认输出到 ${user.home}
logging.path=./logs

# 参考文档：https://help.aliyun.com/document_detail/151854.html
# 采样率 (0~100)%
com.alipay.sofa.tracer.samplerPercentage=100
# 采样模式类型名称
#com.alipay.sofa.tracer.samplerName=PercentageBasedSampler

# 统计日志的时间间隔，默认 60，这里为了方便快速看统计设置成 1
com.alipay.sofa.tracer.statLogInterval=1
com.alipay.sofa.tracer.zipkin.enabled=false

# 是否以 JSON 格式输出日志，使用非 JSON 格式输出，期望较少日志空间占用
#com.alipay.sofa.tracer.JSONOutput=false
```



### 构造 OkHttp 发起一次对上文的 RESTful 服务的调用

构造 OkHttp Client 调用实例的代码示例如下：

```java
String httpGetUrl = "http://localhost:8080/okhttp";
String responseStr = new OkhttpClientInstance().executeGet(httpGetUrl);
```



### 添加 Controller

如果您的 Web 工程中没有基于 Spring MVC 框架构建的 Controller，那么可以按照如下方式添加一个 Controller；如果已经有 Controller，那么可直接访问相应的服务。

```java
package com.sofaboot.quickstart.controller;

import com.sofaboot.quickstart.instance.OkhttpClientInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerOkhttpRestController.java, v 0.1 2024/05/27, ljt Exp $
 */
@RestController
public class TracerOkhttpRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TracerOkhttpRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/okhttp?name=
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/okhttp")
    public Map<String, Object> okhttp(@RequestParam(value = "name", defaultValue = "okhttp") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));
        return resultMap;
    }

    /**
     * Request http://localhost:8080/sync
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/sync")
    public String sync() throws Exception {
        String httpGetUrl = "http://localhost:8080/okhttp";
        String responseStr = new OkhttpClientInstance().executeGet(httpGetUrl);
        logger.info("Response is {}", responseStr);

        return responseStr;
    }
}
```



### 运行工程

可以将 SOFABoot 工程导入到 IDE 中，工程编译正确后，运行工程里面中的 main 方法启动应用。以上面添加的 Controller 为例，可以

通过在浏览器中输入 http://localhost:8080/sync 来访问 REST 服务，结果类似如下：

```json
{"success":true,"count":1,"content":"Hello, okhttp!"}
```



调用成功的控制台输出日志如下：

```
2024-05-27 00:00:00.000  INFO 22080 --- [nio-8080-exec-1] c.s.q.c.TracerOkhttpRestController       : Response is {"success":true,"count":1,"content":"Hello, okhttp!"}
```



在 SOFABoot 的配置文件 `application.properties` 中可定义日志打印目录。假设配置的日志打印目录是 `./logs`，即当前应用的根目录，应用名设置为 `spring.application.name=sofaboot-quickstart-tracer-okhttp`，那么在当前工程的根目录下可以看到类似如下结构的日志文件：

```
./logs
├── spring.log
└── tracelog
    ├── okhttp-digest.log
    ├── okhttp-stat.log
    ├── spring-mvc-digest.log
    ├── spring-mvc-stat.log
    ├── static-info.log
    └── tracer-self.log
```



#### 查看 OkHttp 摘要日志

以 OkHttp 同步调用为例，摘要日志 `okhttp-digest.log` 如下：

```json
{"time":"2024-05-27 00:00:00.000","local.app":"sofaboot-quickstart-tracer-okhttp","traceId":"c0a818011716811868219100122080","spanId":"0.1","span.kind":"client","result.code":"200","current.thread.name":"http-nio-8080-exec-1","time.cost.milliseconds":"81ms","request.url":"http://localhost:8080/okhttp","method":"GET","result.code":"200","req.size.bytes":0,"resp.size.bytes":0,"remote.app":"","sys.baggage":"","biz.baggage":""}
```

对应 key 的说明如下：

| key                    | 说明                    |
| ---------------------- | ----------------------- |
| time                   | 日志打印时间            |
| local.app              | 当前应用名              |
| traceId                | TraceId                 |
| spanId                 | SpanId                  |
| span.kind              | Span 类型               |
| result.code            | 结果码                  |
| current.thread.name    | 当前线程名              |
| time.cost.milliseconds | 请求耗时                |
| request.url            | 请求 URL                |
| method                 | 调用方法                |
| req.size.bytes         | 请求数据大小            |
| resp.size.bytes        | 响应数据大小            |
| remote.app             | 目标应用                |
| sys.baggage            | 系统透传的 baggage 数据 |
| biz.baggage            | 业务透传的 baggage 数据 |



#### 查看 OkHttp 统计日志

以 OkHttp 同步调用和异步调用为例，统计日志 `okhttp-stat.log` 如下：

```json
{"time":"2024-05-27 00:00:00.000","stat.key":{"method":"GET","local.app":"sofaboot-quickstart-tracer-okhttp","request.url":"http://localhost:8080/okhttp"},"count":1,"total.cost.milliseconds":81,"success":"Y","load.test":"F"}
```

对应 key 的说明如下：

| key                     | 说明                                                         |
| ----------------------- | ------------------------------------------------------------ |
| time                    | 日志打印时间                                                 |
| stat.key.method         | 调用方法                                                     |
| stat.key.local.app      | 当前应用名                                                   |
| stat.key.request.url    | 请求 URL                                                     |
| count                   | 请求次数                                                     |
| total.cost.milliseconds | 请求总耗时                                                   |
| success                 | 请求结果：true：表示请求成功。false：表示请求失败。          |
| load.test               | 判断当前是否为全链路压测：T：表示当前为全链路压测。当前线程中能获取到日志上下文，且上下文中有压测信息。F：表示当前非全链路压测。当前线程中不能获取到日志上下文，或上下文中没有压测信息。 |



## 集成 SLF4J MDC 功能

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.alipay.sofa</groupId>
        <artifactId>sofaboot-dependencies</artifactId>
        <version>3.11.1</version>
        <relativePath/>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sofaboot.quickstart</groupId>
    <artifactId>sofaboot-quickstart-tracer-slf4j</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>sofaboot-quickstart-tracer-slf4j</name>
    <description>sofaboot-quickstart-tracer-slf4j</description>

    <properties>
        <java.version>1.8</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```



### 引入 Tracer 依赖

1. 在 SOFABoot 的 Web 项目中引入如下 Tracer 依赖：

```xml
<!-- import SOFABoot Dependency Tracer starter -->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>tracer-sofa-boot-starter</artifactId>
</dependency>
```

添加 Tracer starter 依赖后，可在 SOFABoot 的全局配置文件中添加配置项目以定制 Tracer 的行为。详情见 [Tracer 配置项说明](https://help.aliyun.com/document_detail/151843.html?spm=a2c4g.280407.0.0.65896f45o7OjVg#h2-tracer-5)。



2. slf4j 依赖

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```



### 添加 properties

```properties
# Application Name
spring.application.name=sofaboot-quickstart-tracer-mvc-slf4j
# 日志输出目录，默认输出到 ${user.home}
logging.path=./logs

# 参考文档：https://help.aliyun.com/document_detail/151854.html
# 采样率 (0~100)%
com.alipay.sofa.tracer.samplerPercentage=100
# 采样模式类型名称
#com.alipay.sofa.tracer.samplerName=PercentageBasedSampler

# 统计日志的时间间隔，默认 60，这里为了方便快速看统计设置成 1
com.alipay.sofa.tracer.statLogInterval=1
com.alipay.sofa.tracer.zipkin.enabled=false

# 是否以 JSON 格式输出日志，使用非 JSON 格式输出，期望较少日志空间占用
#com.alipay.sofa.tracer.JSONOutput=false

# 扩展的日志配置文件，扩展 xml 配置大于 yaml 配置
logging.level.=info
# 扩展的日志配置文件，扩展 xml 配置大于 yaml 配置
logging.config=classpath:logback-spring-back.xml
```



### logback 示例

1. 在 PatternLayout 中增加 `%X{SOFA-TraceId}` 和 `%X{SOFA-SpanId}` 配置：
   - `%X{SOFA-TraceId}`：对应 `TraceId`，实际运行时将会被替换为当前 Tracer 上下文的 TraceId，如果当前不存在 Tracer 上下文，则会被替换为空字符串。
   - `%X{SOFA-SpanId}`：对应 `SpanId`，实际运行时将会被替换为当前 Tracer 上下文的 SpanId，如果当前不存在 Tracer 上下文，则会被替换为空字符串。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- please pay attention that: file name should not be logback.xml，name it logback-spring.xml to use it in springboot framework -->
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为 WARN，则低于 WARN 的信息都不会输出 -->
<!-- scan：当此属性设置为 true 时，配置文件如果发生改变，将会被重新加载，默认值为 true -->
<!-- scanPeriod：设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当 scan 为 true 时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug：当此属性设置为 true 时，将打印出 logback 内部日志信息，实时查看 logback 运行状态。默认值为 false。 -->
<configuration scan="true" scanPeriod="10 seconds" debug="false">
    <springProperty scope="context" name="logging.path" source="logging.path"/>
    <springProperty scope="context" name="spring.application.name" source="spring.application.name"/>

    <!-- Log formatted output -->
    <property name="MDC_FILE_PATTERN"
              value="%d{yyyy-MM-dd'T'HH:mm:ss.SSS'Z'} %-5level ${PID:-} [%thread] [%X{SOFA-TraceId},%X{SOFA-SpanId}] %logger{50} [%method,%line] - %msg%n"/>
    <property name="MDC_CONSOLE_PATTERN"
              value="%d{yyyy-MM-dd'T'HH:mm:ss.SSS'Z'} %highlight(%-5level) ${PID:-} [%magenta(%20.20thread)] [%X{SOFA-TraceId},%X{SOFA-SpanId}] %yellow(%logger{50}) [%cyan(%method,%line)] - %msg%n"/>

    <!-- console output -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <Pattern>${MDC_CONSOLE_PATTERN}</Pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- to generate logfile daily -->
    <appender name="ERROR-APPENDER" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <append>true</append>
        <!-- a filter that show green light for object that has a error log level-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- filter level -->
            <level>ERROR</level>
            <!-- match operation: ACCEPT -->
            <onMatch>ACCEPT</onMatch>
            <!-- mismatch operation: DENY -->
            <onMismatch>DENY</onMismatch>
        </filter>
        <!-- log name -->
        <file>${logging.path}/${spring.application.name}/common-error.log</file>
        <!-- to generate a log file everyday with a longest lasting of 30 days -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- logfile name with daily rolling -->
            <FileNamePattern>${logging.path}/${spring.application.name}/common-error.log.%d{yyyy-MM-dd}
            </FileNamePattern>
            <!-- limit the total size of your 30-day history to 3GB -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>3GB</totalSizeCap>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- output format：%d is for date，%thread is for thread name，%-5level：loglevel with 5 character  %msg：log message，%n line breaker -->
            <pattern>${MDC_FILE_PATTERN}</pattern>
            <!-- encoding -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <appender name="ROOT-APPENDER" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <append>true</append>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <!-- filter level -->
            <level>INFO</level>
            <!-- match operation: ACCEPT -->
            <onMatch>ACCEPT</onMatch>
            <!-- mismatch operation: DENY -->
            <onMismatch>DENY</onMismatch>
        </filter>
        <file>${logging.path}/${spring.application.name}/common-default.log</file>
        <!-- to generate a log file everyday with a longest lasting of 30 days -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- logfile name with daily rolling-->
            <FileNamePattern>${logging.path}/${spring.application.name}/common-default.log.%d{yyyy-MM-dd}
            </FileNamePattern>
            <!-- limit the total size of your 30-day history to 3GB -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>3GB</totalSizeCap>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- output format：%d is for date，%thread is for thread name，%-5level：loglevel with 5 character  %msg：log message，%n line breaker -->
            <pattern>${MDC_FILE_PATTERN}</pattern>
            <!-- encoding -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- SOFATracer MDC -->
    <appender name="MDC-EXAMPLE-APPENDER" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <append>true</append>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <!-- filter level -->
            <level>INFO</level>
        </filter>
        <file>${logging.path}/${spring.application.name}/mdc-example.log</file>
        <!-- to generate a log file everyday with a longest lasting of 30 days -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- logfile name with daily rolling-->
            <FileNamePattern>${logging.path}/${spring.application.name}/mdc-example.log.%d{yyyy-MM-dd}</FileNamePattern>
            <!-- limit the total size of your 30-day history to 3GB -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>3GB</totalSizeCap>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- output format：%d is for date，%thread is for thread name，%-5level：loglevel with 5 character  %msg：log message，%n line breaker -->
            <pattern>${MDC_FILE_PATTERN}</pattern>
            <!-- encoding -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 设置 Spring & Hibernate 日志输出级别 -->
    <logger name="org.springframework" level="WARN"/>
    <logger name="org.mybatis" level="WARN"/>
    <logger name="com.ibatis" level="DEBUG"/>
    <logger name="com.ibatis.common.jdbc.SimpleDataSource" level="DEBUG"/>
    <logger name="com.ibatis.common.jdbc.ScriptRunner" level="DEBUG"/>
    <logger name="com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate" level="DEBUG"/>
    <logger name="java.sql.Connection" level="DEBUG"/>
    <logger name="java.sql.Statement" level="DEBUG"/>
    <logger name="java.sql.PreparedStatement" level="DEBUG"/>
    <logger name="com.ruidou.baoqian.mapper" level="DEBUG"/>
    <!-- TODO：name 改成自己的项目的包路径 -->
    <logger name="com.sofaboot.quickstart.controller" level="DEBUG"/>

    <logger name="com.sofaboot.quickstart" level="INFO" additivity="false">
        <appender-ref ref="ROOT-APPENDER"/>
        <appender-ref ref="ERROR-APPENDER"/>
    </logger>

    <logger name="MDC-EXAMPLE" level="INFO" additivity="false">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="MDC-EXAMPLE-APPENDER"/>
        <appender-ref ref="ERROR-APPENDER"/>
    </logger>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="ROOT-APPENDER"/>
        <appender-ref ref="ERROR-APPENDER"/>
    </root>

</configuration>
```



### 添加 Controller

如果您的 Web 工程中没有基于 Spring MVC 框架构建的 Controller，那么可以按照如下方式添加一个 Controller；如果已经有 Controller，那么可直接访问相应的服务。

```java
package com.sofaboot.quickstart.controller;

import com.alipay.common.tracer.core.async.SofaTracerRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljt
 * @version: $Id: TracerSlf4jRestController.java, v 0.1 2024/05/28, ljt Exp $
 */
@RestController
public class TracerSlf4jRestController {

    /**
     * 日志记录器对象
     */
    private static final Logger logger = LoggerFactory.getLogger("MDC-EXAMPLE");
    // private static final Logger logger = LoggerFactory.getLogger(TracerSlf4jRestController.class);

    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Request http://localhost:8080/slf4j?name=slf4j
     *
     * @param name name
     * @return Map of Result
     */
    @RequestMapping("/slf4j")
    public Map<String, Object> slf4j(@RequestParam(value = "name", defaultValue = "SOFATracer SLF4J MDC EXAMPLE") String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        resultMap.put("count", counter.incrementAndGet());
        resultMap.put("content", String.format(TEMPLATE, name));

        long id = Thread.currentThread().getId();
        logger.info("SOFATracer Print TraceId and SpanId ");

        // Asynchronous thread transparent transmission
        final SofaTracerRunnable sofaTracerRunnable = new SofaTracerRunnable(new Runnable() {
            @Override
            public void run() {
                logger.info("SOFATracer Print TraceId and SpanId In Child Thread.");
            }
        });

        Thread thread = new Thread(sofaTracerRunnable);
        thread.start();

        return resultMap;
    }
}
```



### 运行工程

可以将 SOFABoot 工程导入到 IDE 中，工程编译正确后，运行工程里面中的 main 方法启动应用。以上面添加的 Controller 为例，可以

通过在浏览器中输入 http://localhost:8080/slf4j 来访问 REST 服务，结果类似如下：

```json
{ "success": true, "count": 1, "content": "Hello, SOFATracer SLF4J MDC EXAMPLE!" }
```



调用成功的控制台输出日志如下：

```
2024-05-28T00:00:00.000Z INFO  17952 [http-nio-8080-exec-2] [c0a818011716916466413100117952,0] MDC-EXAMPLE [slf4j,44] - SOFATracer Print TraceId and SpanId 
2024-05-28T00:00:00.000Z INFO  17952 [           Thread-12] [c0a818011716916466413100117952,0] MDC-EXAMPLE [run,50] - SOFATracer Print TraceId and SpanId In Child Thread.
```



# SOFABoot 安全兼容版本

| **依赖**       | **包/组件名称**                | **3.6.6 版本** | **3.10.2 版本** |
| -------------- | ------------------------------ | -------------- | --------------- |
| 安全版本升级   | SpringBoot                     | 2.3.12         | 2.7.15          |
|                | Spring Framework               | 5.2.21         | 5.3.29          |
|                | logback                        | 1.2.8          | 1.2.12          |
|                | slf4j                          | 1.7.30         | 1.7.36          |
|                | gson                           | 2.8.9          | 2.9.1           |
|                | protobuf-java                  | 3.11.0         | 3.21.12         |
|                | jackson                        | 2.11.4         | 2.14.2          |
|                | okhttp                         | 3.14.9         | 4.9.3           |
|                | commons-beanutils              | 2.9.3          | 2.9.4           |
|                | commons-fileupload             | 1.4            | 1.5             |
|                | resteasy                       | 3.6.3.Final    | 3.11.3.Final    |
|                | netty                          | 4.1.65.Final   | 4.1.97.Final    |
|                | tomcat                         | 9.0.43         | 9.0.79          |
|                | aviator                        | 4.2.7          | 5.3.3           |
|                | ant                            | 1.7.1          | 1.9.16          |
|                | groovy                         | 2.5.14         | 3.0.19          |
|                | hibernate-validator            | 5.2.4.Final    | 6.2.5.Final     |
|                | jasypt                         | 1.5            | 1.9.3           |
|                | velocity                       | 1.6            | 1.7             |
|                | snakeyaml                      | 1.32           | 1.33            |
| SOFA 依赖      | SOFABoot 开源版                | 3.11.1         | 3.19.1          |
|                | sofa-common-tools              | 1.3.6          | 1.3.11          |
|                | registry-client-enterprise-all | 5.5.1.RELEASE  | 5.6.0           |
| 间接的三方依赖 | commons-logging                | 1.1.1          | 1.1.2           |
|                | commons-pool                   | 1.3            | 1.6             |
|                | commons-lang                   | 3.3.10         | 3.3.12.0        |
|                | io.prometheus:simpleclient     | 0.10.0         | 0.15.0          |
|                | com.beust:jcommander           | 1.72           | 1.78            |
|                | json-path                      | 2.4.0          | 2.7.0           |
|                | okio                           | 1.17.2         | 2.8.0           |
|                | javax.mail                     | 1.6.2          | 1.6.7           |
|                | picocli                        | 4.3.2          | 4.6.3           |
|                | byte-buddy                     | 1.10.22        | 1.12.23         |
|                | net.minidev                    | 2.3.1          | 2.4.11          |
|                | apiguardian-api                | 1.1.0          | 1.1.2           |
|                | assertj-core                   | 3.16.1         | 3.22.0          |
|                | org.glassfish.jaxb             | 2.3.4          | 2.3.8           |
|                | javassist                      | 3.19.0-GA      | 3.28.0-GA       |
|                | jboss-logging                  | 3.4.2.Final    | 3.4.3.Final     |
|                | jboss-annotations-api_1.3_spec | 1.0.1.Final    | 2.0.1.Final     |
|                | objenesis                      | 2.6            | 3.1             |
|                | asm                            | 5.0.4          | 9.3             |
|                | reactive-streams               | 1.0.3          | 1.0.4           |
|                | jsonassert                     | 1.5.0          | 1.5.1           |
| 测试框架       | junit                          | 5.6.3          | 5.8.2           |
|                | junit-platform                 | 1.6.3          | 1.8.2           |
|                | mockito                        | 3.3.3          | 3.6.28          |
|                | testng                         | 6.13.1         | 7.5             |