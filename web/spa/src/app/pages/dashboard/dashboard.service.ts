import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {SmartTableData} from '../../@core/data/smart-table';
import {Game} from './Game';

@Injectable()
export class DashboardService extends SmartTableData {
  url: string;

  constructor(private http: HttpClient) {
    super();
    this.url = environment.dashboardUrl;
  }

  getData(): Observable<Game[]> {
    return this.http.get<Game[]>(this.url);
  }

  getStatistics(): Observable<any> {
    return this.http.get(this.url + '/statistics');
  }
}
