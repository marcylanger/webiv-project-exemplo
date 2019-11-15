import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const API_URL = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }


  public login( email: string, senha: string): Promise<any>
    {
    let body = new URLSearchParams();
    body.set('email', email);
    body.set('password', senha);

    return this.http.post(API_URL+"/authenticate", body).toPromise();
  }
}
