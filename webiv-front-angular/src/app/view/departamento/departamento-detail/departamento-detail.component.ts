import { Component, OnInit } from '@angular/core';
import { Departamento } from 'src/app/model/departamento';
import { DepartamentoService } from 'src/app/service/departamento.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-departamento-detail',
  templateUrl: './departamento-detail.component.html',
  styleUrls: ['./departamento-detail.component.css']
})
export class DepartamentoDetailComponent implements OnInit {

   /**
   * Objeto departamento
   */
  public departamento: Departamento;

  constructor(private departamentoService: DepartamentoService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    this.departamento = new Departamento(null, null, null, null);
    this.departamento.id = this.activatedRoute.snapshot.params['id'];
    if (this.departamento.id) {
      this.loadDados();
    }
    
  }

  /**
   * Método para popular os campos com os dados do departamento em visualização
   */
  loadDados(){
    this.departamentoService.detalhar(this.departamento.id).subscribe(res => {
      this.departamento = new Departamento(res.id, res.nome, res.descricao, []);
    },
    (error: any) => alert(error)
    );
    
  }

  /**
   * Método para voltar a pagina de list de departamentos
   */
  onBack() {

    this.router.navigate(['../../'], { relativeTo: this.activatedRoute });
    
  }

  /**
   * Método que redireciona para alterar departamento
   */
  navigateToEdit() {
    this.router.navigate(['../../alterar/'+this.departamento.id], { relativeTo: this.activatedRoute });
  }

}
