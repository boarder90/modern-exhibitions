import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeLinkTestComponent } from './node-link-test.component';

describe('NodeLinkTestComponent', () => {
  let component: NodeLinkTestComponent;
  let fixture: ComponentFixture<NodeLinkTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NodeLinkTestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NodeLinkTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
