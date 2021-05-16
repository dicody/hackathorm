import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Image} from './image';
import {SmartTableData} from '../../@core/data/smart-table';
import {Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable()
export class ActivityService extends SmartTableData {
  url: string;

  constructor(private http: HttpClient) {
    super();
    this.url = environment.imageUrl;
  }

  add(image: Image) {
    return this.http.post<Image>
      // todo catch error
      (this.url, image, httpOptions);
  }


  getData(): Observable<Image[]> {
    return this.http.get<Image[]>(this.url);
  }
}
