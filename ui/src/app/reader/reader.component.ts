import {Component, OnInit} from '@angular/core';
import {AppService} from '../app.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-reader',
  templateUrl: './reader.component.html',
  styleUrls: ['./reader.component.css']
})
export class ReaderComponent implements OnInit {

  public ReaderList;

  constructor(private appservice_2: AppService) {
  }

  ngOnInit() {
    this.getList();
  }

  submitData(form: NgForm) {
    this.appservice_2.confirmAddReader((form.value)).subscribe((data: any) => {
      console.log("Hi")
      if (data.content.includes("success")) {
        var x = confirm("Are you sure you want to add?");
        if (x) {
          this.appservice_2.AddReader((form.value)).subscribe((data: any) => {
            alert(data.content)
          })
        } else {
          console.log("No selected")
        }
      } else if (data.content == "Need Numerical ID") {
        alert("Need Numerical ID")
      } else if (data.content == "Need Valid Phone Number") {
        alert("Need Valid Phone Number")
      } else if (data.content == "Id already exists") {
        alert("Id already exists")
      }
    })
  }

  getList() {
    this.appservice_2.getReaderList().subscribe((data: any) => {
      this.ReaderList = data;
      console.log(data);
    })
  }

}
