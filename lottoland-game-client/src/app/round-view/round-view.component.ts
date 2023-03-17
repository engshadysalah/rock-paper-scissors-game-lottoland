import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Round } from '../round';
import { RoundsService } from '../rounds.service';
import {HttpClient} from "@angular/common/http";




@Component({
  selector: 'app-round-view',
  templateUrl: './round-view.component.html',
  styleUrls: ['./round-view.component.css']
})
export class RoundViewComponent  implements OnInit {
 
  rounds: Round[] = [];
  constructor(private roundsService: RoundsService,
    private router: Router, private http: HttpClient) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {

    debugger

    this.http.get<any>('http://localhost:8080/api/v1/game/play')
      .toPromise()
      .then(res => <Round[]>res.data)
      .then(data => { return data; }).then(

        data1 => {
          data1.forEach(data => {
            let roundObject: Round = new Round();
            // @ts-ignore
            data.allRoundsPerSingleSession.forEach(round => {
              roundObject.roundNumbersPerSingleSession = data.roundNumbersPerSingleSession;
              roundObject.allRoundsPerSingleSession.firstPlayerMove = round.firstPlayerMove;
              roundObject.allRoundsPerSingleSession.secondPlayerMove = round.secondPlayerMove;
              roundObject.allRoundsPerSingleSession.roundResult = round.roundResult;
              this.rounds.push(roundObject);
              roundObject = new Round();
            });

          })

        }
       );

    console.log(this.rounds);

  //  console.log(this.rounds.pipe(tap(x=>console.log(x))));


/*
    const jsonData = [{
      "roundNumbersPerSingleSession": 2,
      "allRoundsPerSingleSession": [
          {
              "firstPlayerMove": "rock",
              "restarted": false,
              "secondPlayerMove": "rock"
          },
          {
              "firstPlayerMove": "scissors",
              "restarted": false,
              "secondPlayerMove": "rock"
          }
      ]
  }];

 */

  //json.

}

playGame() {

 // this.rounds = this.roundsService.playAndGetAllRoundsDetailsPerSingleSession();
}


reStartGame() {
  
  // this.rounds = this.roundsService.restartGame();
}

}

