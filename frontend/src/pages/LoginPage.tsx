import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';

export const LoginPage = () => {
  const { login } = useAuth();
  const [username, setUsername] = useState('admin');
  const [password, setPassword] = useState('ChangeMe123!');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const onSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setError('');
    try {
      await login({ username, password });
      navigate('/');
    } catch {
      setError('登录失败，请检查账号密码或联系管理员。');
    }
  };

  return (
    <div className="container">
      <div className="card" style={{ maxWidth: 420, margin: '0 auto' }}>
        <h1>Kitttus 企业控制台</h1>
        <p>安全认证入口（JWT + 网关鉴权）</p>
        <form onSubmit={onSubmit}>
          <input className="input" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="用户名" />
          <input className="input" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="密码" />
          {error && <p className="error">{error}</p>}
          <button className="btn" type="submit">登录</button>
        </form>
      </div>
    </div>
  );
};
