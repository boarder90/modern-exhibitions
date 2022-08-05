import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NodesColoringComponent } from './nodes-coloring.component';

describe('NodesColoringComponent', () => {
  let component: NodesColoringComponent;
  let fixture: ComponentFixture<NodesColoringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NodesColoringComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NodesColoringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
