import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Departamento} from './../model/departamento'

@Injectable({
  providedIn: 'root'
})
export class DepartamentoService {

  constructor(private http: HttpClient) { }

  listar(){
    return this.http.get<Departamento[]>('http://localhost:4200/api/api/departamento/list/');
  }

  detalhar(departamentoId: number){
    return this.http.get<Departamento>('http://localhost:4200/api/api/departamento/find?id='+departamentoId);
  }

  cadastrar(departamento: Departamento){
    return this.http.post<Departamento>('http://localhost:4200/api/api/departamento/insert/', departamento);
  }

  editar(departamento: Departamento){
    return this.http.post<Departamento>('http://localhost:4200/api/api/departamento/update/', departamento);
  }

  remover(departamentoId: number){
    return this.http.get('http://localhost:4200/api/api/departamento/remove?id='+departamentoId);

  }
}
