# Kitttus Spring Cloud + React 企业级脚手架

## 架构概览
- **gateway-service**：统一入口，路由转发、跨域与统一访问控制。
- **auth-service**：认证服务，负责登录与 JWT 签发。
- **user-service**：业务服务示例，提供 `/api/users/me` 受保护接口。
- **common-security**：安全共享组件，包含密码加密与 JWT 解析/签发。
- **frontend (React + TypeScript)**：前后端分离控制台，具备登录态、路由守卫、请求鉴权拦截器。

## 安全机制
1. `BCrypt(12)` 对密码进行强散列。
2. JWT 使用 HMAC-SHA 算法，带过期时间与角色声明。
3. 网关与业务服务双层校验，防止单点绕过。
4. 前端通过 Axios 拦截器统一注入 Bearer Token。
5. 仅开放最小接口面：`/api/auth/login` 与健康检查。

## 演进建议（商业化）
- 引入 Redis 管理会话黑名单与刷新令牌。
- 引入 Spring Authorization Server / Keycloak 实现 OIDC 与 SSO。
- 使用 Nacos/Consul + Config Server 实现服务治理与密钥外置。
- 接入审计日志、行为风控、MFA、多租户 RBAC。
