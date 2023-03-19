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
 
  round?: Round;

  constructor(private roundsService: RoundsService,
    private router: Router, private http: HttpClient) {}

  ngOnInit() {

    this.roundsService.getAllRoundsForAllSessions()
    .subscribe(data => {
      console.log(data)
      this.round = data;
    }, error => console.log(error))

  }

}

