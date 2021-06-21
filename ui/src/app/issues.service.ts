import { Injectable } from '@angular/core';
import{HttpClient} from '@angular/common/http';


import { Observable } from 'rxjs';

// import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class IssueService {


  private _url: string="/assets/data/employees.json";
  constructor(private http: HttpClient) { }



  public sendSome(): Observable<any>{
    return this.http.get('/report_see');
  }




  // callClient(){
  //   console.log('http://localhost:9000/books1');

  //   return this.http.get('http://localhost:9000/issues/books1');

  // }

  // getIssues(){
  //    return this.http.get('${this.url}/issues')
  // }

  // getIssueById(id){
  //   return this.http.get('${this.url}/issues/${id}')
  // }




  // updateIssue(id,title,responsible, description, severity,status){
  //   const issue= {
  //     title: title,
  //     responsible: responsible,
  //     description: description,
  //     severity: severity,
  //     status: status

  //   };

  //   return this.http.post('${this.url}/issues/update/${id}',issue)
  // }

  // addIssue(title,responsible, description, severity){
  //   const issue= {
  //     title: title,
  //     responsible: responsible,
  //     description: description,
  //     severity: severity

  //   };
  //   return this.http.post('${this.url}/issues/add',issue);
  // }

  // deleteIssue(id){
  //   return this.http.get('${this.url}/issues/delete/${id}');
  // }



}
