import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { CounterPartyUpdateComponent } from 'app/entities/counter-party/counter-party-update.component';
import { CounterPartyService } from 'app/entities/counter-party/counter-party.service';
import { CounterParty } from 'app/shared/model/counter-party.model';

describe('Component Tests', () => {
  describe('CounterParty Management Update Component', () => {
    let comp: CounterPartyUpdateComponent;
    let fixture: ComponentFixture<CounterPartyUpdateComponent>;
    let service: CounterPartyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [CounterPartyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CounterPartyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CounterPartyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CounterPartyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CounterParty(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CounterParty();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
