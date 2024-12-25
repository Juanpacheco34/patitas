import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },

  {
    path: 'home',
    loadComponent: () =>
      import('./components/pages/home/home.component').then(
        (c) => c.HomeComponent
      ),
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./components/auth/login/login.component').then(
        (c) => c.LoginComponent
      ),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./components/auth/register/register.component').then(
        (c) => c.RegisterComponent
      ),
  },
  {
    path: 'patitas',
    loadComponent: () =>
      import('./components/pages/patitas/patitas.component').then(
        (c) => c.PatitasComponent
      ),
  },
];
