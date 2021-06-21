import { NgModule } from '@angular/core';
import { Routes,RouterModule } from '@angular/router';
import { AddItemComponent } from './add-item/add-item.component';
import { DeleteItemComponent } from './delete-item/delete-item.component';
import { BorrowItemComponent } from './borrow-item/borrow-item.component';
import { ReturnItemComponent } from './return-item/return-item.component';
import { GenerateItemComponent } from './generate-item/generate-item.component';
import { ReportComponent } from './report/report.component';
import { DvdItemComponent } from './dvd-item/dvd-item.component';
import { ReaderComponent } from './reader/reader.component';
import { ReserveComponent } from './reserve/reserve.component';


const routes: Routes =[
  {path: 'add-item', component: AddItemComponent},
  {path: 'delete-item', component: DeleteItemComponent},
  {path: 'borrow-item', component: BorrowItemComponent},
  {path: 'return-item', component: ReturnItemComponent},
  {path: 'generate-item', component: GenerateItemComponent},
  {path: 'report', component: ReportComponent},
  {path: 'dvd-item', component: DvdItemComponent},
  {path: 'reader', component: ReaderComponent},
  {path: 'reserve', component: ReserveComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule{}
