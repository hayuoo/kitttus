# Kitttus Expo Supply Chain Platform

企业级前后端分离项目：基于 Spring Cloud + React 构建 **智能会展供应链中台**。

## 关键特性
- 多租户：主办方 / 供应商 / 参展商
- 闭环链路：订单 + 合同 + 验收 + 结算
- 风险控制：超预算预警、延迟预警
- 强 RBAC + 审批流入口

## 项目结构
- `backend/`：Spring Cloud 多模块
  - `auth-service`
  - `gateway-service`
  - `user-service`
  - `supplychain-service`
  - `common-security`
- `frontend/`：React 管理控制台
- `docs/architecture.md`：架构与安全策略

## 快速开始
### 后端
```bash
cd backend
mvn clean package
```
启动建议顺序：
1. `auth-service` (8081)
2. `user-service` (8082)
3. `supplychain-service` (8083)
4. `gateway-service` (8080)

### 前端
```bash
cd frontend
npm install
npm run dev
```

## 示例账号
- 主办方审批人：`organizer.admin / ChangeMe123!`
- 供应商经理：`supplier.pm / ChangeMe123!`
- 参展商负责人：`exhibitor.owner / ChangeMe123!`

默认租户：`expo-org-001`
