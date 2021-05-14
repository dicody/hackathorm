import {Component} from '@angular/core';

import {FormBuilder, Validators} from '@angular/forms';
import {ActivityService} from './activity.service';


@Component({
  selector: 'ngx-activity-submit',
  styleUrls: ['./activity.component.scss'],
  templateUrl: './activity-submit-image-version.component.html',
  providers: [ActivityService],
})
export class ActivitySubmitImageVersionComponent {

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
        .submitNewVersion(this.imageForm.value)
        .subscribe();
    }
  }
}
