import {NgModule} from '@angular/core';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {NbCardModule, NbListModule, NbTreeGridModule} from '@nebular/theme';

import {ThemeModule} from '../../@theme/theme.module';
import {ActivityComponent} from './activity.component';

@NgModule({
  imports: [
    NbCardModule,
    ThemeModule,
    NbListModule,
    NbTreeGridModule,
    Ng2SmartTableModule,
  ],
  declarations: [
    ActivityComponent,
  ],
})
export class ActivityModule {
}
