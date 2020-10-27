import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { DispatchDetailComponent } from 'app/entities/dispatch/dispatch-detail.component';
import { Dispatch } from 'app/shared/model/dispatch.model';

describe('Component Tests', () => {
  describe('Dispatch Management Detail Component', () => {
    let comp: DispatchDetailComponent;
    let fixture: ComponentFixture<DispatchDetailComponent>;
    const route = ({ data: of({ dispatch: new Dispatch(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [DispatchDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DispatchDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DispatchDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dispatch on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dispatch).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
