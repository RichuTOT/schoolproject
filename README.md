# schoolproject
springboot+vue

版本
安装jdk17
安装apache maven
进入项目目录并构建项目：
cd schoolproject/shetuanlianmeng
mvn clean install
前端
安装node.js
全局安装 Vue CLI：
npm install -g @vue/cli
进入项目目录并安装依赖：
cd schoolproject/Test2/my-project
npm install
在项目根目录下运行 Spring Boot 应用：   
cd schoolproject/shetuanlianmeng
mvn spring-boot:run
在前端项目目录下启动开发服务器：
cd schoolproject/Test2/my-project
npm run serve

创建数据库 club_management 并配置用户和权限。
application.properties
配置 Spring Boot 应用的数据库连接