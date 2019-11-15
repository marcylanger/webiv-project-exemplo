import { Component, OnInit } from '@angular/core';
import { Departamento } from 'src/app/model/departamento';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DepartamentoService } from 'src/app/service/departamento.service';

@Component({
  selector: 'app-departamento-form',
  templateUrl: './departamento-form.component.html',
  styleUrls: ['./departamento-form.component.css']
})
export class DepartamentoFormComponent implements OnInit {
/**
   * Departamento Form
   */
  public departamentoForm: FormGroup;

  /**
   * Objeto departamento
   */
  public departamento: Departamento;
  
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
    private departamentoService: DepartamentoService) { }

  /**
   * Método chamado ao iniciar a classe
   */
  ngOnInit() {

    this.departamento = new Departamento(null, null, null, null);
    this.createForm();
    this.departamento.id = this.activatedRoute.snapshot.params['id'];
    if (this.departamento.id) {
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
    this.departamentoForm = this.fb.group(
      {
        nome: [null, {validators: [Validators.required, Validators.maxLength(144)], updateOn: 'blur'}],
        descricao: [null],
      }
    );
  }


  /**
   * Método para salvar o departamento
   */
  onSave() {
    if (this.departamentoForm.valid) {

      this.departamento.nome = this.departamentoForm.get("nome").value;
      this.departamento.descricao = this.departamentoForm.get("descricao").value;

      /**
       * Verifica se é cadastro ou edição
       */
      if(this.departamento.id == null){
        this.departamentoService.cadastrar(this.departamento).subscribe(res => {
          this.departamento = res;
          this.onBack();
        },
          (error: any) => alert(error)
          );
      }
      else{
        this.departamentoService.editar(this.departamento).subscribe(res => {
          this.departamento = res;
          this.isOnUpdate = true;
          this.onBack();
        },
          (error: any) => alert(error)
          );
      }

    } else {
      alert("Deu ruim");
      
    }
  }



  /**
   * Método para popular o formulário com os dados do departamento em edição
   */
  loadToEdit(){
    this.departamentoService.detalhar(this.departamento.id).subscribe(res => {
      this.departamentoForm.get("nome").setValue(res.nome);
      this.departamentoForm.get("descricao").setValue(res.descricao);
      this.isOnUpdate = true;
    },
    (error: any) => alert(error)
    );
    
  }

  /**
   * Método para voltar a pagina de list de departamentos
   */
  onBack() {
    if(!this.isOnUpdate){
      this.router.navigate(['../'], { relativeTo: this.activatedRoute });
    }else{
      this.router.navigate(['../../'], { relativeTo: this.activatedRoute });
    }
    
  }

}
