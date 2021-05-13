import { NgModule } from '@angular/core';
import { NbCardModule } from '@nebular/theme';

import { ThemeModule } from '../../@theme/theme.module';
import { DashboardComponent } from './dashboard.component';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {DashboardBarComponent} from './dashboard-bar.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';

@NgModule({
  imports: [
    NbCardModule,
    ThemeModule,
    Ng2SmartTableModule,
    NgxChartsModule,
  ],
  declarations: [
    DashboardComponent,
    DashboardBarComponent,
  ],
})
export class DashboardModule { }
