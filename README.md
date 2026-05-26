# 基础登录服务 (human_manage_basic)

Spring Boot 4 + Sa-Token 构建的基础登录认证服务，提供用户注册、登录、登出、密码管理等 RESTful API，可作为任何前后端分离项目的认证基础模块。

## 技术栈

| 组件 | 版本 |
|---|---|
| Java | 21+ |
| Spring Boot | 4.0.5 |
| MyBatis-Plus | 3.5.16 |
| Sa-Token | 1.45.0 |
| MySQL | 8.0+ |
| MinIO | 最新版 |
| SpringDoc OpenAPI | 2.8.0 |

## 功能特性

- 用户注册、登录、登出
- Argon2 密码加密
- Sa-Token 无状态 Token 认证
- 修改密码、重置密码
- 账号禁用检查
- 登录失败次数限制（5 次锁定 30 分钟）
- 参数校验（Jakarta Validation）
- Swagger API 文档
- 全局异常处理
- 文件上传（MinIO）

## 快速开始

### 1. 环境要求

- JDK 21+
- Maven 3.9+
- MySQL 8.0+
- MinIO（可选，用于文件上传）

### 2. 数据库初始化

执行 `doc/sql/schema.sql` 创建表结构。

### 3. 配置环境变量

```bash
# 数据库（必填）
DB_PASSWORD=你的数据库密码

# 可选覆盖
DB_USERNAME=human_manage_basic
DB_HOST=localhost
DB_PORT=3306
DB_NAME=human_manage_basic
SERVER_PORT=19500

# MinIO（如需文件上传功能）
MINIO_URL=http://localhost:9000
MINIO_USERNAME=admin
MINIO_PASSWORD=admin123456
MINIO_BUCKET=human-manage
```

### 4. 启动服务

```bash
cd human_manage_basic
mvn clean package -DskipTests
java -jar business/target/business-*.jar
```

服务启动后访问：
- API 基础路径：`http://localhost:19500/api/basic`
- Swagger UI：`http://localhost:19500/api/basic/swagger-ui/index.html`

## API 接口

| 方法 | 路径 | 说明 | 认证 |
|---|---|---|---|
| POST | `/sys/user/add` | 用户注册 | 否 |
| POST | `/sys/user/login` | 用户登录 | 否 |
| POST | `/sys/user/logout` | 用户登出 | 是 |
| GET | `/sys/user/info` | 获取当前用户信息 | 是 |
| POST | `/sys/user/change-password` | 修改密码 | 是 |
| POST | `/sys/user/reset-password` | 重置密码（管理员） | 是 |

### 登录请求示例

```json
POST /api/basic/sys/user/login
Content-Type: application/json

{
    "username": "admin",
    "password": "123456"
}
```

### 登录响应示例

```json
{
    "code": 200,
    "msg": "登录成功",
    "data": {
        "token": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
        "tokenName": "saToken",
        "userId": 1,
        "username": "admin",
        "phone": "13800138000",
        "email": "admin@example.com",
        "headPhoto": null
    }
}
```

### 认证方式

登录后，在请求头中携带 Token：

```
saToken: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
```

## 项目结构

```
human_manage_basic/
├── common/                   # 公共模块（响应封装、分页工具）
├── business/                 # 业务模块（用户、员工管理）
│   └── src/main/java/com/human/business/
│       ├── config/           # 配置（Sa-Token、Swagger、MyBatis-Plus）
│       ├── controller/       # 控制器
│       ├── service/          # 服务层
│       ├── mapper/           # 数据访问层
│       ├── domain/           # 实体、DTO、VO
│       └── util/             # 工具类（密码加密）
└── facade/                   # 对外接口模块（文件上传）
```

## License

MIT