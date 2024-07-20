
社团联盟用户手册
1.	系统安装手册
o	1.1 系统运行的软硬件环境说明
o	1.2 数据库配置说明
o	1.3 系统安装步骤
	1.3.1 后端安装
	1.3.2 前端安装
2.	用户使用指导
o	2.1 登录界面
o	2.2 主界面
o	2.3 功能界面
	2.3.1 社团信息管理
	2.3.2 活动发布与管理
	2.3.3 成员管理
	2.3.4 后台管理
	2.3.5 聊天功能
系统安装手册
系统运行的软硬件环境说明
软件环境
•	操作系统：Windows 10 或以上 / macOS / Linux
•	JDK版本：17
•	Apache Maven：3.8.1 或以上
•	Node.js：14.17.0 或以上
•	Vue CLI：4.5.0 或以上
•	数据库：PostgreSQL 13 或以上
硬件环境
•	CPU：Intel Core i5 或以上
•	内存：8 GB 或以上
•	存储空间：至少 20 GB 可用空间
数据库配置说明
1.	安装 PostgreSQL 数据库，并创建数据库 club_management。
2.	创建数据库用户并授予适当权限：
CREATE USER club_user WITH ENCRYPTED PASSWORD 'password';
CREATE DATABASE club_management;
GRANT ALL PRIVILEGES ON DATABASE club_management TO club_user;
系统安装步骤
后端安装
1.	安装 JDK 17：
o	下载并安装 JDK 17。
o	配置 JAVA_HOME 环境变量，指向 JDK 17 的安装路径。
2.	安装 Apache Maven：
o	下载并安装 Apache Maven。
o	配置 MAVEN_HOME 环境变量，指向 Maven 的安装路径。
3.	进入项目目录并构建项目：
cd schoolproject/shetuanlianmeng
mvn clean install
配置 Spring Boot 应用的数据库连接： 编辑 application.properties 文件，添加数据库连接配置：
spring.datasource.url=jdbc:mysql://localhost:5432/club_management
spring.datasource.username=club_user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
运行 Spring Boot 应用：
mvn spring-boot:run
前端安装
1.	安装 Node.js：
o	下载并安装 Node.js。
2.	全局安装 Vue CLI：
npm install -g @vue/cli
进入前端项目目录并安装依赖：
cd schoolproject/Test2/my-project
npm install
启动前端开发服务器：
npm run serve/npm run dev

用户使用指导
登录界面
1.	打开浏览器，访问前端开发服务器地址（默认为 http://localhost:8080）。
2.	在登录界面输入用户名和密码，点击“登录”按钮。 
主界面
1.	登录成功后，进入主界面。
2.	主界面显示用户的基本信息和主要功能入口。 
功能界面
社团信息管理
1.	在主界面点击“社团信息管理”。
2.	在社团信息管理界面，可以查看、创建、修改和删除社团信息。 
活动发布与管理
1.	在主界面点击“活动发布与管理”。
2.	在活动发布与管理界面，可以发布新活动，查看活动列表，并对活动进行管理。 
成员管理
1.	在主界面点击“成员管理”。
2.	在成员管理界面，可以查看社团成员，添加新成员，修改成员信息，并删除成员。 
后台管理
1.	在主界面点击“后台管理”。
2.	后台管理界面包括社团审核、活动审核、用户管理等功能。 
聊天功能
1.	在主界面点击“聊天”。
2.	在聊天界面，可以选择社团进行聊天，发送和接收消息。

