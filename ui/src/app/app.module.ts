import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RouteExampleComponent } from './route-example/route-example.component';

import { AppService } from './app.service';
import { AppHttpInterceptorService } from './http-interceptor.service';
import { AddItemComponent } from './add-item/add-item.component';
import { BorrowItemComponent } from './borrow-item/borrow-item.component';
import { DeleteItemComponent } from './delete-item/delete-item.component';
import { GenerateItemComponent } from './generate-item/generate-item.component';
import { ReportComponent } from './report/report.component';
import {AppRoutingModule} from "./app-routing.module";
import { ReturnItemComponent } from './return-item/return-item.component';

import { DvdItemComponent } from './dvd-item/dvd-item.component';
import {FormsModule}  from '@angular/forms';
import { ReaderComponent } from './reader/reader.component';
import { ReserveComponent } from './reserve/reserve.component'



const routes: Routes = [
  {
    path: 'java',
    component: RouteExampleComponent,
    data: { technology: 'Java' }
  },
  {
    path: 'play',
    component: RouteExampleComponent,
    data: { technology: 'Play' }
  },
  {
    path: 'angular',
    component: RouteExampleComponent,
    data: { technology: 'Angular' }
  },
  {
    path: '**',
    redirectTo: '/play',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    RouteExampleComponent,
    AddItemComponent,
    BorrowItemComponent,
    DeleteItemComponent,
    GenerateItemComponent,
    ReportComponent,
    ReturnItemComponent,

    DvdItemComponent,

    ReaderComponent,

    ReserveComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'Csrf-Token',
      headerName: 'Csrf-Token',
    }),
    RouterModule.forRoot(routes),
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    AppService,
    {
      multi: true,
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptorService
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
