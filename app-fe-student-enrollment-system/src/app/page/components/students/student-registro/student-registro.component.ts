import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { StudentService } from '../../../services/student.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { Student } from '../../../interfaces/student';

@Component({
  selector: 'app-student-registro',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './student-registro.component.html',
  styleUrl: './student-registro.component.css'
})
export class StudentRegistroComponent implements OnInit {

/*   bsConfig: Partial<BsDatepickerConfig> = {
    dateInputFormat: 'YYYY-MM-DD',  // Formato ISO
    adaptivePosition: true,
    containerClass: 'theme-dark-blue',
    showWeekNumbers: false,
    isAnimated: true
  }; */

  frmRegistro: FormGroup = new FormGroup({});

  id: number = 0
  submitted = false;

  constructor(
    private studentService: StudentService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activedRoute: ActivatedRoute,
    private toastr: ToastrService
  ) {

  }


  ngOnInit(): void {
    this.createForm();

    // Manejamos tanto la entrada manual como la selección del datepicker
/*     this.f['admissionDate']?.valueChanges.subscribe(value => {
      if (value instanceof Date) {
        const formattedDate = this.formatDate(value);
        this.f['admissionDate']?.setValue(formattedDate, { emitEvent: false });
      }
    }); */

    this.id = this.activedRoute.snapshot.params["id"];
    if (this.id) {
      this.findById(this.id);
    }

  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  createForm() {
    this.frmRegistro = this.formBuilder.group(
      {
        studentCode: [''],
        dni: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
        fullName: ['Student\'s name', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
        email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
        phoneNumber: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
        address: ['', [Validators.required, Validators.maxLength(200)]],
        birthDate: ['', [Validators.required, Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]], // YYYY-MM-DD format
        gender: ['', [Validators.required, Validators.pattern(/^[MF]$/)]],
        admissionDate: ['', [Validators.required, Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]], // YYYY-MM-DD format
        status: ''
      }
    );
  }

  get f(): { [key: string]: AbstractControl } {
    return this.frmRegistro.controls;
  }

  // Custom error messages
  getErrorMessages(controlName: string): string[] {
    const control = this.f[controlName];
    const messages: string[] = [];

    if (!control || !control.errors) return messages;

    if (control.errors['required']) {
      messages.push(`The ${controlName} is required`);
    }

    switch (controlName) {
      case 'dni':
        if (control.errors['pattern']) {
          messages.push('DNI must be exactly 8 digits');
        }
        break;

      case 'fullName':
        if (control.errors['minlength']) {
          messages.push(`The name must be at least ${control.errors['minlength'].requiredLength} characters`);
        }
        if (control.errors['maxlength']) {
          messages.push(`The name must be at most ${control.errors['maxlength'].requiredLength} characters`);
        }
        break;

      case 'email':
        if (control.errors['email']) {
          messages.push('Please enter a valid email');
        }
        if (control.errors['maxlength']) {
          messages.push(`Email cannot exceed ${control.errors['maxlength'].requiredLength} characters`);
        }
        break;

      case 'phoneNumber':
        if (control.errors['pattern']) {
          messages.push('Phone number must be exactly 9 digits');
        }
        break;

      case 'address':
        if (control.errors['maxlength']) {
          messages.push(`Address cannot exceed ${control.errors['maxlength'].requiredLength} characters`);
        }
        break;

      case 'birthDate':
        if (control.errors['pattern']) {
          messages.push('Birth date must be in YYYY-MM-DD format');
        }
        break;

      case 'gender':
        if (control.errors['pattern']) {
          messages.push('Gender must be M or F');
        }
        break;

      case 'admissionDate':
        if (control.errors['pattern']) {
          messages.push('Admission date must be in YYYY-MM-DD format');
        }
        break;
    }

    return messages;
  }

  findById(id: number) {
    this.studentService.findById(id).subscribe(
      {
        next: (res) => {
          this.frmRegistro.patchValue(res);

        },
        error: (err) => {
          this.toastr.error('Error retrieving student', 'Error');
        },
        complete: () => { }
      }
    );

  }

  irListado() {
    this.router.navigate(["/page/mantenimiento/students"])
  }

  limpiar() {

  }

  grabar() {

    this.submitted = true;

    if (this.frmRegistro.invalid) {
      return;
    }

    const student: Student = {
      id: 0,
      studentCode: this.f['studentCode'].value,
      dni: this.f['dni'].value,
      fullName: this.f['fullName'].value,
      email: this.f['email'].value,
      phoneNumber: this.f['phoneNumber'].value,
      address: this.f['address'].value,
      birthDate: this.f['birthDate'].value,
      gender: this.f['gender'].value,
      admissionDate: this.f['admissionDate'].value,
      statusText: this.f['status'].value
    }
    console.log(student)

    if (this.id) {

      this.studentService.update(this.id, student).subscribe(
        {
          next: (res) => {
            console.log(res)
            this.toastr.success('Successful updating student', 'Aviso');
          },
          error: (err: any) => {
            console.log(err);
            this.toastr.error('Error updating student: ' + err.error.message, 'Error');
          },
          complete: () => {

          }
        }
      )
    } else {
      this.studentService.save(student).subscribe(
        {
          next: (res) => {
            console.log(res)
            console.log("INICIO toastr....................")
            this.toastr.success('Successful registering student', 'Aviso');
            console.log("FIN toastr....................")
          },
          error: (err: any) => {
            console.log(err);
            this.toastr.error('Error registering student: ' + err.error.message, 'Error');
          },
          complete: () => {

          }
        }
      )
    }
  }

}
