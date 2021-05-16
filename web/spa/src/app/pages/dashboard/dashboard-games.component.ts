import {Component} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {DashboardService} from './dashboard.service';

@Component({
  selector: 'ngx-dashboard-games',
  styleUrls: ['./dashboard.component.scss'],
  template: `
    <ng2-smart-table [settings]="settings" [source]="source"></ng2-smart-table>
  `,
  providers: [DashboardService],
})
export class DashboardGamesComponent {
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
      winner: {
        title: 'Winner',
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
    this.service
      .getData()
      .subscribe(data => {
        this.source.load(data);
      });
  }
}
