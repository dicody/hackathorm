import {NgModule} from '@angular/core';
import {NbMenuModule} from '@nebular/theme';

import {ThemeModule} from '../@theme/theme.module';
import {PagesComponent} from './pages.component';
import {DashboardModule} from './dashboard/dashboard.module';
import {PagesRoutingModule} from './pages-routing.module';
import {FeedbackModule} from './feedback/feedback.module';
import {ActivityModule} from './activity/activity.module';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    FeedbackModule,
    ActivityModule,
  ],
  declarations: [
    PagesComponent,
  ],
})
export class PagesModule {
}
