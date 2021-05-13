import {Component} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';

import {SmartTableData} from '../../@core/data/smart-table';


@Component({
  selector: 'ngx-activity',
  styleUrls: ['./activity.component.scss'],
  templateUrl: './activity.component.html',
})
export class ActivityComponent {
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

  constructor(private service: SmartTableData) {
    const data = this.service.getData();
    this.source.load(data);
  }
}
