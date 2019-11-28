import { Component, OnInit } from '@angular/core';
import { Funcionario } from 'src/app/model/funcionario';
import { FuncionarioService } from 'src/app/service/funcionario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MessagesService } from 'src/app/service/messages.service';
import * as moment from 'moment';
import { ParserToDateService } from 'src/app/service/parser-to-date.service';

@Component({
  selector: 'app-funcionario-detail',
  templateUrl: './funcionario-detail.component.html',
  styleUrls: ['./funcionario-detail.component.css']
})
export class FuncionarioDetailComponent implements OnInit {

  /**
   * Objeto funcionário
   */
  public funcionario: Funcionario;

  constructor(private funcionarioService: FuncionarioService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private messageService: MessagesService,
    private parserToDate: ParserToDateService) { }

  ngOnInit() {
    this.funcionario = new Funcionario(null, null, null, null, null, null, null, null, null, null, null);
    this.funcionario.id = this.activatedRoute.snapshot.params['id'];
    if (this.funcionario.id) {
      this.loadDados();
    }
    
  }

  /**
   * Método para popular os campos com os dados do funcionário em visualização
   */
  loadDados(){
    this.funcionarioService.detalhar(this.funcionario.id).subscribe(res => {
      this.funcionario = new Funcionario(res.id, res.nome, res.salario, res.cpf, res.horaEntrada, res.horaSaida, res.dataDemissao, res.dataNascimento, res.cargo, res.departamento, res.idade);
      
      this.funcionario.dataNascimento = this.parserToDate.parser(this.funcionario.dataNascimento);
      this.funcionario.dataDemissao = this.parserToDate.parser(this.funcionario.dataDemissao);
    },
    (error: any) => {
      this.messageService.toastError(error.error.message);
    }
    );
    
  }

  /**
   * Método para voltar a pagina de list de funcionarios
   */
  onBack() {

    this.router.navigate(['../../'], { relativeTo: this.activatedRoute });
    
  }

  /**
   * Método que redireciona para alterar funcionario
   */
  navigateToEdit() {
    this.router.navigate(['../../alterar/'+this.funcionario.id], { relativeTo: this.activatedRoute });
  }

}
