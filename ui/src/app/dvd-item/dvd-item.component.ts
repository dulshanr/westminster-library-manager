import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {NgForm} from "@angular/forms";
import {AppService} from "../app.service";

@Component({
  selector: 'app-dvd-item',
  templateUrl: './dvd-item.component.html',
  styleUrls: ['./dvd-item.component.css']
})
export class DvdItemComponent implements OnInit {

  constructor(private router: Router,private appservice_2: AppService) { }
public datareturned_1;
public dvdSlots;
  ngOnInit() {
      this.getDVdSlots();
  }

  changeScene(value_3){
    this.router.navigate([value_3]);
}

  submitData(form: NgForm){




    if(+this.dvdSlots>1){

    


    this.appservice_2.confirmAddItem(form.value).subscribe((data:any)=>{
      if(data.content=="success"){
        var x=confirm("Are you sure you want to add the DVD? "+form.value.title)
        if(x){
          console.log((form.value));
          this.appservice_2.addDVD((form.value)).subscribe((data:any)=>{
            this.datareturned_1=data.content;
            console.log(data.content);
          })
        }else{
          console.log("no")
        }
      } else if(data.content=="ID already exists"){
        alert("ID already exists")
      } else if(data.content=="Need Numerical ID"){
        alert("ID has to be in the number format")
      }
    })

    // console.log((form.value));
    // this.appservice_2.addDVD((form.value)).subscribe((data:any)=>{
    //   this.datareturned_1=data.content;
    //   console.log(data.content);
    // })

  } else{
    alert("Max number of DVD slots reached")
  }
  }

  getDVdSlots(): void{
    
    this.appservice_2.availableDvdSlots().subscribe((data:any)=>{
      this.dvdSlots=data.content;
    })
  }

}
