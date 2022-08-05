import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExhibitionsListComponent } from './exhibitions-list.component';

describe('ExhibitionsListComponent', () => {
  let component: ExhibitionsListComponent;
  let fixture: ComponentFixture<ExhibitionsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExhibitionsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExhibitionsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
