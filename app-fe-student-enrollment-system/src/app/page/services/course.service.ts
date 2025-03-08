import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { CourseWithSections } from "../interfaces/course-with-sections";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class CourseService {

    uri_base = `${environment.URL_BASE}/api/v1/courses`;

    constructor(private http: HttpClient) { }

    // Obtener cursos disponibles para un periodo académico
    getAvailableCourses(academicPeriod: string): Observable<CourseWithSections[]> {
        return this.http.get<CourseWithSections[]>(`${this.uri_base}/findByAcademicPeriod?academicPeriod=${academicPeriod}`);
    }

}