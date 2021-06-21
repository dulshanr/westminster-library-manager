import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgForm} from '@angular/forms'
import {AppService} from "../app.service";


@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {
  public statement = "Please Enter";
  public error = false;
  public datareturned;
  public bookSlots;

  constructor(private router: Router, private appservice_2: AppService) {
  }

  ngOnInit() {
    this.getBookSlots();
  }

  name123() {

  }

  changeScene(value_3) {
    this.router.navigate([value_3]);
  }

  submitData(form: NgForm) {

    if (+this.bookSlots > 1) {


      this.appservice_2.confirmAddItem(form.value).subscribe((data: any) => {
        if (data.content == "success") {
          var x = confirm("Are you sure you want to add the book? " + form.value.title)
          if (x) {
            console.log((form.value));
            this.appservice_2.addBook((form.value)).subscribe((data: any) => {
              this.datareturned = data.content;
              console.log(data.content);
            })
          } else {
            console.log("no")
          }
        } else if (data.content == "ID already exists") {
          alert("ID already exists")
        } else if (data.content == "Need Numerical ID") {
          alert("ID has to be in the number format")
        }
      })
    } else {
      alert("Maximum limit Reached")
    }

  }

  getBookSlots(): void {
    this.appservice_2.availableBookSlots().subscribe((data: any) => {
      this.bookSlots = data.content;
    })
  }
}
