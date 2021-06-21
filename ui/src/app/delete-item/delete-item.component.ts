import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {AppService} from '../app.service';

@Component({
  selector: 'app-delete-item',
  templateUrl: './delete-item.component.html',
  styleUrls: ['./delete-item.component.css']
})
export class DeleteItemComponent implements OnInit {
  private datareturned;
  private datareturned_confirm;
  public bookSlots;
  public dvdSlots;

  constructor(private router: Router, private appservice_2: AppService) {
  }

  ngOnInit() {
    this.getBookslots();
    this.getDvdSlots();
  }

  submitData(form: NgForm) {

    this.appservice_2.returnDeleteConfirm((form.value)).subscribe((data: any) => {
      console.log("Hi")
      if (data.content.includes("Are you sure you want to delete ")) {
        var x = confirm(data.content);
        if (x) {
          this.appservice_2.deleteItem((form.value)).subscribe((data: any) => {
            this.datareturned = data.content;
          })
        } else {
          console.log("No selected")
        }
      } else if (data.content == "Need Numerical ID") {
        alert("Need Numerical ID")
      } else if (data.content == "ID does not exist") {
        alert("ID does not exist")
      }
    })


    console.log((form.value));
    this.appservice_2.deleteItem((form.value)).subscribe((data: any) => {
      this.datareturned = data.content;

    })
    this.getBookslots();
    this.getDvdSlots();

  }


  confirmSubmission(form: NgForm) {


  }


  // this.datareturned_confirm=data.content;
  // console.log(this.datareturned_confirm);
  // if(confirm(this.datareturned_confirm)){
  //   this.submitData(form);
  // } else{
  //   console.log("not pressed ok");
  // }


  public getBookslots(): void {
    this.appservice_2.availableBookSlots().subscribe((data: any) => {
      this.bookSlots = data.content;
    })
  }

  public getDvdSlots(): void {
    this.appservice_2.availableDvdSlots().subscribe((data: any) => {
      this.dvdSlots = data.content;
    })
  }


}
