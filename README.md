# Life Wish List (人生愿望清单)

Life Wish List 是一个基于Spring Boot开发的愿望清单管理系统，允许用户创建和管理个人愿望清单。

## 项目概述

这是一个RESTful API服务，提供用户认证、愿望桶(Bucket)管理、愿望(Wish)管理和愿望日志(WishLog)等功能。用户可以创建个人愿望清单，跟踪愿望的完成进度，并记录实现愿望的过程。

## 技术栈

- **后端框架**: Spring Boot 3.5.5
- **编程语言**: Java 17
- **数据库**: MySQL
- **安全框架**: Spring Security + JWT
- **API文档**: SpringDoc OpenAPI (Swagger)
- **构建工具**: Maven
- **其他技术**: JPA/Hibernate, Lombok

## 功能模块

### 1. 用户认证
- 用户注册
- 用户登录
- JWT Token认证
- 获取当前用户信息

### 2. 愿望桶(Bucket)管理
- 创建愿望桶
- 获取用户的所有愿望桶
- 获取特定愿望桶
- 更新愿望桶
- 删除愿望桶

### 3. 愿望(Wish)管理
- 在愿望桶中创建愿望
- 获取愿望桶中的所有愿望
- 获取特定愿望
- 更新愿望
- 标记愿望为完成
- 删除愿望

### 4. 愿望日志(WishLog)管理
- 为愿望创建日志
- 获取愿望的所有日志
- 获取特定日志
- 更新日志
- 删除日志

## 项目结构

```
src
├── main
│   ├── java
│   │   └── org.jinwenxin.lifewishlist
│   │       ├── config       # 配置类
│   │       ├── controller   # 控制器层
│   │       ├── dto          # 数据传输对象
│   │       ├── exception    # 异常处理
│   │       ├── model        # 实体模型
│   │       ├── repository   # 数据访问层
│   │       ├── service      # 业务逻辑层
│   │       └── util         # 工具类
│   └── resources
│       └── application.properties  # 配置文件
└── test
    └── java                    # 测试代码
```

## 环境要求

- Java 17+
- Maven 3.6+
- MySQL 8.0+

## 快速开始

### 1. 数据库配置

在 `src/main/resources/application.properties` 中配置数据库连接：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/life_wish_db?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. 构建项目

```bash
# 克隆项目
git clone <repository-url>

# 进入项目目录
cd life-wish-list

# 构建项目
mvn clean install

# 运行项目
mvn spring-boot:run
```

### 3. 访问API

项目启动后，可以通过以下URL访问：

- API根地址: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- API文档: http://localhost:8080/v3/api-docs

## API接口

### 认证相关接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/auth/me` - 获取当前用户信息

### 愿望桶接口
- `POST /api/buckets` - 创建愿望桶
- `GET /api/buckets` - 获取用户的所有愿望桶
- `GET /api/buckets/{id}` - 获取特定愿望桶
- `PUT /api/buckets/{id}` - 更新愿望桶
- `DELETE /api/buckets/{id}` - 删除愿望桶

### 愿望接口
- `POST /api/buckets/{bucketId}/wishes` - 在愿望桶中创建愿望
- `GET /api/buckets/{bucketId}/wishes` - 获取愿望桶中的所有愿望
- `GET /api/buckets/{bucketId}/wishes/{id}` - 获取特定愿望
- `PUT /api/buckets/{bucketId}/wishes/{id}` - 更新愿望
- `PATCH /api/buckets/{bucketId}/wishes/{id}/complete` - 标记愿望为完成
- `DELETE /api/buckets/{bucketId}/wishes/{id}` - 删除愿望

### 愿望日志接口
- `POST /api/wishes/{wishId}/logs` - 为愿望创建日志
- `GET /api/wishes/{wishId}/logs` - 获取愿望的所有日志
- `GET /api/wishes/{wishId}/logs/{id}` - 获取特定日志
- `PUT /api/wishes/{wishId}/logs/{id}` - 更新日志
- `DELETE /api/wishes/{wishId}/logs/{id}` - 删除日志

## 测试

项目包含单元测试，可以使用以下命令运行：

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=AuthControllerTest
```

## 开发工具

- 推荐使用IntelliJ IDEA进行开发
- 使用Postman或Swagger UI测试API接口
- 使用Lombok减少样板代码

## 许可证

本项目采用 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html) 许可证。

## 联系方式

如有问题，请联系项目维护者。