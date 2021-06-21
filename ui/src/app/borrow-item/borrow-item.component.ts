import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AppService} from '../app.service';

@Component({
  selector: 'app-borrow-item',
  templateUrl: './borrow-item.component.html',
  styleUrls: ['./borrow-item.component.css']
})
export class BorrowItemComponent implements OnInit {
  public dataReturned;


  public date_1;
  public year;
  public month;
  public day;

  public dateDisplayed;


  constructor(private appservice_2: AppService) {
  }

  ngOnInit() {

    this.date_1 = new Date();
    this.day = this.date_1.getDate();
    this.month = this.date_1.getMonth() + 1;
    this.year = this.date_1.getFullYear();
    this.dateDisplayed = this.day + "/" + this.month + "/" + this.year;
  }

  submitData(form: NgForm) {

    this.appservice_2.confirmBorrowItem(form.value).subscribe((data: any) => {
      if (data.content.includes("success")) {
        var x = confirm(data.content.substring(9))
        if (x) {
          console.log((form.value));
          this.appservice_2.borrowItem((form.value)).subscribe((data: any) => {
            this.dataReturned = data.content;
            console.log(data.content);
          })
        } else {
          console.log("no")
        }
      } else if (data.content == "ID non-existant") {
        alert("Item ID or Reader ID is incorrect")
      } else if (data.content == "Need Numerical ID") {
        alert("IDS has to be in the number format")
      } else if (data.content.includes("Book not available at the moment, Expected to be available in")) {
        alert(data.content)
      } else if (data.content == "DVD not available at the moment, Expected to be available in") {
        alert(data.content)
      } else if (data.content == "User has already borrowed a book") {
        alert("User has already borrowed a book")
      }
    })

  }
}
