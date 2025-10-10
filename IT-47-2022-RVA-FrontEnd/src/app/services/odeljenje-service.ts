import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Odeljenje } from '../models/odeljenje';
import { ODELJENJE_URL, ODELJENJE_URL_R } from '../app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class OdeljenjeService {

  constructor(private httpClient: HttpClient) { }

  public getAllOdeljenja(): Observable<Odeljenje[]> {
    return this.httpClient.get<Odeljenje[]>(`${ODELJENJE_URL}`);
  }

  public addOdeljenje(odeljenje: Odeljenje): Observable<any> {
    odeljenje.id = 0;
    return this.httpClient.post(`${ODELJENJE_URL_R}`, odeljenje, {
      responseType: 'text' as 'json'
    });
  }

  public updateOdeljenje(odeljenje: Odeljenje): Observable<any> {
    return this.httpClient.put(`${ODELJENJE_URL_R}/${odeljenje.id}`, odeljenje, {
      responseType: 'text' as 'json'
    });
  }

  public deleteOdeljenje(id: number): Observable<any> {
    return this.httpClient.delete(`${ODELJENJE_URL_R}/${id}`, {
      responseType: 'text' as 'json'
    });
  }

  public getOdeljenjeById(id: number): Observable<Odeljenje> {
    return this.httpClient.get<Odeljenje>(`${ODELJENJE_URL_R}/${id}`);
  }
}
