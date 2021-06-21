import { Component, OnInit } from '@angular/core';
import {AppService} from "../app.service";
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-generate-item',
  templateUrl: './generate-item.component.html',
  styleUrls: ['./generate-item.component.css']
})
export class GenerateItemComponent implements OnInit {

  public receivedBookList=[];
  public receivedDVDList=[];


  constructor(private appservice_1: AppService) { }

  ngOnInit() {

    this.appservice_1.getBookList_generate().subscribe((data:any)=>{
      this.receivedBookList=data;

    })


  }

  resetMe(){
    this.appservice_1.getBookList_generate().subscribe((data:any)=>{
      this.receivedBookList=data;

    })
    console.log("Resetted")

  }
  submitData(form: NgForm){
    this.appservice_1.searchItem((form.value)).subscribe((data:any)=>{
      this.receivedBookList=data;

    })

    console.log((form.value));
  }


  public getbooklist(): void{
    this.appservice_1.getBookList_generate().subscribe((data:any)=>{
      this.receivedBookList=data;
    })
  }

  public getDVDlist(): void{
    this.appservice_1.getDVDList_generate().subscribe((data:any)=>{
      this.receivedDVDList=data;
    })
  }

}
