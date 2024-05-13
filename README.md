# 参考文档

https://www.sofastack.tech/projects/sofa-boot/quick-start/


https://help.aliyun.com/document_detail/133243.html



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