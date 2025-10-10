import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DIJAGNOZA_URL, DIJAGNOZA_URL_R } from '../app.constants';
import { Dijagnoza } from '../models/dijagnoza';

@Injectable({
  providedIn: 'root'
})
export class DijagnozaService {

  constructor(private httpClient: HttpClient) { }

  public getAllDijagnoze(): Observable<Dijagnoza[]> {
    return this.httpClient.get<Dijagnoza[]>(`${DIJAGNOZA_URL}`);
  }

  public addDijagnoza(dijagnoza: Dijagnoza): Observable<any> {
    dijagnoza.id = 0;
    return this.httpClient.post(`${DIJAGNOZA_URL_R}`, dijagnoza, {
      responseType: 'text' as 'json'
    });
  }

  public updateDijagnoza(dijagnoza: Dijagnoza): Observable<any> {
    return this.httpClient.put(`${DIJAGNOZA_URL_R}/${dijagnoza.id}`, dijagnoza, {
      responseType: 'text' as 'json'
    });
  }

  public deleteDijagnoza(id: number): Observable<any> {
    return this.httpClient.delete(`${DIJAGNOZA_URL_R}/${id}`, {
      responseType: 'text' as 'json'
    });
  }
}
