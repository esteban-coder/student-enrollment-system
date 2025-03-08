import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Enrollment } from '../interfaces/enrollment';
import { EnrollmentRequest } from '../interfaces/enrollment-request';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {

    uri_base = `${environment.URL_BASE}/api/v1/enrollments`

    http= inject(HttpClient)

    searchAllEnrollmentHeaders(studentCode: string): Observable<Enrollment[]> {
      let uri = `${this.uri_base}/searchAllHeaders?studentCode=${studentCode}`;
      return this.http.get<Enrollment[]>(uri);
    }

    searchEnrollment(studentCode: string, academicPeriod: string): Observable<Enrollment>{
      let uri = `${this.uri_base}/searchBy?studentCode=${studentCode}&academicPeriod=${academicPeriod}`;
      return this.http.get<Enrollment>(uri);
    }

    save(enrollmentRequest: EnrollmentRequest): Observable<{data:Enrollment}>{
      let uri = `${this.uri_base}/enroll`;
      return this.http.post<{data:Enrollment}>(uri, enrollmentRequest);
    }

    delete(id?: number): Observable<string> {
      const uri = `${this.uri_base}/${id}`
      return this.http.delete<string>(uri);
    }
}
