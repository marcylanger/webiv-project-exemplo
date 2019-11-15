import { Injectable } from '@angular/core';
import { Funcionario } from '../model/funcionario';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {


  constructor(private http: HttpClient) { }

  listar(){
    return this.http.get<Funcionario[]>('http://localhost:4200/api/api/funcionario/list/');
  }

  detalhar(funcionarioId: number){
    return this.http.get<Funcionario>('http://localhost:4200/api/api/funcionario/find?id='+funcionarioId);

  }

  cadastrar(funcionario: Funcionario){
    return this.http.post<Funcionario>('http://localhost:4200/api/api/funcionario/insert/', funcionario);
  }

  editar(funcionario: Funcionario){
    return this.http.post<Funcionario>('http://localhost:4200/api/api/funcionario/update/', funcionario);
  }

  remover(funcionarioId: number){
    return this.http.get('http://localhost:4200/api/api/funcionario/remove?id='+funcionarioId);

  }
}
