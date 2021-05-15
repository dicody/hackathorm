import {Component} from '@angular/core';

import {ActivityService} from './activity.service';
import {LocalDataSource} from 'ng2-smart-table';


@Component({
  selector: 'ngx-activity-images',
  styleUrls: ['./activity.component.scss'],
  template: `
    <ng2-smart-table [settings]="settings" [source]="source"></ng2-smart-table>
  `,
  providers: [ActivityService],
})
export class ActivityImagesComponent {
  settings = {
    hideSubHeader: true,
    actions: {add: false, edit: false, delete: false},
    columns: {
      id: {
        title: '#',
        type: 'number',
        sortDirection: 'desc',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      submittedAt: {
        title: 'Submitted At',
        type: 'string',
      },
      submittedBy: {
        title: 'Submitted By',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private service: ActivityService) {
    this.service
      .getData()
      .subscribe(data => this.source.load(data));
  }
}
