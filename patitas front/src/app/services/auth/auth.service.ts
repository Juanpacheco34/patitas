import { HttpClient } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable } from 'rxjs';
import { PatitaUser } from '../../interfaces/patita-user';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  http = inject(HttpClient);
  platformId = inject(PLATFORM_ID);
  private url: string = 'http://localhost:8080';

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    const headers = {
      'Content-Type': 'application/json',
    };

    return this.http.post(`${this.url}/login`, body, { headers });
  }
  register(data: PatitaUser): Observable<PatitaUser> {
    return this.http.post<PatitaUser>(`${this.url}/register`, data);
  }

  isLogged() {
    if (isPlatformBrowser(this.platformId)) return localStorage.getItem('token') ? true : false;
    return false;
  }
}
