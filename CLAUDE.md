# Java 电商项目代码规范

## 1. 项目结构规范

### 1.1 分层架构
```
shop-demo/
├── shop-common/          # 公共模块（工具类、通用枚举、常量）
├── shop-model/           # 实体类（Entity/DTO/VO）
├── shop-dao/             # 数据访问层（Mapper 接口）
├── shop-service/         # 业务逻辑层（Service 接口 + 实现）
├── shop-api/             # API 接口定义（Controller）
└── pom.xml               # Maven 配置
```

### 1.2 包命名规范
- `com.shop.demo.*` 作为基础包名
- `com.shop.demo.common` - 公共工具类
- `com.shop.demo.model.entity` - 实体类
- `com.shop.demo.model.dto` - 数据传输对象
- `com.shop.demo.model.vo` - 视图对象
- `com.shop.demo.dao.mapper` - Mapper 接口
- `com.shop.demo.service` - 业务接口
- `com.shop.demo.service.impl` - 业务实现
- `com.shop.demo.api.controller` - 控制器
- `com.shop.demo.config` - 配置类
- `com.shop.demo.exception` - 异常处理

## 2. 命名规范

### 2.1 类命名
- **实体类**: `User` (对应表 user), `Product` (对应表 product)
- **DTO**: `UserRegisterDTO`, `LoginDTO`
- **VO**: `UserVO`, `ProductVO`, `ResultVO`
- **Mapper**: `UserMapper`, `ProductMapper`
- **Service**: `UserService`, `ProductService`
- **Controller**: `UserController`, `ProductController`

### 2.2 方法命名
- 查询: `getXxx`, `findXxx`, `listXxx`
- 保存: `saveXxx`, `addXxx`
- 更新: `updateXxx`
- 删除: `deleteXxx`, `removeXxx`
- 统计: `countXxx`

### 2.3 变量命名
- 驼峰式命名，禁止使用中文拼音
- 布尔类型: `isDeleted`, `isActive`
- 列表: `userList`, `productList`
- 单个对象: `user`, `product`

## 3. 代码格式规范

### 3.1 缩进和空格
- 使用 4 空格缩进
- 运算符前后、逗号后加空格
- 左大括号不换行，右大括号换行

### 3.2 类文件结构顺序
1. 类注释
2. 包声明
3. import 语句（按字母顺序）
4. 类声明
5. 静态常量
6. 成员变量
7. 构造方法
8. 公有方法
9. 私有方法

### 3.3 方法规范
- 方法长度控制在 50 行以内
- 参数不超过 5 个
- 避免使用三元运算符嵌套
- 优先使用卫语句早期返回

## 4. Controller 层规范

### 4.1 返回格式
```java
@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public Result<UserVO> getUser(@PathVariable Long id) {
        // 业务逻辑
        return Result.success(userVO);
    }

    @PostMapping("/users")
    public Result<Void> addUser(@RequestBody @Valid UserRegisterDTO dto) {
        // 业务逻辑
        return Result.success();
    }
}
```

### 4.2 注意事项
- 所有接口添加 `@Valid` 参数校验
- 使用 `@RequestBody` 接收 JSON
- 路径变量使用 `@PathVariable`
- 查询参数使用 `@RequestParam`
- 文件上传使用 `MultipartFile`

## 5. Service 层规范

### 5.1 接口与实现分离
```java
public interface UserService {
    User getUserById(Long id);
    void register(UserRegisterDTO dto);
}

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    // 实现
}
```

### 5.2 事务管理
- 在 Service 层添加事务注解
- 默认 runtimeException 回滚
- 读方法不加事务，写方法根据需求添加

### 5.3 异常处理
- 使用自定义业务异常
- 捕获异常转换为业务异常抛出

## 6. DAO 层规范

### 6.1 Mapper 接口
```java
@Mapper
public interface UserMapper {
    User selectById(@Param("id") Long id);

    int insert(@Param("user") User user);

    int updateById(@Param("user") User user);

    int deleteById(@Param("id") Long id);
}
```

### 6.2 XML 文件
- 放在 `resources/mapper/` 目录
- 命名与 Mapper 接口对应: `UserMapper.xml`
- 使用 resultMap 映射实体
- 尽量使用 `#{}` 防止 SQL 注入

### 6.3 注意
- 不推荐使用 MyBatis-Plus 简化版（学习目的）
- 手动编写 SQL 更好理解原理

## 7. Model 层规范

### 7.1 实体类 (Entity)
```java
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

### 7.2 DTO 数据传输对象
- 用于接收请求参数
- 添加校验注解
- 不包含业务逻辑

### 7.3 VO 视图对象
- 用于返回给前端
- 避免暴露内部字段
- 可组合多个实体

### 7.4 统一返回结果
```java
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
```

## 8. 数据库表设计规范

### 8.1 表名规范
- 使用下划线命名
- 单词用复数形式: `users`, `products`, `orders`

### 8.2 字段规范
- id: 主键，使用 BIGINT + 自增
- create_time: 创建时间，DATETIME
- update_time: 更新时间，DATETIME
- is_deleted: 逻辑删除标志，TINYINT(1)

### 8.3 示例表结构
```sql
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 1-正常 0-禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

## 9. 配置规范

### 9.1 application.yml
```yaml
server:
  port: 8080

spring:
  application:
    name: shop-demo
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/shop_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: root

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.shop.demo.model.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

logging:
  level:
    com.shop.demo: debug
```

## 10. 注释规范

### 10.1 类注释
```java
/**
 * 用户 Service 实现类
 *
 * @author shop-demo
 * @since 2024-01-01
 */
```

### 10.2 方法注释
```java
/**
 * 根据ID查询用户
 *
 * @param id 用户ID
 * @return 用户信息
 */
public User getUserById(Long id) {
    // ...
}
```

### 10.3 重要逻辑注释
- 复杂业务逻辑必须添加注释
- 解释 Why 而不是 What

## 11. 异常处理规范

### 11.1 自定义业务异常
```java
public class BusinessException extends RuntimeException {
    private Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
```

### 11.2 全局异常处理
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        return Result.error("系统异常，请稍后重试");
    }
}
```

## 12. 参数校验规范

### 12.1 使用 Hibernate Validator
```java
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 20, message = "用户名长度3-20")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度6-20")
    private String password;

    @Email(message = "邮箱格式不正确")
    private String email;
}
```

## 13. 开发原则

1. **单一职责**: 每个类和方法只做一件事
2. **开闭原则**: 对扩展开放，对修改关闭
3. **DRY  Yourself
4.原则**: Don't Repeat **KISS 原则**: Keep It Simple, Stupid
5. **YAGNI 原则**: You Aren't Gonna Need It

## 14. Git 提交规范

```
feat: 添加用户注册功能
fix: 修复购物车数量计算错误
docs: 更新 README 文档
style: 格式化代码
refactor: 重构用户Service
test: 添加用户注册单元测试
chore: 更新依赖版本
```
