import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Departamento } from 'src/app/model/departamento';
import { Router, ActivatedRoute } from '@angular/router';
import { DepartamentoService } from 'src/app/service/departamento.service';
import { TipoAcaoValues } from 'src/app/model/tipo-acao';
import { MessagesService } from 'src/app/service/messages.service';
import { TdDialogService } from '@covalent/core/dialogs';

@Component({
  selector: 'app-departamento-search',
  templateUrl: './departamento-search.component.html',
  styleUrls: ['./departamento-search.component.css']
})
export class DepartamentoSearchComponent implements OnInit {

   /**
   * Lista de departamentos a ser exibida
   */
  departamentos : Array<Departamento>;

  /**
   * Construtor da classe
   * @param router 
   * @param activatedRoute 
   * @param departamentoService 
   */
  constructor(private router: Router,
            private activatedRoute: ActivatedRoute,
            private departamentoService: DepartamentoService,
            private messageService: MessagesService,
            private _dialogService: TdDialogService,
            private _viewContainerRef: ViewContainerRef) { 
  }

  /**
   * Método que é executado ao carregar a classe
   */
  ngOnInit() {
    this.listar();
  }

  /**
   * Método que redireciona para cadastrar departamento
   */
  navigateToNovo() {
    this.router.navigate(['cadastrar'], { relativeTo: this.activatedRoute });

  }

  /**
   * Método que redireciona para alterar, excluir ou visualizar departamento
   * @param evento 
   */
  navigateTo(evento) {
    console.log(evento.acaoRealizada);
    let id: number  = evento.departamentoSelecionadoId;
    if(evento.acaoRealizada == TipoAcaoValues[0]){
      this.router.navigate(['detalhes/'+id], { relativeTo: this.activatedRoute });
    }
    else if(evento.acaoRealizada == TipoAcaoValues[1]){
      this.router.navigate(['alterar/'+id], { relativeTo: this.activatedRoute });
    
    } else if(evento.acaoRealizada == TipoAcaoValues[2]){
      this.remover(id);
    }
    
  }

  /**
   * Método para listar os departamentos
   */
  listar(){
    this.departamentoService.listar().subscribe(dados => {
      this.departamentos = dados;
    },
    (error: any) => {
      console.log(error);
      console.log(error.error.message);
      this.messageService.toastError(error.error.message);
    });
  }
    
  /**
   * Método para remover um departamento
   */
  remover(id: number){
    this.openRemoverConfirm(id);
  }

  openRemoverConfirm(id: number): void {
    this._dialogService.openConfirm({
      message: 'Tem certeza que deseja excluir esse departamento?',
      disableClose: true, // defaults to false
      viewContainerRef: this._viewContainerRef, //OPTIONAL
      title: 'Excluir departamento', //OPTIONAL, hides if not provided
      cancelButton: 'Não', //OPTIONAL, defaults to 'CANCEL'
      acceptButton: 'Sim', //OPTIONAL, defaults to 'ACCEPT'
      width: '500px', //OPTIONAL, defaults to 400px
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        this.departamentoService.remover(id).subscribe(dados => {
          this.messageService.toastSuccess('Departamento excluído com sucesso.');
          this.listar();
        },
        (error: any) => {
          console.log(error.error.message);
          this.messageService.toastError(error.error.message);
          
        });
      } else {
        // DO SOMETHING ELSE
      }
    });
  }
}