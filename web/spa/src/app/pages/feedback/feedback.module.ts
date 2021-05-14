import {NgModule} from '@angular/core';
import {NbButtonModule, NbCardModule, NbInputModule, NbRadioModule} from '@nebular/theme';
import {FeedbackComponent} from './feedback.component';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    NbCardModule,
    NbInputModule,
    NbButtonModule,
    NbRadioModule,
    ReactiveFormsModule,
  ],
  declarations: [
    FeedbackComponent,
  ],
})
export class FeedbackModule {
}
