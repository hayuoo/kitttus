import axios from 'axios';

export const httpClient = axios.create({
  baseURL: '/api',
  timeout: 10000
});

httpClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  const activeTenantId = localStorage.getItem('activeTenantId');

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  if (activeTenantId) {
    config.headers['X-Tenant-Id'] = activeTenantId;
  }
  return config;
});
