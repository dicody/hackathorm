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
  feedbackProvided: boolean;
  private feedbackProvidedKey = 'feedbackProvided';

  constructor(private feedbackService: FeedbackService,
              private formBuilder: FormBuilder) {
    this.feedbackProvided = Boolean(localStorage.getItem(this.feedbackProvidedKey));
  }

  onSubmit(): void {
    this.feedbackService
      .add(this.feedbackForm.value)
      .subscribe(_ => {
        this.feedbackForm.reset();
        this.feedbackProvided = true;
        localStorage.setItem(this.feedbackProvidedKey, String(this.feedbackProvided));
      });
  }
}
