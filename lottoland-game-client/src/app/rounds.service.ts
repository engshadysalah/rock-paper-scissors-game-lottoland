import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  private baseUrl = 'http://localhost:8080/api/v1/game/get';

  constructor(private http: HttpClient) { }

  getAllRoundsForAllSessions(): Observable<any> {
    return this.http.get(`${this.baseUrl}/allRoundsResultForAllSessions`);
  }

  playAndGetAllRoundsDetailsPerSingleSession(): Observable<any> {
    return this.http.get(`${this.baseUrl}/play`);
  }

}