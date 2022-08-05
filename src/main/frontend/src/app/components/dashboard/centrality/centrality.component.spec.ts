import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentralityComponent } from './centrality.component';

describe('CentralityComponent', () => {
  let component: CentralityComponent;
  let fixture: ComponentFixture<CentralityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentralityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentralityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
