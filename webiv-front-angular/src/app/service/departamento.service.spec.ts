import { TestBed } from '@angular/core/testing';

import { DepartamentoService } from './departamento.service';

describe('DepartamentoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DepartamentoService = TestBed.get(DepartamentoService);
    expect(service).toBeTruthy();
  });
});
