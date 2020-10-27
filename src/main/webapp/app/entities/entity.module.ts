import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.MySampleRegionModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.MySampleCountryModule),
      },
      {
        path: 'city',
        loadChildren: () => import('./city/city.module').then(m => m.MySampleCityModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.MySampleAddressModule),
      },
      {
        path: 'phone-number',
        loadChildren: () => import('./phone-number/phone-number.module').then(m => m.MySamplePhoneNumberModule),
      },
      {
        path: 'email-address',
        loadChildren: () => import('./email-address/email-address.module').then(m => m.MySampleEmailAddressModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.MySampleContactModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.MySampleLocationModule),
      },
      {
        path: 'billing-location',
        loadChildren: () => import('./billing-location/billing-location.module').then(m => m.MySampleBillingLocationModule),
      },
      {
        path: 'counter-party',
        loadChildren: () => import('./counter-party/counter-party.module').then(m => m.MySampleCounterPartyModule),
      },
      {
        path: 'billing-company',
        loadChildren: () => import('./billing-company/billing-company.module').then(m => m.MySampleBillingCompanyModule),
      },
      {
        path: 'product-type',
        loadChildren: () => import('./product-type/product-type.module').then(m => m.MySampleProductTypeModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.MySampleProductModule),
      },
      {
        path: 'product-alias',
        loadChildren: () => import('./product-alias/product-alias.module').then(m => m.MySampleProductAliasModule),
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.MySampleUnitModule),
      },
      {
        path: 'packing',
        loadChildren: () => import('./packing/packing.module').then(m => m.MySamplePackingModule),
      },
      {
        path: 'stock-item',
        loadChildren: () => import('./stock-item/stock-item.module').then(m => m.MySampleStockItemModule),
      },
      {
        path: 'stock-transaction',
        loadChildren: () => import('./stock-transaction/stock-transaction.module').then(m => m.MySampleStockTransactionModule),
      },
      {
        path: 'production',
        loadChildren: () => import('./production/production.module').then(m => m.MySampleProductionModule),
      },
      {
        path: 'goods-received',
        loadChildren: () => import('./goods-received/goods-received.module').then(m => m.MySampleGoodsReceivedModule),
      },
      {
        path: 'dispatch',
        loadChildren: () => import('./dispatch/dispatch.module').then(m => m.MySampleDispatchModule),
      },
      {
        path: 'transport-details',
        loadChildren: () => import('./transport-details/transport-details.module').then(m => m.MySampleTransportDetailsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MySampleEntityModule {}
