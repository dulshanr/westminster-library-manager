import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AppService } from '../app.service';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})
export class ReserveComponent implements OnInit {

  public Data_3;
  public Data_4;


  public dataReturned;
  
  public date_1;
  public year;
  public month;
  public day;


  public dateDisplayed;

  constructor(private router: Router, private appservice_2: AppService) { }

  ngOnInit() {
    this.date_1=new Date();
    this.day=this.date_1.getDate();
    this.month=this.date_1.getMonth()+1;
    this.year=this.date_1.getFullYear();
    this.dateDisplayed=this.day+"/"+this.month+"/"+this.year;
  }

  submitData(form: NgForm){
    this.appservice_2.getAverageDays((form.value)).subscribe((data: any) => {
      console.log("Hi")
      if(data.content.includes("You will have to wait an aproximate amount of ") || data.content.includes("Data is not enough to provide a prediction,but do you wish to get added to the que")){
      var y=confirm(data.content);
      if(y){
        this.appservice_2.addToQue((form.value)).subscribe((data: any) => {
            alert("Successfully Added")
        })

      }else{
        console.log("Pressed no")
      }
    }else{
      alert(data.content)
    }

    })
  }

}
