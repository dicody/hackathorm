import {NgModule} from '@angular/core';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {
  NbButtonModule,
  NbCardModule,
  NbInputModule,
} from '@nebular/theme';

import {ActivityComponent} from './activity.component';

@NgModule({
  imports: [
    NbCardModule,
    Ng2SmartTableModule,
    NbInputModule,
    NbButtonModule,
  ],
  declarations: [
    ActivityComponent,
  ],
})
export class ActivityModule {
}
