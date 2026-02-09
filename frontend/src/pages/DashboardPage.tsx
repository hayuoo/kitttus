import { Link } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';

export const DashboardPage = () => {
  const { username, roles, permissions, tenantIds, activeTenantId, switchTenant, logout } = useAuth();

  return (
    <div className="container">
      <div className="card">
        <div className="row">
          <h1>欢迎，{username}</h1>
          <button className="btn" onClick={logout}>退出登录</button>
        </div>
        <p>当前角色：{roles.join(', ')}</p>
        <p>权限集：{permissions.join(', ')}</p>
        <label>当前租户：</label>
        <select className="input" value={activeTenantId ?? ''} onChange={(e) => switchTenant(e.target.value)}>
          {tenantIds.map((tenantId) => (
            <option key={tenantId} value={tenantId}>{tenantId}</option>
          ))}
        </select>
        <p>业务入口：</p>
        <Link to="/supplychain" className="btn" style={{ textDecoration: 'none', display: 'inline-block' }}>
          进入会展供应链中台
        </Link>
      </div>
    </div>
  );
};
