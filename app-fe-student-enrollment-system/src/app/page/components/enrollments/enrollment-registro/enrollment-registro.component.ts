import { Component, OnInit, TemplateRef } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { EnrollmentService } from '../../../services/enrollment.service';
import { StudentService } from '../../../services/student.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageChangedEvent, PaginationModule } from 'ngx-bootstrap/pagination';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { combineLatest, Subscription } from 'rxjs';
import { Enrollment } from '../../../interfaces/enrollment';
import { EnrollmentDetail } from '../../../interfaces/enrollment-detail';
import { AddCourseModalComponent } from '../add-course-modal/add-course-modal.component';
import { Section } from '../../../interfaces/section';
import { SectionWithCourse } from '../../../interfaces/section-with-course';
import { CourseWithSections } from '../../../interfaces/course-with-sections';
import { EnrollmentRequest } from '../../../interfaces/enrollment-request';
import { EnrollmentDetailRequest } from '../../../interfaces/enrollment-detail-request';
import { enrollmentDetailStatusMap } from '../../../constants/enrollment-detail-status';

@Component({
  selector: 'app-enrollment-registro',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PaginationModule,
    //CurrencyPipe
  ],
  templateUrl: './enrollment-registro.component.html',
  styleUrl: './enrollment-registro.component.css'
})
export class EnrollmentRegistroComponent implements OnInit {

  currentAcademicPeriod = '2024-1';
  frmCabStudent!: FormGroup;
  enrollment: Enrollment | null = null;
  details: Array<EnrollmentDetail> = [];
  modalRef?: BsModalRef;

  totals: { totalCredits: number, totalCourses: number } = { totalCredits: 0, totalCourses: 0 };

  private subscriptions: Subscription[] = [];

  enrollmentDetailStatusMap = enrollmentDetailStatusMap;

  //DI
  constructor(
    private enrollmentService: EnrollmentService,
    private studentService: StudentService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private modalService: BsModalService
  ) {
    this.createFormCabStudent();
  }

  createFormCabStudent() {
    this.frmCabStudent = this.formBuilder.group({
      studentCode: [''],
      dni: [{ value: '20544978359', disabled: true }],
      studentId: [{ value: '', disabled: true }],
      fullName: [{ value: '', disabled: true }],
      email: [{ value: '', disabled: true }],
      phoneNumber: [{ value: '', disabled: true }],
      address: [{ value: '', disabled: true }],
      comments: [{ value: '', disabled: false }, [Validators.required, Validators.minLength(10), Validators.maxLength(50)]],
    });
  }

  get fpc(): { [key: string]: AbstractControl } {
    return this.frmCabStudent.controls;
  }

  ngOnInit(): void {
    combineLatest([
      this.activatedRoute.params,
      this.activatedRoute.queryParams
    ]).subscribe(([params, queryParams]) => {
      if (params['studentCode'] && queryParams['academicPeriod']) {
        const studentCode = params['studentCode'];
        const academicPeriod = queryParams['academicPeriod'];

        this.findByStudentCodeAndAcademicPeriod(studentCode, academicPeriod);
      }
    });
  }

  findStudentByStudentCode() {
    const studentCode = this.fpc['studentCode'].value;
    console.log('studentCode -> ' + studentCode);

    // validar si el student ya tiene una matricula en curso

    this.studentService.findByStudentCode(studentCode).subscribe(
      {
        next: (res) => {

          console.log('res : ', res);
          const student = res;

          this.fpc['studentId'].setValue(student.id);
          this.fpc['dni'].setValue(student.dni)
          this.fpc['fullName'].setValue(student.fullName)
          this.fpc['phoneNumber'].setValue(student.phoneNumber)
          this.fpc['email'].setValue(student.email)
          this.fpc['address'].setValue(student.address)
        },
        error: (err) => {
          console.error('Error occurs', err);
          this.toastr.error('Error registering student: ' + err.error.message, 'Error');
        }

      }
    );
  }

  findByStudentCodeAndAcademicPeriod(studentCode: string, academicPeriod: string) {
    this.enrollmentService.searchEnrollment(studentCode, academicPeriod).subscribe(
      {
        next: (res) => {

          // Cabecera
          this.enrollment = res;
          // Detalle
          // Formulario Detalle Matricula
          this.details = this.enrollment.details;
          console.log('enrollment : ', this.enrollment);
          // console.log('details : ', this.details);

          // Formulario Student
          this.fpc['comments'].setValue(res.comments);
          this.fpc['fullName'].setValue(res.student.fullName);
          this.fpc['studentId'].setValue(res.student.id);
          this.fpc['dni'].setValue(res.student.dni);
          this.fpc['phoneNumber'].setValue(res.student.phoneNumber);
          this.fpc['email'].setValue(res.student.email);
          this.fpc['studentCode'].setValue(res.student.studentCode);

          this.calcularTotales();

          this.fpc['studentCode'].disable();
        },
        error: (err) => {
          console.error('Error occurs', err);
        }
      }
    );
  }

  calcularTotales(): void {
    this.totals.totalCredits = this.enrollment!.totalCredits;
    this.totals.totalCourses = this.enrollment!.totalEnrolledCourses;
  }

  calcularTotales2(): void {
    this.totals = {
      totalCredits: this.details.reduce((sum, detail) => sum + (detail.credits || 0), 0),
      totalCourses: this.details.length
    };
  }

