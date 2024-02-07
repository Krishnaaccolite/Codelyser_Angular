import { TestBed } from '@angular/core/testing';

import { MonacoeditorService } from './monacoeditor.service';

describe('MonacoeditorService', () => {
  let service: MonacoeditorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MonacoeditorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
