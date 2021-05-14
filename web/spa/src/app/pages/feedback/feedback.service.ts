import {Injectable} from '@angular/core';
import {Feedback} from './feedback';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable()
export class FeedbackService {
  url: string;

  constructor(private http: HttpClient) {
    this.url = environment.feedbackUrl;
  }

  add(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>
    (this.url, feedback, httpOptions);
    // todo catch error
    // .pipe();
  }
}
