import { Component, OnInit } from '@angular/core';
import { Funcionario } from 'src/app/model/funcionario';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FuncionarioService } from 'src/app/service/funcionario.service';
import { MessagesService } from 'src/app/service/messages.service';

@Component({
  selector: 'app-funcionario-form',
  templateUrl: './funcionario-form.component.html',
  styleUrls: ['./funcionario-form.component.css']
})
export class FuncionarioFormComponent implements OnInit {
/**
   * Funcionario Form
   */
  public funcionarioForm: FormGroup;

  /**
   * Objeto funcionario
   */
  public funcionario: Funcionario;
  
  /**
  * Controla se é atualização
  */
  private isOnUpdate: boolean = false;

  /**
   * Construtor da classe
   * @param fb 
   * @param activatedRoute 
   * @param router 
   * @param departamentoService 
   */
  constructor(private fb: FormBuilder, 
    private activatedRoute: ActivatedRoute,
    private router: Router, 
    private funcionarioService: FuncionarioService,
    private messageService: MessagesService) { }

  /**
   * Método chamado ao iniciar a classe
   */
  ngOnInit() {

    this.funcionario = new Funcionario(null, null, null, null, null, null, null, null, null, null, null);
    this.createForm();
    this.funcionario.id = this.activatedRoute.snapshot.params['id'];
    if (this.funcionario.id) {
      this.loadToEdit();
    }
    
  }

  /**
   * Método para criar o formulário
   */
  public createForm() {
    /**
     * Instancia uma classe FormGroup
     */
    this.funcionarioForm = this.fb.group(
      {
        nome: [null, {validators: [Validators.required, Validators.maxLength(144)], updateOn: 'blur'}],
        departamento: [null, {validators: [Validators.required], updateOn: 'select'}],
      }
    );
  }


  /**
   * Método para salvar o funcionario
   */
  onSave() {
    if (this.funcionarioForm.valid) {

      this.funcionario.nome = this.funcionarioForm.get("nome").value;
      this.funcionario.departamento = this.funcionarioForm.get("departamento").value;

      /**
       * Verifica se é cadastro ou edição
       */
      if(this.funcionario.id == null){
        this.funcionarioService.cadastrar(this.funcionario).subscribe(res => {
          this.funcionario = res;
          this.messageService.toastSuccess('Funcionário cadastrado com sucesso.');
          this.onBack();
        },
          (error: any) => {
            this.messageService.toastError(error.error.message);
          });
      }
      else{
        this.funcionarioService.editar(this.funcionario).subscribe(res => {
          this.funcionario = res;
          this.isOnUpdate = true;
          this.messageService.toastSuccess('Funcionário atualizado com sucesso.');
          this.onBack();
        },
          (error: any) => alert(error)
          );
      }

    } else {
      this.messageService.toastWarnning('Preencha todos os campos obrigatórios antes de salvar.');
      
    }
  }



  /**
   * Método para popular o formulário com os dados do funcionário em edição
   */
  loadToEdit(){
    this.funcionarioService.detalhar(this.funcionario.id).subscribe(res => {
      this.funcionarioForm.get("nome").setValue(res.nome);
      this.funcionarioForm.get("departamento").setValue(res.departamento);
      this.isOnUpdate = true;
    },
    (error: any) => {
      this.messageService.toastError(error.error.message);
    });
    
  }

  /**
   * Método para voltar a pagina de list de funcionarios
   */
  onBack() {
    if(!this.isOnUpdate){
      this.router.navigate(['../'], { relativeTo: this.activatedRoute });
    }else{
      this.router.navigate(['../../'], { relativeTo: this.activatedRoute });
    }
    
  }

}

