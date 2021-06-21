import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/index';


/**
 * Class representing application service.
 *
 * @class AppService.
 */
@Injectable()
export class AppService {
  private serviceUrl = '/api/summary';
  private dataPostTestUrl = '/api/postTest';

  constructor(private http: HttpClient) {

  }

  /**
   * Makes a http get request to retrieve the welcome message from the backend service.
   */
  public getWelcomeMessage() {
    return this.http.get(this.serviceUrl).pipe(
      map(response => response)
    );
  }

  /**
   * Makes a http post request to send some data to backend & get response.
   */
  public sendData(): Observable<any> {
    return this.http.post(this.dataPostTestUrl, {});
  }

  public sendData_2(): Observable<any> {
    return this.http.post('/postTest2', {});
  }

  public getBooklist(jsonObject): Observable<any>{
    return this.http.get('/assets/data/bookList.json');
  }

  public getBooklist_1(): Observable<any>{
    return this.http.get('/assets/data/SingleBook.json');
  }

  public getBooklist_1234(): Observable<any>{
    return this.http.post('/getTestX',{});
  }

  public postFormdata(): Observable<any>{
    return this.http.post('/formData',{
      name:"mark",
      age:41
    });
  }

  public addBook(jsonObject): Observable<any>{
    return this.http.post('/addItem1',jsonObject);
  }

  public addDVD(jsonObject): Observable<any>{
    return this.http.post('/addItem2',jsonObject);
  }

  public deleteItem(jsonObject): Observable<any>{
    return this.http.post('/deleteItem',jsonObject);
  }

  public getList(): Observable<any>{
    return this.http.post('/getList',{});
  }

  public getBookList_generate(): Observable<any>{
    return this.http.post('/getBook',{});
  }
  public getDVDList_generate(): Observable<any>{
    return this.http.post('/getDVD',{});
  }

  public borrowItem(jsonObject): Observable<any>{
    return this.http.post('/borrowItem',jsonObject);
  }

  public returnItem(jsonObject): Observable<any>{
    return this.http.post('/returnItem',jsonObject);
  }
  public returnDeleteConfirm(jsonObject): Observable<any>{
    return this.http.post('/deleteItemConfirm',jsonObject);
  }
  public availableBookSlots(): Observable<any>{
    return this.http.post('/displayBookSlots',{});
  }

  public availableDvdSlots(): Observable<any>{
    return this.http.post('/displayDvdSlots',{});
  }

  public confirmAddItem(jsonObject): Observable<any>{
    return this.http.post('/confirmAddItem',jsonObject);
  }

  public confirmBorrowItem(jsonObject): Observable<any>{
    return this.http.post('/confirmBorrowItem',jsonObject);
  }

  public confirmReturnItem(jsonObject): Observable<any>{
    return this.http.post('/confirmReturnItem',jsonObject);
  }

  public getDueBooks(jsonObject): Observable<any>{
    return this.http.post('/getDueBooks',jsonObject);
  }

  public searchItem(jsonObject): Observable<any>{
    return this.http.post('/searchItem',jsonObject);
  }

  public confirmAddReader(jsonObject): Observable<any>{
    return this.http.post('/confirmAddReader   ',jsonObject);
  }

  public AddReader(jsonObject): Observable<any>{
    return this.http.post('/addReader',jsonObject);
  }

  public getReaderList(): Observable<any>{
    return this.http.post('/getReaderList',{});
  }

  public getAverageDays(jsonObject): Observable<any>{
    return this.http.post('/getAverageDay',jsonObject);
  }


  public addToQue(jsonObject): Observable<any>{
    return this.http.post('/addToQue',jsonObject);
  }










  // public getStringPassed(){
  //   return this.http.post('/deleteItem',{});
  // }




}
