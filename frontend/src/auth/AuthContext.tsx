import { createContext, useContext, useMemo, useState } from 'react';
import type { PropsWithChildren } from 'react';
import { httpClient } from '../api/httpClient';
import type { LoginForm, TokenResponse } from '../types/auth';

interface AuthContextProps {
  token: string | null;
  username: string | null;
  roles: string[];
  permissions: string[];
  tenantIds: string[];
  activeTenantId: string | null;
  login: (form: LoginForm) => Promise<void>;
  switchTenant: (tenantId: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: PropsWithChildren) => {
  const [token, setToken] = useState<string | null>(localStorage.getItem('accessToken'));
  const [username, setUsername] = useState<string | null>(localStorage.getItem('username'));
  const [roles, setRoles] = useState<string[]>(JSON.parse(localStorage.getItem('roles') || '[]'));
  const [permissions, setPermissions] = useState<string[]>(JSON.parse(localStorage.getItem('permissions') || '[]'));
  const [tenantIds, setTenantIds] = useState<string[]>(JSON.parse(localStorage.getItem('tenantIds') || '[]'));
  const [activeTenantId, setActiveTenantId] = useState<string | null>(localStorage.getItem('activeTenantId'));

  const login = async (form: LoginForm) => {
    const { data } = await httpClient.post<TokenResponse>('/auth/login', form);
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('username', data.username);
    localStorage.setItem('roles', JSON.stringify(data.roles));
    localStorage.setItem('permissions', JSON.stringify(data.permissions));
    localStorage.setItem('tenantIds', JSON.stringify(data.tenantIds));
    localStorage.setItem('activeTenantId', data.tenantIds[0]);
    setToken(data.accessToken);
    setUsername(data.username);
    setRoles(data.roles);
    setPermissions(data.permissions);
    setTenantIds(data.tenantIds);
    setActiveTenantId(data.tenantIds[0]);
  };

  const switchTenant = (tenantId: string) => {
    localStorage.setItem('activeTenantId', tenantId);
    setActiveTenantId(tenantId);
  };

  const logout = () => {
    localStorage.clear();
    setToken(null);
    setUsername(null);
    setRoles([]);
    setPermissions([]);
    setTenantIds([]);
    setActiveTenantId(null);
  };

  const value = useMemo(
    () => ({ token, username, roles, permissions, tenantIds, activeTenantId, login, switchTenant, logout }),
    [token, username, roles, permissions, tenantIds, activeTenantId]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used inside AuthProvider');
  }
  return context;
};
