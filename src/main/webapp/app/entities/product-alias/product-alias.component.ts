import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductAlias } from 'app/shared/model/product-alias.model';
import { ProductAliasService } from './product-alias.service';
import { ProductAliasDeleteDialogComponent } from './product-alias-delete-dialog.component';

@Component({
  selector: 'jhi-product-alias',
  templateUrl: './product-alias.component.html',
})
export class ProductAliasComponent implements OnInit, OnDestroy {
  productAliases?: IProductAlias[];
  eventSubscriber?: Subscription;

  constructor(
    protected productAliasService: ProductAliasService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productAliasService.query().subscribe((res: HttpResponse<IProductAlias[]>) => (this.productAliases = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductAliases();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductAlias): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductAliases(): void {
    this.eventSubscriber = this.eventManager.subscribe('productAliasListModification', () => this.loadAll());
  }

  delete(productAlias: IProductAlias): void {
    const modalRef = this.modalService.open(ProductAliasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productAlias = productAlias;
  }
}
