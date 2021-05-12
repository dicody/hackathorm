import { NgModule } from '@angular/core';
import { NbCardModule } from '@nebular/theme';

import { ThemeModule } from '../../@theme/theme.module';
import { FeedbackComponent } from './feedback.component';

@NgModule({
  imports: [
    NbCardModule,
    ThemeModule,
  ],
  declarations: [
    FeedbackComponent,
  ],
})
export class FeedbackModule { }
