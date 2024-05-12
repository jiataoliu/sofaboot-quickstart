# 快速开始

参考：https://www.sofastack.tech/projects/sofa-boot/quick-start/


参考：https://help.aliyun.com/document_detail/133243.html


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



# 创建工程

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



# 依赖管理

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



# 启动应用

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
2024-05-12 03:12:22.849  INFO 31384 --- [ main] : Starting SofabootQuickstartApplication on LAPTOP-0UIAERQR with PID 99999 (C:\xxx\xxx\sofaboot-quickstart\target\classes started by root in C:\xxx\xxx\sofaboot-quickstart)
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



# 健康检查

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



# 查看日志

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
public class JvmServiceConsumer implements ClientFactoryAware {
    private ClientFactory    clientFactory;

    public void init() {
        ReferenceClient referenceClient = clientFactory.getClient(ReferenceClient.class);
        ReferenceParam<SampleJvmService> referenceParam = new ReferenceParam<>();
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





