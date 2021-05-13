import {Injectable} from '@angular/core';
import {SmartTableData} from '../data/smart-table';

@Injectable()
export class ActivityService extends SmartTableData {

  data = [{
    id: 1,
    name: 'first attempt',
    submittedAt: '15:38:34 12.05.2021',
    submittedBy: 'teamMember1',
  }, {
    id: 2,
    name: 'another try',
    submittedAt: '15:42:42 12.05.2021',
    submittedBy: 'teamMember1',
  }, {
    id: 3,
    name: 'final one',
    submittedAt: '16:00:01 12.05.2021',
    submittedBy: 'teamMember2',
  }];

  getData() {
    return this.data;
  }
}
