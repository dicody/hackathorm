import {Component} from '@angular/core';
import {FeedbackService} from './feedback.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'ngx-feedback',
  styleUrls: ['./feedback.component.scss'],
  templateUrl: './feedback.component.html',
  providers: [FeedbackService],
})
export class FeedbackComponent {

  feedbackForm = this.formBuilder.group({
    rate: 'LIKE',
    comment: '',
  });

  constructor(private feedbackService: FeedbackService,
              private formBuilder: FormBuilder) {
  }

  onSubmit(): void {
    this.feedbackService
      .add(this.feedbackForm.value)
      .subscribe(_ => {
        this.feedbackForm.reset();
        this.feedbackForm.disable();
      });
  }
}
