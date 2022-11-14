import { Component } from '@angular/core';
import { Login } from './models';
import { ItemService } from './service/ItemService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'day36';

  constructor(private itemSvc: ItemService) { }


  processNewRequest(newSvc: Login) {
    console.info('>>>> New registration: ', newSvc)
    this.itemSvc.newRequest(newSvc)
      .then(result => {
        console.info('>>>> Result: ', result)
      })
      .catch(error => {
        console.error('>>>> error: ', error)
      })
  }
}
