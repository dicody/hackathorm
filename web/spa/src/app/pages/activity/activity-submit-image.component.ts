import {Component, EventEmitter, Output} from '@angular/core';

import {FormBuilder, Validators} from '@angular/forms';
import {ActivityService} from './activity.service';


@Component({
  selector: 'ngx-activity-submit',
  styleUrls: ['./activity.component.scss'],
  templateUrl: './activity-submit-image.component.html',
  providers: [ActivityService],
})
export class ActivitySubmitImageComponent {

  @Output() submittedEvent = new EventEmitter<object>();

  imageForm = this.formBuilder.group({
    name: ['', Validators.required],
    imageUrl: ['', Validators.required],
  });

  constructor(private activityService: ActivityService,
              private formBuilder: FormBuilder) {
  }

  onSubmit(): void {
    if (this.imageForm.dirty && this.imageForm.valid) {
      this.activityService
        .add(this.imageForm.value)
        .subscribe(_ => this.submittedEvent.emit({}));
    }
  }
}
