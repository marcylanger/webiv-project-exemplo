import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Departamento } from 'src/app/model/departamento';
import { TipoAcaoValues } from 'src/app/model/tipo-acao';

@Component({
  selector: 'app-departamento-list',
  templateUrl: './departamento-list.component.html',
  styleUrls: ['./departamento-list.component.css']
})
export class DepartamentoListComponent implements OnInit {

  @Input() departamentos : Departamento[];

  @Output() selecionarDepartamento = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  selecionar(id: any, acao: number) {
    console.log(acao);
    this.selecionarDepartamento.emit({departamentoSelecionadoId : id, acaoRealizada : TipoAcaoValues[acao]})
  }
}