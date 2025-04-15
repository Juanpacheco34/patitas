import { Routes } from '@angular/router';
import { authenticatedRoutes, unauthenticatedRoutes } from './guards/path.guard';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () =>
      import('./pages/home/home.component').then((c) => c.HomeComponent),
    canActivate: [unauthenticatedRoutes],
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/auth/login/login.component').then(
        (c) => c.LoginComponent
      ),
    canActivate: [unauthenticatedRoutes],
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./components/auth/register/register.component').then(
        (c) => c.RegisterComponent
      ),
    canActivate: [unauthenticatedRoutes],
  },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then(
        (c) => c.DashboardComponent
      ),
    canActivate: [authenticatedRoutes],
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '/home',
  },
];
