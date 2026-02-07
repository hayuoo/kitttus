import { Navigate, Route, Routes } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';
import { LoginPage } from '../pages/LoginPage';
import { DashboardPage } from '../pages/DashboardPage';
import { SupplychainPage } from '../pages/SupplychainPage';

const PrivateRoute = ({ children }: { children: JSX.Element }) => {
  const { token } = useAuth();
  return token ? children : <Navigate to="/login" replace />;
};

export const AppRoutes = () => (
  <Routes>
    <Route path="/login" element={<LoginPage />} />
    <Route path="/" element={<PrivateRoute><DashboardPage /></PrivateRoute>} />
    <Route path="/supplychain" element={<PrivateRoute><SupplychainPage /></PrivateRoute>} />
  </Routes>
);
