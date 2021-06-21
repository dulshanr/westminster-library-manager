import { Component } from '@angular/core';

import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string;
  postRequestResponse: string;

  startupCondition=true;

  constructor(private appService: AppService) {
    this.appService.getWelcomeMessage().subscribe((data: any) => {
      this.title = data.content;
      this.startup();
    });
  }

  /**
   * This method is used to test the post request
   */
  public postData(): void {
    this.appService.sendData().subscribe((data: any) => {
      this.postRequestResponse = data.content;
    });
  }

  public startup(): void{


      this.appService.getList().subscribe((data:any)=>{
      })
      console.log("Starting Up")
      this.startupCondition=false;


  }

}
