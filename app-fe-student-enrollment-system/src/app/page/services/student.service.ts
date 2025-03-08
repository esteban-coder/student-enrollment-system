import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../interfaces/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  uri_base = `${environment.URL_BASE}/api/v1/students`

  constructor(private http: HttpClient) { }

  findByNombre(name: string): Observable<Student[]> {
    const uri = `${this.uri_base}/search-like-name?name=${name}`
    console.log(uri)
    return this.http.get<Student[]>(uri);
  }

  delete(id: number): Observable<void> {
    const uri = `${this.uri_base}/${id}`
    return this.http.delete<void>(uri);
  }

  findById(id: number): Observable<Student> {
    const uri = `${this.uri_base}/${id}`
    console.log(uri)
    return this.http.get<Student>(uri);
  }

  findByStudentCode(studentCode: string): Observable<Student> {
    const uri = `${this.uri_base}/search-by-studentcode?studentCode=${studentCode}`
    console.log(uri)
    return this.http.get<Student>(uri);
  }

  save(student: Student): Observable<Student> {
    return this.http.post<Student>(this.uri_base,student);
  }

  update(id: number,student: Student): Observable<Student> {
    const uri = `${this.uri_base}/${id}`
    return this.http.put<Student>(uri,student);
  }

}
