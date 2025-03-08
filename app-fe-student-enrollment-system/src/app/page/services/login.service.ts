
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Login } from '../interfaces/login';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  url = `${environment.URL_BASE}/api/v1/auth/login`
  constructor(private httpClient: HttpClient) {

  }

  login(login: Login): Observable<any> {
    //console.log(this.url)
    //console.log(JSON.stringify(login))
    //return this.httpClient.post<any>(this.url,login)
    return this.httpClient.post(this.url, login, { observe: 'response' })
      .pipe(map(res => {

        //console.log('res -> ' + JSON.stringify(res));
        //console.log(res);

        const token = res.headers.get('Authorization') || '';

        console.log('authorization token -> ' + token);

        sessionStorage.setItem('token', token); // Session Storage y Local Storage

        return res;
      }
      ));
  }
}
