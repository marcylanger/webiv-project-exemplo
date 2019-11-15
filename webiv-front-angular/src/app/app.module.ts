import { BrowserModule} from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatListModule, MatCardModule, MatMenuModule, MatInputModule, MatButtonToggleModule, MatIconModule,
  MatProgressSpinnerModule, MatSelectModule, MatSlideToggleModule, MatSnackBarModule, MatToolbarModule,
  MatTabsModule, MatSidenavModule, MatTooltipModule, MatRippleModule, MatRadioModule, MatGridListModule,
  MatDatepickerModule, MatNativeDateModule, MatSliderModule, MatAutocompleteModule } from '@angular/material';
import {MatDialogModule} from '@angular/material/dialog';
import { CovalentCommonModule, CovalentLayoutModule, CovalentMediaModule, CovalentExpansionPanelModule,
  CovalentStepsModule, CovalentLoadingModule, CovalentDialogsModule, CovalentSearchModule, CovalentPagingModule,
  CovalentNotificationsModule, CovalentMenuModule, CovalentDataTableModule, CovalentMessageModule } from '@covalent/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule} from "@angular/flex-layout";
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';




import { AppComponent } from './app.component';
import { DepartamentoListComponent } from './view/departamento/departamento-list/departamento-list.component';
import { DepartamentoFormComponent } from './view/departamento/departamento-form/departamento-form.component';
import { MenuComponent } from './view/menu/menu.component';
import { DepartamentoSearchComponent } from './view/departamento/departamento-search/departamento-search.component';
import { UsuarioService } from './service/usuario.service';
import { DepartamentoService } from './service/departamento.service';
import { DepartamentoDetailComponent } from './view/departamento/departamento-detail/departamento-detail.component';
import { FuncionarioDetailComponent } from './view/funcionario/funcionario-detail/funcionario-detail.component';
import { FuncionarioSearchComponent } from './view/funcionario/funcionario-search/funcionario-search.component';
import { FuncionarioFormComponent } from './view/funcionario/funcionario-form/funcionario-form.component';
import { FuncionarioListComponent } from './view/funcionario/funcionario-list/funcionario-list.component';

@NgModule({
  declarations: [
    AppComponent,
    DepartamentoListComponent,
    DepartamentoFormComponent,
    MenuComponent,
    DepartamentoSearchComponent,
    DepartamentoDetailComponent,
    FuncionarioDetailComponent,
    FuncionarioSearchComponent, 
    FuncionarioFormComponent,
    FuncionarioListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    HttpModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    
    /** Material Modules */
    
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatInputModule,
    MatSelectModule,
    MatButtonToggleModule,
    MatSlideToggleModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatTabsModule,
    MatSidenavModule,
    MatTooltipModule,
    MatRippleModule,
    MatRadioModule,
    MatGridListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSliderModule,
    MatAutocompleteModule,
    /** Covalent Modules */
    CovalentCommonModule,
    CovalentLayoutModule,
    CovalentMediaModule,
    CovalentExpansionPanelModule,
    CovalentStepsModule,
    CovalentDialogsModule,
    CovalentLoadingModule,
    CovalentSearchModule,
    CovalentPagingModule,
    CovalentNotificationsModule,
    CovalentMenuModule,
    CovalentDataTableModule,
    CovalentMessageModule

  ],
  providers: [
    UsuarioService,
    DepartamentoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
