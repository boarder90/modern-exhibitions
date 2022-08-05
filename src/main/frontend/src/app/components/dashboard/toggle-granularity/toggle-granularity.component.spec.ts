import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToggleGranularityComponent } from './toggle-granularity.component';

describe('ToggleGranularityComponent', () => {
  let component: ToggleGranularityComponent;
  let fixture: ComponentFixture<ToggleGranularityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToggleGranularityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToggleGranularityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
