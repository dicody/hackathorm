import { Component } from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {DashboardService} from '../../@core/mock/dashboard.service';

@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  settings = {
    hideSubHeader: true,
    actions: {add: false, edit: false, delete: false},
    columns: {
      id: {
        title: '#',
        type: 'number',
        sortDirection: 'desc',
      },
      players: {
        title: 'Players',
        type: 'string',
      },
      won: {
        title: 'Won',
        type: 'string',
      },
      info: {
        title: 'Info',
        type: 'string',
      },
      startedAt: {
        title: 'Started At',
        type: 'string',
      },
      finishedAt: {
        title: 'Finished At',
        type: 'string',
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private service: DashboardService) {
    const data = this.service.getData();
    this.source.load(data);
  }
}
