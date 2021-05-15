import {NgModule} from '@angular/core';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {NbButtonModule, NbCardModule, NbInputModule} from '@nebular/theme';

import {ActivityComponent} from './activity.component';
import {ActivitySubmitImageComponent} from './activity-submit-image.component';
import {ActivityImagesComponent} from './activity-images.component';
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
    ActivitySubmitImageComponent,
    ActivityImagesComponent,
  ],
})
export class ActivityModule {
}
