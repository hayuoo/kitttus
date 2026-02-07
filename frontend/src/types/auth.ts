export interface TokenResponse {
  accessToken: string;
  tokenType: string;
  username: string;
  roles: string[];
}

export interface LoginForm {
  username: string;
  password: string;
}
