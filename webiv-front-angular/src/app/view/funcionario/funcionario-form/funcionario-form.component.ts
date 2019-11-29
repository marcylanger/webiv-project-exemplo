import { Component, OnInit } from '@angular/core';
import { Funcionario } from 'src/app/model/funcionario';
import { CargoValues } from 'src/app/model/cargo';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FuncionarioService } from 'src/app/service/funcionario.service';
import { MessagesService } from 'src/app/service/messages.service';
import { Departamento } from 'src/app/model/departamento';
import { DepartamentoService } from 'src/app/service/departamento.service';
import { ParserToDateService } from 'src/app/service/parser-to-date.service';
import { DateAdapter } from '@angular/material/core';

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
   * Lista de cargos
   */
  private cargoValues: string[] = CargoValues;


  /**
   * List de departamentos
   */
  public departamentosList: Array<Departamento> = [];

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
    private messageService: MessagesService,
    private departamentoService: DepartamentoService,
    private parserToDate: ParserToDateService,
    private _adapter: DateAdapter<any>) { }

  /**
   * Método chamado ao iniciar a classe
   */
  ngOnInit() {


    this.funcionario = new Funcionario(null, null, null, null, null, null, null, null, null, null, null);
    this.createForm();
    this.listarDepartamentos("");
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
        nome: [null, { validators: [Validators.required, Validators.maxLength(144)], updateOn: 'blur' }],
        cargo: [null, { validators: [Validators.required] }],
        cpf: [null, { validators: [Validators.required, Validators.maxLength(11)], updateOn: 'blur' }],
        departamento: [null, { validators: [Validators.required], updateOn: 'select' }],
        dataNascimento: [null, { validators: [Validators.required], updateOn: 'blur' }],
      }
      
      
    );

    /**
     * Seta o locale da data para usar padrão brasileiro
     */
    this._adapter.setLocale('pt');

  }


  /**
   * Método para salvar o funcionario
   */
  onSave() {
    if (this.funcionarioForm.valid) {

      this.funcionario.nome = this.funcionarioForm.get("nome").value;
      var dep : Departamento = this.funcionarioForm.get("departamento").value;
      this.funcionario.departamento = dep;
      this.funcionario.cargo = this.funcionarioForm.get("cargo").value;
      this.funcionario.cpf = this.funcionarioForm.get("cpf").value;
      this.funcionario.dataNascimento = this.funcionarioForm.get("dataNascimento").value;
      console.log(this.funcionario);
      /**
       * Verifica se é cadastro ou edição
       */
      if (this.funcionario.id == null) {
        this.funcionarioService.cadastrar(this.funcionario).subscribe(res => {
          this.funcionario = res;
          this.messageService.toastSuccess('Funcionário cadastrado com sucesso.');
          this.onBack();
        },
          (error: any) => {
            this.messageService.toastError(error.error.message);
          });
      }
      else {
        this.funcionarioService.editar(this.funcionario).subscribe(res => {
          this.funcionario = res;
          this.isOnUpdate = true;
          this.messageService.toastSuccess('Funcionário atualizado com sucesso.');
          this.onBack();
        },
        (error: any) => {
          this.messageService.toastError(error.error.message);
        });
      }

    } else {
      this.messageService.toastWarnning('Preencha todos os campos obrigatórios antes de salvar.');

    }
  }



  /**
   * Método para popular o formulário com os dados do funcionário em edição
   */
  loadToEdit() {
    this.funcionarioService.detalhar(this.funcionario.id).subscribe(res => {
      this.funcionarioForm.get("nome").setValue(res.nome);
      this.funcionarioForm.get("cargo").setValue(res.cargo);
      this.funcionarioForm.get("cpf").setValue(res.cpf);
      this.funcionarioForm.get("departamento").setValue(res.departamento);
      this.funcionarioForm.get("dataNascimento").setValue(res.dataNascimento);
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
    console.log(this.funcionarioForm.get("departamento").value);
    if (!this.isOnUpdate) {
      this.router.navigate(['../'], { relativeTo: this.activatedRoute });
    } else {
      this.router.navigate(['../../'], { relativeTo: this.activatedRoute });
    }

  }


  /**
   * Display de departamento
   */
  displayDepartamento(departamento?: Departamento): string | undefined {
    return departamento ? departamento.descricao : undefined;
  }

  listarDepartamentos(filter: string) {
    this.departamentoService.listar().subscribe(dados => {
      this.departamentosList = dados;
    },
      (error: any) => {
        this.messageService.toastError(error.error.message);
      });
  }


  selectDepartamento(event: any) {
    this.funcionarioForm.get("departamento").setValue(event.option.value);
  }

}

