import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { EmailAddressDetailComponent } from 'app/entities/email-address/email-address-detail.component';
import { EmailAddress } from 'app/shared/model/email-address.model';

describe('Component Tests', () => {
  describe('EmailAddress Management Detail Component', () => {
    let comp: EmailAddressDetailComponent;
    let fixture: ComponentFixture<EmailAddressDetailComponent>;
    const route = ({ data: of({ emailAddress: new EmailAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [EmailAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmailAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emailAddress on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