  remove(detail: EnrollmentDetail) {
    this.details = this.details.filter(d => d !== detail);
  }

  save(): void {

    if (!this.frmCabStudent.valid) {
      this.toastr.error('Please complete all the required fields', 'Validation error');
      this.frmCabStudent.markAllAsTouched();
      return;
    }

    // console.log('1:',this.enrollment?.student.id);
    // console.log('2:',this.enrollment?.student?.id);
    // console.log('3:',this.fpc['studentId'].value);
    const studentId = this.enrollment?.student.id || this.fpc['studentId'].value;
    // console.log('studentId:', studentId);

    if (!studentId) {
      this.toastr.error('No se encontró ID de estudiante', 'Error');
      return;
    }

    if (!this.details.length) {
      this.toastr.warning('There must be at least one course added in the enrollment', 'Warning');
      return;
    }

    /*
    const enrollmentDetails: Array<EnrollmentDetailRequest> = [];
    this.details.forEach((detail) => {
      if (detail.status === '-') {
        const detailRequest: EnrollmentDetailRequest = {
          sectionId: detail.section.id
        };
        enrollmentDetails.push(detailRequest);
      }
    });
    */

    // Filtrar solo los detalles nuevos (status = '-')
    const enrollmentDetails: Array<EnrollmentDetailRequest> = this.details
      .filter(detail => detail.status === '-')
      .map(detail => ({ sectionId: detail.section.id })
      );

    if (this.enrollment?.id && !enrollmentDetails.length) {
      this.toastr.warning('No hay cursos nuevos para matricular', 'Advertencia');
      return;
    }

    const enrollmentRequest: EnrollmentRequest = {
      studentId: studentId,  // this.enrollment!.student.id,
      comments: this.fpc['comments'].value,
      enrollmentDetails: enrollmentDetails
    };

    this.enrollmentService.save(enrollmentRequest).subscribe({
      next: (res) => {
        console.log("********* res:", res);
        this.enrollment = res.data; // comparé lo que devolvia /searchBy y /enroll - necesario para que no vuelva a dar click en guardar, y ya se de cuenta que no hubo cursos nuevos
        this.details = this.enrollment.details; // necesario para actualizar el status en la grilla
        this.calcularTotales(); // los calcula de los datos traidos del api, mas seguro que tomarlo del calculo del frontend
        this.toastr.success('Enrollment success', 'Completed');
        // Opcional: redirigir al listado después de guardar
        // this.irListado();
      },
      error: (err) => {
        console.error('-------- error:', err);
        this.toastr.error('Enrollment failed: ' + err.error.message, "Error");
      },
    });

  }

  irListado() {
    this.router.navigate(['/page/procesos/enrollments/listado'])
  }

  openAddCourseModal() {
    // Verificar si hay un estudiante seleccionado
    if (!this.enrollment?.student?.id && !this.fpc['studentId'].value) {
      this.toastr.warning('You must select an student', 'Warning');
      return;
    }

    const initialState = {
      academicPeriod: this.currentAcademicPeriod
    };

    this.modalRef = this.modalService.show(AddCourseModalComponent, {
      initialState,
      class: 'modal-lg',
      animated: true
    });

    /*
    this.modalRef.content.sectionSelected.subscribe((section: SectionWithCourse) => {
      // console.log('********** section', section);
      if (section) {
        const detail: EnrollmentDetail = {
          id: 0,
          section: section,
          credits: section.course.credits,
          status: '-',
          withdrawalDate: '',
          withdrawalReason: '',
          creditsEarned: 0,
          finalGrade: 0,
          gradeStatus: ''
        };

        this.details.push(detail);

        this.totals.totalCredits += detail.credits;
        this.totals.totalCourses += 1;
      }
    });
    */

    const subscription = this.modalRef.content.sectionSelected.subscribe((section: SectionWithCourse) => {
      if (!section) return;

      // Verificar si la sección ya está en los detalles
      const isSectionAlreadyAdded = this.details.some(detail => detail.section.id === section.id);
      if (isSectionAlreadyAdded) {
        this.toastr.warning(`La sección ${section.sectionCode} del curso ${section.course.name} ya ha sido agregada`, 'Warning');
        return;
      }

      // Verificar si la sección ya está en los detalles
      const isCourseAlreadyAdded = this.details.some(detail => detail.section.course.id === section.course.id);
      if (isCourseAlreadyAdded) {
        this.toastr.warning(`La sección ${section.sectionCode} es del curso ${section.course.name}, el cual ya ha sido agregada`, 'Warning');
        return;
      }

      // Agregar el detalle
      this.addEnrollmentDetail(section);

      // Calcular totales
      this.calcularTotales2();
    });

    // Guardar la suscripción para desuscribirse después (en ngOnDestroy)
    this.subscriptions.push(subscription);
  }

  // Método auxiliar para agregar un detalle de matrícula
  private addEnrollmentDetail(section: SectionWithCourse): void {
    const detail: EnrollmentDetail = {
      id: 0,
      section: section,
      credits: section.course.credits,
      status: '-',
      withdrawalDate: '',
      withdrawalReason: '',
      creditsEarned: 0,
      finalGrade: 0,
      gradeStatus: ''
    };

    this.details.push(detail);
  }

  // Agregar en ngOnDestroy para evitar memory leaks
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
