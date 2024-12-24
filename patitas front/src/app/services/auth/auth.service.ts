import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private url: string = 'http://localhost:8080';

  constructor(private hc: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const body = { username, password };
    return this.hc.post(`${this.url}/login`, body);
  }
  register(data: Object): Observable<any> {
    console.log(data)
    return this.hc.post(`${this.url}/register`, data, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }
}
