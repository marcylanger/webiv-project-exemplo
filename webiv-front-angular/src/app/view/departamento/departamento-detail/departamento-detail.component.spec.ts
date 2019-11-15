import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartamentoDetailComponent } from './departamento-detail.component';

describe('DepartamentoDetailComponent', () => {
  let component: DepartamentoDetailComponent;
  let fixture: ComponentFixture<DepartamentoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartamentoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartamentoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
