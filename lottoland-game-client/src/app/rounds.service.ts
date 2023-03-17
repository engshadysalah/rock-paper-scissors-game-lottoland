import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoundsService {

  private baseUrl = 'http://localhost:8080/api/v1/game';

  constructor(private http: HttpClient) { }


  playAndGetAllRoundsDetailsPerSingleSession(): Observable<any> {
    return this.http.get(`${this.baseUrl}/play`);
  }

  restartGame(): Observable<any> {
    return this.http.post(`${this.baseUrl}/restart`, null);
  }

  getAllRoundsForAllSessions(): Observable<any> {
    return this.http.get(`${this.baseUrl}/get/allRoundsPerSingleSession`);
  }

}