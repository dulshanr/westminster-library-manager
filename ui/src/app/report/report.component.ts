import {Component, OnInit} from '@angular/core';
import {IssueService} from "../issues.service";
import {AppService} from "../app.service";


@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  public receivedData: string;
  public name_2: string;
  public bookList = [];
  public receivedData_2 = [];
  public receivedData_x;
  public DueBooks = [];

  public name_ex: string;

  public date_1;
  public year;
  public month;
  public day;
  public dateDisplayed;


  constructor(private appservice_1: AppService) {
  }

  ngOnInit() {
    this.date_1 = new Date();
    this.day = this.date_1.getDate();
    this.month = this.date_1.getMonth() + 1;
    this.year = this.date_1.getFullYear();
    this.dateDisplayed = this.day + "/" + this.month + "/" + this.year;


    this.postData();
    this.postFormData();
    this.getDueBooks();
  }

  public postData(): void {
    this.appservice_1.sendData_2().subscribe((data: any) => {
      this.receivedData = data.content;
    })
  }

  public postData_10(): void {
    this.appservice_1.getBooklist_1234().subscribe((data: any) => {
      this.receivedData_2 = data;
    })
  }

  public postFormData(): void {
    this.appservice_1.postFormdata().subscribe((data: any) => {
      this.receivedData_x = data;
    })
  }

  public getDueBooks(): void {
    let time = {time: this.dateDisplayed};
    console.log(JSON.stringify(time));
    this.appservice_1.getDueBooks(time).subscribe((data: any) => {
      this.DueBooks = data;
      console.log(data);
    })
  }

  startup() {
    console.log("Button is being called")
    if (confirm("Custom Message")) {
      console.log("pressed ok");
    } else {
      console.log("not pressed ok");
    }

  }


  // public passPara(): void{
  //   this.appservice_1.getStringPassed().subscribe((data:any)=>{
  //     this.name_2=data.content;
  //   })
  // }


}
