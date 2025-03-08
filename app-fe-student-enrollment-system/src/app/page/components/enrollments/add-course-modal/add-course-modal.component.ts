import { Component, EventEmitter, OnInit } from '@angular/core';
import { CourseWithSections } from '../../../interfaces/course-with-sections';
import { Section } from '../../../interfaces/section';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { CourseService } from '../../../services/course.service';
import { SectionWithCourse } from '../../../interfaces/section-with-course';
import { Course } from '../../../interfaces/course';

@Component({
  selector: 'app-add-course-modal',
  standalone: true,
  imports: [],
  templateUrl: './add-course-modal.component.html',
  styleUrl: './add-course-modal.component.css'
})
export class AddCourseModalComponent implements OnInit {

  academicPeriod: string = '';
  courses: Array<CourseWithSections> = [];
  filteredCourses: CourseWithSections[] = [];
  expandedCourseId: number | null = null;
  selectedSection: Section | null = null;
  selectedCourse: CourseWithSections | null = null;
  sectionSelected: EventEmitter<SectionWithCourse> = new EventEmitter();

  constructor(
    public bsModalRef: BsModalRef,
    private courseService: CourseService
  ) { }

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.getAvailableCourses(this.academicPeriod)
      .subscribe({
        next: (courses) => {
          this.courses = courses;
          this.filteredCourses = [...this.courses];
        },
        error: (error) => console.error('Error loading courses:', error)
      });
  }

  toggleCourseExpansion(courseId: number): void {
    if(this.expandedCourseId == courseId){
      this.expandedCourseId = null;
    }
    else{
      this.expandedCourseId = courseId;
    }
  }

  selectSection(section: Section, course: CourseWithSections): void {
    this.selectedSection = section;
    this.selectedCourse = course;
    console.log('asignando...')
  }

  addToEnrollment(): void {
    if(this.selectedSection && this.selectedCourse){
      const section: SectionWithCourse = {...this.selectedSection, course: this.selectedCourse};
      this.sectionSelected.emit(section);
      this.bsModalRef.hide();
    }
  }

  applyFilter(event: any): void {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();

    if (!filterValue) {
      this.filteredCourses = [...this.courses];
      console.log('filteredCourses');
      return;
    }

    this.filteredCourses = this.courses.filter(course => 
      course.name.toLowerCase().includes(filterValue) || 
      course.courseCode.toLowerCase().includes(filterValue)
    );
  }

}
