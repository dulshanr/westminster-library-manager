import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppService } from '../app.service';

@Component({
  selector: 'app-return-item',
  templateUrl: './return-item.component.html',
  styleUrls: ['./return-item.component.css']
})
export class ReturnItemComponent implements OnInit {
  public dataReturned;
  
  public date_1;
  public year;
  public month;
  public day;

  public dateDisplayed;

  constructor(private appservice_2: AppService) { }

  ngOnInit() {
    this.date_1=new Date();
    this.day=this.date_1.getDate();
    this.month=this.date_1.getMonth()+1;
    this.year=this.date_1.getFullYear();
    this.dateDisplayed=this.day+"/"+this.month+"/"+this.year;
  }

  submitData(form: NgForm){




    this.appservice_2.confirmReturnItem(form.value).subscribe((data:any)=>{

      this.date_1=new Date();
      console.log(this.date_1)
      console.log(data.content)

      if((data.content.includes("Book to be returned"))||(data.content.includes("DVD to be returned"))){
        var x=confirm(data.content)
        if(x){
          console.log((form.value));
          this.appservice_2.returnItem((form.value)).subscribe((data:any)=>{
            this.dataReturned=data.content;
            alert(data.content);
            console.log(data.content);
          })
        }else{
          console.log("no")
        }
      } else if(data.content=="Book is not burrowed so it cannot be returned!!"){
        alert("Book is not burrowed so it cannot be returned!!")
      } else if(data.content=="DVD is not burrowed so it cannot  be returned!!"){
        alert("DVD is not burrowed so it cannot  be returned!!")
      } else if(data.content=="Item does not exist"){
        alert("Item does not exist")

      }else if(data.content=="Need Numerical ID") {
        alert("Need Numerical ID")
      }
    })


  }

}
