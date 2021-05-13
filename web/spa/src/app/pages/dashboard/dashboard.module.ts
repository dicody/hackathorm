import { NgModule } from '@angular/core';
import { NbCardModule } from '@nebular/theme';

import { ThemeModule } from '../../@theme/theme.module';
import { DashboardComponent } from './dashboard.component';
import {Ng2SmartTableModule} from "ng2-smart-table";

@NgModule({
  imports: [
    NbCardModule,
    ThemeModule,
    Ng2SmartTableModule,
  ],
  declarations: [
    DashboardComponent,
  ],
})
export class DashboardModule { }
