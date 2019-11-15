import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DepartamentoFormComponent } from './view/departamento/departamento-form/departamento-form.component';
import { DepartamentoSearchComponent } from './view/departamento/departamento-search/departamento-search.component';
import { DepartamentoDetailComponent } from './view/departamento/departamento-detail/departamento-detail.component';
import { FuncionarioSearchComponent } from './view/funcionario/funcionario-search/funcionario-search.component';
import { FuncionarioFormComponent } from './view/funcionario/funcionario-form/funcionario-form.component';
import { FuncionarioDetailComponent } from './view/funcionario/funcionario-detail/funcionario-detail.component';



const routes: Routes = [
  {
    component: DepartamentoSearchComponent,
    path: 'departamentos'
  },
















  
  {
    component: DepartamentoFormComponent,
    path: 'departamentos/cadastrar'
  },
  {
    component: DepartamentoFormComponent,
    path: 'departamentos/alterar/:id'
  },
  {
    component: DepartamentoDetailComponent,
    path: 'departamentos/detalhes/:id'
  },
  {
    component: FuncionarioSearchComponent,
    path: 'funcionarios'
  },
  {
    component: FuncionarioFormComponent,
    path: 'funcionarios/cadastrar'
  },
  {
    component: FuncionarioFormComponent,
    path: 'funcionarios/alterar/:id'
  },
  {
    component: FuncionarioDetailComponent,
    path: 'funcionarios/detalhes/:id'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
