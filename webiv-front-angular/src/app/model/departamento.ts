import { Funcionario } from './funcionario'

export class Departamento {
    constructor(
       public id: number,
       public nome: string,
       public descricao: string,
       public funcionarios: Funcionario[]
      ) {  }
    
}
