import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILocation, Location } from 'app/shared/model/location.model';
import { LocationService } from './location.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';
import { ICounterParty } from 'app/shared/model/counter-party.model';
import { CounterPartyService } from 'app/entities/counter-party/counter-party.service';

type SelectableEntity = IContact | ICounterParty;

@Component({
  selector: 'jhi-location-update',
  templateUrl: './location-update.component.html',
})
export class LocationUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContact[] = [];
  counterparties: ICounterParty[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255), Validators.pattern('^[A-Z][a-z]+\\d$')]],
    panNumber: [null, [Validators.required, Validators.maxLength(255)]],
    gstNumber: [null, [Validators.required, Validators.maxLength(255)]],
    contact: [null, Validators.required],
    counterParty: [],
  });

  constructor(
    protected locationService: LocationService,
    protected contactService: ContactService,
    protected counterPartyService: CounterPartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ location }) => {
      this.updateForm(location);

      this.contactService
        .query({ filter: 'location-is-null' })
        .pipe(
          map((res: HttpResponse<IContact[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IContact[]) => {
          if (!location.contact || !location.contact.id) {
            this.contacts = resBody;
          } else {
            this.contactService
              .find(location.contact.id)
              .pipe(
                map((subRes: HttpResponse<IContact>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IContact[]) => (this.contacts = concatRes));
          }
        });

      this.counterPartyService.query().subscribe((res: HttpResponse<ICounterParty[]>) => (this.counterparties = res.body || []));
    });
  }

  updateForm(location: ILocation): void {
    this.editForm.patchValue({
      id: location.id,
      name: location.name,
      panNumber: location.panNumber,
      gstNumber: location.gstNumber,
      contact: location.contact,
      counterParty: location.counterParty,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const location = this.createFromForm();
    if (location.id !== undefined) {
      this.subscribeToSaveResponse(this.locationService.update(location));
    } else {
      this.subscribeToSaveResponse(this.locationService.create(location));
    }
  }

  private createFromForm(): ILocation {
    return {
      ...new Location(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      panNumber: this.editForm.get(['panNumber'])!.value,
      gstNumber: this.editForm.get(['gstNumber'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      counterParty: this.editForm.get(['counterParty'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
