import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatitasComponent } from './patitas.component';

describe('PatitasComponent', () => {
  let component: PatitasComponent;
  let fixture: ComponentFixture<PatitasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatitasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
