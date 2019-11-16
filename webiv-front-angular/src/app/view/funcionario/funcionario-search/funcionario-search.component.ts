import { Component, OnInit } from '@angular/core';
import { Funcionario } from 'src/app/model/funcionario';
import { Router, ActivatedRoute } from '@angular/router';
import { FuncionarioService } from 'src/app/service/funcionario.service';
import { MessagesService } from 'src/app/service/messages.service';

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
            private messageService: MessagesService) { 
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
    if(evento.toEdit){
      this.router.navigate(['alterar/'+evento.funcionarioSelecionadoId], { relativeTo: this.activatedRoute });
    }
    else{
      this.router.navigate(['detalhes/'+evento.funcionarioSelecionadoId], { relativeTo: this.activatedRoute });
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
    

}