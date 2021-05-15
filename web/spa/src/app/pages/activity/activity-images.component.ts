import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';

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
export class ActivityImagesComponent implements OnChanges {
  @Input() refreshEvent: object;

  settings = {
    hideSubHeader: true,
    actions: {add: false, edit: false, delete: false},
    columns: {
      id: {
        title: '#',
        type: 'string',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      submittedAt: {
        title: 'Submitted At',
        type: 'string',
        sortDirection: 'desc',
      },
      submittedBy: {
        title: 'Submitted By',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private service: ActivityService) {
    this.loadData();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadData();
  }

  private loadData() {
    this.service
      .getData()
      .subscribe(data => this.source.load(data));
  }
}
