# shop-demo 电商项目启动指南

## 项目简介

这是一个基于 Java + Spring Boot + MyBatis + MySQL 的电商网站演示项目。

---

## 环境准备

### 1. 安装 JDK

项目需要 Java 11 或更高版本。

```bash
# 检查是否已安装
java -version

# 如果没有安装，在 Ubuntu 上执行：
sudo apt install openjdk-11-jdk
```

### 2. 安装 MySQL

项目需要 MySQL 5.7 或更高版本。

```bash
# 检查是否已安装
mysql --version

# 如果没有安装，在 Ubuntu 上执行：
sudo apt install mysql-server
sudo apt install mysql-client
```

### 3. 安装 Maven

```bash
# 检查是否已安装
mvn -version

# 如果没有安装，在 Ubuntu 上执行：
sudo apt install maven
```

---

## 启动步骤

### 步骤 1: 启动 MySQL 服务

```bash
# 启动 MySQL
sudo service mysql start

# 或者
sudo systemctl start mysql
```

### 步骤 2: 创建数据库

`sql/init.sql` 是一个 SQL 脚本文件，它包含了：
- 创建数据库 `shop_demo`
- 创建 6 张数据表 (users, categories, products, carts, orders, order_items)
- 插入初始测试数据


可能报错:
运行
# 1. 用 sudo 无密码进入 MySQL
sudo mysql

# 2. 在 MySQL 命令行中执行（将 '你的新密码' 替换为强密码）：
ALTER USER 'root'@'localhost' IDENTIFIED WITH caching_sha2_password BY '你的新密码';
# 或兼容旧客户端的命令：
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '你的新密码';

# 3. 刷新权限
FLUSH PRIVILEGES;

# 4. 退出
EXIT;



执行方式：




```bash
# 方式一：使用命令行执行
mysql -u root -p < /home/luke/shop-demo/sql/init.sql

# 方式二：登录 MySQL 后执行
mysql -u root -p
source /home/luke/shop-demo/sql/init.sql
```

> 注意：如果你的 MySQL root 用户没有密码，可以去掉 `-p` 参数。

### 步骤 3: 修改数据库配置

编辑 `shop-api/src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/shop_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root      # 你的用户名
    password: root      # 你的密码
```

### 步骤 4: 编译项目

```bash
cd /home/luke/shop-demo
mvn clean package -DskipTests
```

首次编译会下载依赖，可能需要几分钟。

### 步骤 5: 启动项目

```bash
# 方式一：直接运行 jar 包
java -jar shop-api/target/shop-api-1.0.0.jar

# 方式二：使用 Maven 运行
cd shop-api
mvn spring-boot:run
```

启动成功后，访问 `http://localhost:8080`

---

## 验证启动成功

### 1. 检查日志

看到类似以下输出说明启动成功：
```
Started ShopDemoApplication in X seconds
```

### 2. 测试接口

使用 curl 或 Postman 测试：

```bash
# 测试获取商品列表
curl http://localhost:8080/api/products

# 测试用户注册
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"123456","email":"test@test.com"}'
```

---

## 常见问题

### Q1: 端口 8080 被占用？

修改 `application.yml` 中的端口：
```yaml
server:
  port: 8081
```

### Q2: MySQL 连接失败？

1. 确认 MySQL 已启动：`sudo service mysql status`
2. 检查用户名密码是否正确
3. 确认数据库已创建

### Q3: 编译失败？

尝试清理并重新编译：
```bash
mvn clean
mvn package -DskipTests
```

---

## 接口文档

| 模块 | 接口 | 说明 |
|------|------|------|
| 用户 | POST /api/users/register | 注册 |
| 用户 | POST /api/users/login | 登录 |
| 用户 | GET /api/users/{id} | 获取用户信息 |
| 商品 | GET /api/products | 商品列表 |
| 商品 | GET /api/products/{id} | 商品详情 |
| 商品 | GET /api/products/search?keyword=xxx | 搜索商品 |
| 分类 | GET /api/categories | 分类列表 |
| 购物车 | GET /api/cart | 购物车列表 |
| 购物车 | POST /api/cart | 添加到购物车 |
| 订单 | POST /api/orders | 创建订单 |
| 订单 | GET /api/orders | 订单列表 |

---

## 下一步

项目启动后，可以：
1. 使用 Postman 或前端页面调用接口
2. 根据需要修改业务逻辑
3. 添加前端页面 (Vue/React)
