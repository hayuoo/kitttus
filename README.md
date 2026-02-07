# Kitttus Enterprise Scaffold

这是一个**前后端分离**的企业级脚手架：后端基于 **Spring Cloud**，前端基于 **React + TypeScript**。

## 项目结构
- `backend/`：Spring Cloud 多模块项目（网关、认证、用户、安全公共模块）
- `frontend/`：React 管理控制台
- `docs/architecture.md`：架构与安全策略说明

## 快速开始
### 后端
```bash
cd backend
mvn clean package
```

启动顺序：
1. `gateway-service` (8080)
2. `auth-service` (8081)
3. `user-service` (8082)

### 前端
```bash
cd frontend
npm install
npm run dev
```

默认测试账号：
- username: `admin`
- password: `ChangeMe123!`

> 登录后，前端通过网关访问 `/api/users/me` 验证鉴权链路。
