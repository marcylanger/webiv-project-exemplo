import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Funcionario } from 'src/app/model/funcionario';


@Component({
  selector: 'app-funcionario-list',
  templateUrl: './funcionario-list.component.html',
  styleUrls: ['./funcionario-list.component.css']
})
export class FuncionarioListComponent implements OnInit {

  @Input() funcionarios : Funcionario[];

  @Output() selecionarFuncionario = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  selecionar(id: any, toEdit: boolean) {
    this.selecionarFuncionario.emit({funcionarioSelecionadoId : id, toEdit : toEdit})

  }
}
