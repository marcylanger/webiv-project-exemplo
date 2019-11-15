import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartamentoSearchComponent } from './departamento-search.component';

describe('DepartamentoSearchComponent', () => {
  let component: DepartamentoSearchComponent;
  let fixture: ComponentFixture<DepartamentoSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartamentoSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartamentoSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
