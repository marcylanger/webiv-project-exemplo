import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FuncionarioSearchComponent } from './funcionario-search.component';

describe('FuncionarioSearchComponent', () => {
  let component: FuncionarioSearchComponent;
  let fixture: ComponentFixture<FuncionarioSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FuncionarioSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FuncionarioSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
