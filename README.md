# SOA-Framework 
#### 基于spring+springmvc+hibernate+mybatis+springData的分布式服务型框架，包含内容服务、单点登录服务、solr搜索服务
## 扫盲介绍
[SOA （面向服务的架构）](https://baike.baidu.com/item/SOA/2140650?fr=aladdin)
> 面向服务的架构（SOA）是一个组件模型，它将应用程序的不同功能单元（称为服务）通过这些服务之间定义良好的接口和契约联系起来。接口是采用中立的方式进行定义的，它应该独立于实现服务的硬件平台、操作系统和编程语言。这使得构建在各种各样的系统中的服务可以以一种统一和通用的方式进行交互。
***
[Dubbo](http://dubbo.io/)
> 一个分布式、高性能、透明化的RPC服务框架，提供服务自动注册、自动发现等高效服务治理方案.
***
[ZooKeeper](https://baike.baidu.com/item/zookeeper/4836397?fr=aladdin)
> ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实现，是Hadoop和Hbase的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。
ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。
ZooKeeper包含一个简单的原语集,提供Java和C的接口。
ZooKeeper代码版本中，提供了分布式独享锁、选举、队列的接口，代码在zookeeper-3.4.3\src\recipes。其中分布锁和队列有Java和C两个版本，选举只有Java版本。
***
[Dozer](http://www.alliedjeep.com/146242.htm)
> Dozer是一个JavaBean映射工具库。
它支持简单的属性映射，复杂类型映射，双向映射，隐式显式的映射，以及递归映射。
它支持三种映射方式：注解、API、XML。
它是开源的，遵从Apache 2.0 协议
***
[SpringData之JPA](http://www.jianshu.com/p/1ccb6de76d38)
> SpringData为我们使用统一的API来对主流的数据存储技术进行数据访问操作提供了支持（基于JPA标准）。这是Spring通过SpringDataCommons项目来实现的，它是上述各种SpringData项目的依赖。SpringDataCommons让我们在使用关系型或非关系型数据访问技术时都使用基于Spring的统一标注，该标准包含CRUD（创建、获取、更新、删除）查询、排序和分页的相关操作。
***
[Spring增强控制器--@ControllerAdvice](http://blog.csdn.net/sanjay_f/article/details/47441631)
> Classes with @ControllerAdvice can be declared explicitly as Spring beans or auto-detected via classpath scanning. All such beans are sorted via AnnotationAwareOrderComparator, i.e. based on @Order and Ordered, and applied in that order at runtime. For handling exceptions the first @ExceptionHandler to match the exception is used. For model attributes and InitBinder initialization, @ModelAttribute and @InitBinder methods will also follow @ControllerAdvice order.
> By default the methods in an @ControllerAdvice apply globally to all Controllers. Use selectors annotations(), basePackageClasses(), and basePackages() (or its alias value()) to define a more narrow subset of targeted Controllers. If multiple selectors are declared, OR logic is applied, meaning selected Controllers should match at least one selector. Note that selector checks are performed at runtime and so adding many selectors may negatively impact performance and add complexity.
***
[JSR 303 - Bean Validation](https://www.ibm.com/developerworks/cn/java/j-lo-jsr303/)
> 在任何时候，当你要处理一个应用程序的业务逻辑，数据校验是你必须要考虑和面对的事情。应用程序必须通过某种手段来确保输入进来的数据从语义上来讲是正确的。在通常的情况下，应用程序是分层的，不同的层由不同的开发人员来完成。很多时候同样的数据验证逻辑会出现在不同的层，这样就会导致代码冗余和一些管理的问题，比如说语义的一致性等。为了避免这样的情况发生，最好是将验证逻辑与相应的域模型进行绑定。
Bean Validation 为 JavaBean 验证定义了相应的元数据模型和 API。缺省的元数据是 Java Annotations，通过使用 XML 可以对原有的元数据信息进行覆盖和扩展。在应用程序中，通过使用 Bean Validation 或是你自己定义的 constraint，例如 @NotNull, @Max, @ZipCode， 就可以确保数据模型（JavaBean）的正确性。constraint 可以附加到字段，getter 方法，类或者接口上面。对于一些特定的需求，用户可以很容易的开发定制化的 constraint。Bean Validation 是一个运行时的数据验证框架，在验证之后验证的错误信息会被马上返回。
下载 JSR 303 – Bean Validation 规范 http://jcp.org/en/jsr/detail?id=303 。
Hibernate Validator 是 Bean Validation 的参考实现 . Hibernate Validator 提供了 JSR 303 规范中所有内置 constraint 的实现，除此之外还有一些附加的 constraint。如果想了解更多有关 Hibernate Validator 的信息，请查看 http://www.hibernate.org/subprojects/validator.html
***
[hession]()
> Hessian是一个轻量级的remoting onhttp工具，使用简单的方法提供了RMI的功能。 相比WebService，Hessian更简单、快捷。采用的是二进制RPC协议，因为采用的是二进制协议，所以它很适合于发送二进制数据。
*注意：Dubbo默认序列化采用就是Hessian。当pojo中对象没有无参构造器时，反序列化会报错*
[解决方案](http://goodluck-wgw.iteye.com/blog/2220054)：http://goodluck-wgw.iteye.com/blog/2220054
***

## Start
#### 1、 框架结构
##### 1.1、表现层
> 后台管理系统、 搜索系统、用户系统 ...
##### 1.2、服务层
> 内容服务、 单点登录服务、 搜索服务 ...
##### 1.3、 持久层
> springData-jpa、 hibernate-jpa、mybatis
#### 2、 流程
![流程](http://d.pr/i/3P91Uc.png)
