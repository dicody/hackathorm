import {NgModule} from '@angular/core';
import {NbButtonModule, NbCardModule, NbInputModule, NbRadioModule} from '@nebular/theme';
import {FeedbackComponent} from './feedback.component';

@NgModule({
  imports: [
    NbCardModule,
    NbInputModule,
    NbButtonModule,
    NbRadioModule,
  ],
  declarations: [
    FeedbackComponent,
  ],
})
export class FeedbackModule {
}
