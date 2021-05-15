import {Component} from '@angular/core';

@Component({
  selector: 'ngx-activity',
  styleUrls: ['./activity.component.scss'],
  templateUrl: './activity.component.html',
})
export class ActivityComponent {
  imageSubmittedEvent: object;

  newImageSubmittedEvent(imageSubmittedEvent: object) {
    this.imageSubmittedEvent = imageSubmittedEvent;
  }
}
