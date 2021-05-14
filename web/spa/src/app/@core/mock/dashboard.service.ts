import {Injectable} from '@angular/core';
import {SmartTableData} from '../data/smart-table';
import {from, Observable} from 'rxjs';
import {toArray} from 'rxjs/operators';

@Injectable()
export class DashboardService extends SmartTableData {

  data = [
    {
      id: 1,
      players: 'p1, p2',
      won: 'p1',
      info: '',
      startedAt: '15:38:34 12.05.2021',
      finishedAt: '15:39:34 12.05.2021',
    },
    {
      id: 2,
      players: 'p2, p1',
      won: '',
      info: 'draw',
      startedAt: '15:40:34 12.05.2021',
      finishedAt: '15:41:34 12.05.2021',
    },
    {
      id: 3,
      players: 'p1, p2',
      won: 'p2',
      info: 'p1 timeout',
      startedAt: '15:42:34 12.05.2021',
      finishedAt: '15:45:34 12.05.2021',
    },
  ];

  getData(): Observable<any> {
    return from(this.data)
      .pipe(toArray());
  }
}
