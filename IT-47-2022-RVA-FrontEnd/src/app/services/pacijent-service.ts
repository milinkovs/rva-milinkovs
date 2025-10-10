import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PACIJENT_URL, PACIJENT_URL_R } from '../app.constants';
import { Observable, of } from 'rxjs';
import { switchMap, catchError } from 'rxjs/operators';
import { Pacijent } from '../models/pacijent';
import { Odeljenje } from '../models/odeljenje';
import { OdeljenjeService } from './odeljenje-service';

@Injectable({
  providedIn: 'root'
})
export class PacijentService {

  constructor(
    private httpClient: HttpClient,
    private odeljenjeService: OdeljenjeService
  ) { }

  public getAllPacijenti(): Observable<any> {
    return this.httpClient.get(`${PACIJENT_URL}`);
  }

  public addPacijent(pacijent: Pacijent): Observable<any> {
    pacijent.id = 0;
    return this.httpClient.post(`${PACIJENT_URL_R}`, pacijent, {
      responseType: 'text' as 'json'
    });
  }

  public updatePacijent(pacijent: Pacijent): Observable<any> {
    return this.httpClient.put(`${PACIJENT_URL_R}/${pacijent.id}`, pacijent, {
      responseType: 'text' as 'json'
    });
  }

  public deletePacijent(id: number): Observable<any> {
    return this.httpClient.delete(`${PACIJENT_URL_R}/${id}`, {
      responseType: 'text' as 'json'
    });
  }

  public getPacijentiByOdeljenje(odeljenjeId: number): Observable<any> {
    const odeljenje = { id: odeljenjeId };
    return this.httpClient.post(`${PACIJENT_URL_R}/po-odeljenju`, odeljenje).pipe(
      catchError(error => {
        console.error('Error in getPacijentiByOdeljenje:', error);
        return of([]);
      })
    );
  }
}

