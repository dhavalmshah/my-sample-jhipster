import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPacking } from 'app/shared/model/packing.model';

@Component({
  selector: 'jhi-packing-detail',
  templateUrl: './packing-detail.component.html',
})
export class PackingDetailComponent implements OnInit {
  packing: IPacking | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ packing }) => (this.packing = packing));
  }

  previousState(): void {
    window.history.back();
  }
}
