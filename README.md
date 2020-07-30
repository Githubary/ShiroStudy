## byStart权限管理项目简介

byStart是基于hope-plus和若依权限管理的开源项目，实现的一个权限管理的平台。

byStart是您学习权限管理的最佳平台，也是想学习java平台的最好帮手，是最适合初学者上手的一个项目。

byStart是在springboot的基础上搭建的一个项目，以MVC为模型视图控制器，Mybatis为数据访问层，Thymeleaf为前端模板引擎
Apache shiro为权限授权层，Redis对常用数据进行缓存。

byStart目前包括仅仅包含角色权限管理和资源管理。

## 内置功能

1.	用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.	角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。

## 为何选择byStart

1. 使用 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) 协议，源代码完全开源，无商业限制。
2. 使用目前主流的Java EE开发框架，简单易学，学习成本低。
3. 数据库无限制，目前支持MySql、Oracle，可扩充SQL Server、PostgreSQL、H2等。
4. 操作权限控制精密细致，对所有管理链接都进行权限验证，可控制到按钮。

## 技术选型

1、后端

* [Spring Boot](https://github.com/spring-projects/spring-boot)：核心框架
* [Apache Shiro](https://github.com/apache/shiro)：权限框架
* [Redis](https://github.com/antirez/redis)：缓存框架
* [MyBatis](https://github.com/mybatis/mybatis-3)：用于Java的MyBatis SQL Mapper框架
* [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)：分页插件
* [shiro-redis](https://github.com/alexxiyang/shiro-redis)：一个可以由shiro使用的redis缓存工具
* [Lombok](https://www.projectlombok.org/)：简洁你的实体代码

2、前端

* [Bootstrap](https://github.com/twbs/bootstrap.git)：使用最广泛的前端 ui 框架
* [Layer](https://github.com/sentsin/layer.git)：弹出层组件
* [JQuery](https://github.com/jquery/jquery.git)：使用最广泛的 JavaScript 框架
* [Thymeleaf](https://github.com/thymeleaf/thymeleaf)：模板引擎

3、登录
* 管理员账号管理（最高管理员默认账号：123 密码：123456）。

## 快速体验
1. 克隆到本地后，运行ide，导入项目，等待jar包下载。
2. 将docs\db中的sql文件拖入到你的数据库软件中运行。
3. 修改src\main\resources\application.properties文件中的数据库设置参数。
4. 修改src\main\resources\application.properties文件中的Redis设置参数
5. 最高管理员账号，用户名：123 密码：123456

## 感谢、交流、反馈
如果你喜欢byStart，希望您可以为其点上star。我也诚挚的希望byStart能够帮助到您，在您的学习道路上起到一些些的作用。
作者博客：[不洇的博客](https://blog.csdn.net/qq_44705904)（今天不学习，明天变垃圾）。
欢迎指出不足，我们一起学习进步！
