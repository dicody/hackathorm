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
  imageUrl: string;

  constructor(private http: HttpClient) {
    super();
    this.imageUrl = environment.imageUrl;
  }

  add(image: Image) {
    return this.http.post<Image>
      // todo catch error
      (this.imageUrl, image, httpOptions);
  }


  getData(): Observable<Image[]> {
    return this.http.get<Image[]>(this.imageUrl);
  }
}
