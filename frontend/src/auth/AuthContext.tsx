import { createContext, useContext, useMemo, useState } from 'react';
import type { PropsWithChildren } from 'react';
import { httpClient } from '../api/httpClient';
import type { LoginForm, TokenResponse } from '../types/auth';

interface AuthContextProps {
  token: string | null;
  username: string | null;
  roles: string[];
  login: (form: LoginForm) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: PropsWithChildren) => {
  const [token, setToken] = useState<string | null>(localStorage.getItem('accessToken'));
  const [username, setUsername] = useState<string | null>(localStorage.getItem('username'));
  const [roles, setRoles] = useState<string[]>(JSON.parse(localStorage.getItem('roles') || '[]'));

  const login = async (form: LoginForm) => {
    const { data } = await httpClient.post<TokenResponse>('/auth/login', form);
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('username', data.username);
    localStorage.setItem('roles', JSON.stringify(data.roles));
    setToken(data.accessToken);
    setUsername(data.username);
    setRoles(data.roles);
  };

  const logout = () => {
    localStorage.clear();
    setToken(null);
    setUsername(null);
    setRoles([]);
  };

  const value = useMemo(() => ({ token, username, roles, login, logout }), [token, username, roles]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used inside AuthProvider');
  }
  return context;
};
