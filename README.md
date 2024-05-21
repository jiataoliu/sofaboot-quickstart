# 参考文档

https://www.sofastack.tech/projects/sofa-boot/quick-start/《SOFABoot 快速开始》

https://help.aliyun.com/document_detail/133243.html《SOFABoot 快速入门》

https://www.sofastack.tech/projects/sofa-tracer/usage-of-mvc/《SOFATracer 日志 Spring MVC 埋点接入》

https://help.aliyun.com/document_detail/151860.html《SOFATracer 日志说明》

https://help.aliyun.com/document_detail/151854.html《SOFATracer 日志采样模式》



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

为了使得 HttpClient 这个第三方开源组件能够支持 SOFATracer 的链路调用，SOFATracer 提供了 HttpClient 的插件扩展，即 `tracer-enterprise-httpclient-plugin`：

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

```
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

以 HttpClient 同步调用为例，摘要日志 `httpclient-digest.log` 如下：

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

以 HttpClient 同步调用为例，统计日志 `httpclient-stat.log` 如下：

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

