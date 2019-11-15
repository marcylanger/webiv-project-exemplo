import { Time } from '@angular/common'
import { Cargo } from './cargo'
import { Departamento } from './departamento';

export class Funcionario {
    constructor(
        public id: number,
        public nome: string,
        public salario: number, 
        public cpf: string,
        public horaEntrada: Time,
        public horaSaida: Time,
        public dataDemissao: Date,
        public dataNascimento: Date,
        public cargo: Cargo,
        public departamento: Departamento,
        public idade: number
       ) {  }
    
}
