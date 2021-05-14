import {NgModule} from '@angular/core';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {NbButtonModule, NbCardModule, NbInputModule} from '@nebular/theme';

import {ActivityComponent} from './activity.component';
import {ActivitySubmitImageVersionComponent} from './activity-submit-image-version.component';
import {ActivityImageVersionsComponent} from './activity-image-versions.component';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    NbCardModule,
    Ng2SmartTableModule,
    NbInputModule,
    NbButtonModule,
    ReactiveFormsModule,
  ],
  declarations: [
    ActivityComponent,
    ActivitySubmitImageVersionComponent,
    ActivityImageVersionsComponent,
  ],
})
export class ActivityModule {
}
