import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BOLNICA_URL, BOLNICA_URL_R } from '../app.constants';
import { Bolnica } from '../models/bolnica';

@Injectable({
  providedIn: 'root'
})
export class BolnicaService {

  constructor(private httpClient: HttpClient) { }

  public getAllBolnice(): Observable<any> {
    return this.httpClient.get(`${BOLNICA_URL}`);
  }

  public addBolnica(bolnica: Bolnica): Observable<any> {
    bolnica.id = 0;
    return this.httpClient.post(`${BOLNICA_URL_R}`, bolnica, {
      responseType: 'text' as 'json'
    });
  }

  public updateBolnica(bolnica: Bolnica): Observable<any> {
    return this.httpClient.put(`${BOLNICA_URL_R}/${bolnica.id}`, bolnica, {
      responseType: 'text' as 'json'
    });
  }

  public deleteBolnica(id: number): Observable<any> {
    return this.httpClient.delete(`${BOLNICA_URL_R}/${id}`, {
      responseType: 'text' as 'json'
    });
  }
}
