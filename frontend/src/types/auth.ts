export interface TokenResponse {
  accessToken: string;
  tokenType: string;
  username: string;
  roles: string[];
  permissions: string[];
  tenantIds: string[];
}

export interface LoginForm {
  username: string;
  password: string;
}
