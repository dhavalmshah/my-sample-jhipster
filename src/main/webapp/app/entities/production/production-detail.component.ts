import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduction } from 'app/shared/model/production.model';

@Component({
  selector: 'jhi-production-detail',
  templateUrl: './production-detail.component.html',
})
export class ProductionDetailComponent implements OnInit {
  production: IProduction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ production }) => (this.production = production));
  }

  previousState(): void {
    window.history.back();
  }
}
