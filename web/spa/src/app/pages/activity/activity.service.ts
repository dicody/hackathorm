import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {ImageVersion} from './image-version';
import {SmartTableData} from '../../@core/data/smart-table';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable()
export class ActivityService extends SmartTableData {
  activityUrl: string;
  imageVersionUrl: string;

  constructor(private http: HttpClient) {
    super();
    this.activityUrl = environment.activityUrl;
    this.imageVersionUrl = this.activityUrl + '/image';
  }

  submitNewVersion(imageVersion: ImageVersion) {
    return this.http.post<ImageVersion>
      // todo catch error
      (this.imageVersionUrl , imageVersion, httpOptions);
  }


  getData(): Observable<ImageVersion[]> {
    return this.http.get<ImageVersion[]>(this.imageVersionUrl);
  }
}
