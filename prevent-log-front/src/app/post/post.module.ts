import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PostRoutingModule} from './post-routing.module';
import {IndexComponent} from './index/index.component';
import {ViewComponent} from './view/view.component';
import {CreateComponent} from './create/create.component';


import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CalendarModule, TableModule} from 'primeng';
import {AccessLogService} from './access-log.service';


@NgModule({
  declarations: [IndexComponent, ViewComponent, CreateComponent],
  providers: [AccessLogService],
  imports: [
    CommonModule,
    PostRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    CalendarModule
  ]
})
export class PostModule {
}
