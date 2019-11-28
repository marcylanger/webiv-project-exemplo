import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { Funcionario } from 'src/app/model/funcionario';
import { Router, ActivatedRoute } from '@angular/router';
import { FuncionarioService } from 'src/app/service/funcionario.service';
import { TipoAcaoValues } from 'src/app/model/tipo-acao';
import { MessagesService } from 'src/app/service/messages.service';
import { TdDialogService } from '@covalent/core/dialogs';

@Component({
  selector: 'app-funcionario-search',
  templateUrl: './funcionario-search.component.html',
  styleUrls: ['./funcionario-search.component.css']
})
export class FuncionarioSearchComponent implements OnInit {

  /**
   * Lista de funcionários a ser exibida
   */
  funcionarios : Array<Funcionario>;

  /**
   * Construtor da classe
   * @param router 
   * @param activatedRoute 
   * @param funcionarioService 
   */
  constructor(private router: Router,
            private activatedRoute: ActivatedRoute,
            private funcionarioService: FuncionarioService,
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
   * Método que redireciona para cadastrar funcionário
   */
  navigateToNovo() {
    this.router.navigate(['cadastrar'], { relativeTo: this.activatedRoute });

  }

  /**
   * Método que redireciona para alterar ou visualizar funcionário
   * @param evento 
   */
  navigateTo(evento) {
    console.log(evento.acaoRealizada);
    let id: number  = evento.funcionarioSelecionadoId;
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
   * Método para listar os funcionarios
   */
  listar(){
    this.funcionarioService.listar().subscribe(dados => {
      this.funcionarios = dados;
    },
    (error: any) => {
      this.messageService.toastError(error.error.message);
    });
  }
    
  /**
   * Método para remover um funcionario
   */
  remover(id: number){
    this.openRemoverConfirm(id);
  }

  openRemoverConfirm(id: number): void {
    this._dialogService.openConfirm({
      message: 'Tem certeza que deseja excluir esse funcionário?',
      disableClose: true, // defaults to false
      viewContainerRef: this._viewContainerRef, //OPTIONAL
      title: 'Excluir funcionário', //OPTIONAL, hides if not provided
      cancelButton: 'Não', //OPTIONAL, defaults to 'CANCEL'
      acceptButton: 'Sim', //OPTIONAL, defaults to 'ACCEPT'
      width: '500px', //OPTIONAL, defaults to 400px
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        this.funcionarioService.remover(id).subscribe(dados => {
          this.messageService.toastSuccess('Funcionário excluído com sucesso.');
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