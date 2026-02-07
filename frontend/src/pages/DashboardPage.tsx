import { useEffect, useState } from 'react';
import { useAuth } from '../auth/AuthContext';
import { httpClient } from '../api/httpClient';

export const DashboardPage = () => {
  const { username, roles, logout } = useAuth();
  const [profile, setProfile] = useState<{ username: string; status: string } | null>(null);

  useEffect(() => {
    httpClient.get('/users/me').then(({ data }) => setProfile(data));
  }, []);

  return (
    <div className="container">
      <div className="card">
        <div className="row">
          <h1>欢迎，{username}</h1>
          <button className="btn" onClick={logout}>退出登录</button>
        </div>
        <p>当前角色：{roles.join(', ')}</p>
        <p>网关鉴权状态：{profile ? `在线(${profile.status})` : '加载中...'}</p>
      </div>
    </div>
  );
};
