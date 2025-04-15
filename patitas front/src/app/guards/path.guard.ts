import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

export const authenticatedRoutes: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLogged()) {
    return router.createUrlTree(['/login']);
  }

  return true;
};

export const unauthenticatedRoutes: CanActivateFn = (route, state) => { 
  const authService = inject(AuthService);
  const router = inject(Router);

  if(authService.isLogged()) return router.createUrlTree(['/dashboard']);

  return true;
}
