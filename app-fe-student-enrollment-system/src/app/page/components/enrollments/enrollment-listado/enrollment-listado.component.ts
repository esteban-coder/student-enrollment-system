import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EnrollmentService } from '../../../services/enrollment.service';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { PageChangedEvent, PaginationModule } from 'ngx-bootstrap/pagination';
import { Enrollment } from '../../../interfaces/enrollment';
import { enrollmentStatusMap } from '../../../constants/enrollment-status';

@Component({
  selector: 'app-enrollment-listado',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    PaginationModule
  ],
  templateUrl: './enrollment-listado.component.html',
  styleUrl: './enrollment-listado.component.css'
})
export class EnrollmentListadoComponent implements OnInit {

  enrollments: Array<Enrollment> = [];

  frmListado: FormGroup = new FormGroup({});
  currentPage = 0;

  pagedItems:Array<Enrollment>=[];

  itemsPerPage=5;

  totalItems?:number;

  enrollmentStatusMap = enrollmentStatusMap;

  constructor(
    private enrollmentService: EnrollmentService,
    private formBuilder: FormBuilder,
    private router:Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    console.log('...ngOnInit ')
    this.createForm();
    this.buscar()
  }

  nuevo() {
    this.router.navigate(["/page/procesos/enrollments/registro"])
  }

  limpiar() {
    this.fr['studentCode'].setValue('')
    this.buscar()
    //this.enrollments=[]
  }

  eliminar(id:number, idMatricula:number ) {

    Swal.fire({
      title: "Aviso",
      text: "Confirma la eliminación de la matricula: "+ idMatricula  + "!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Si!",
      cancelButtonText:"No"
    }).then((result) => {
      if (result.isConfirmed) {
        this.enrollmentService.delete(id).subscribe(
          {
            next: (res) => {
              console.log(res)
              this.enrollments = this.enrollments.filter(item => item.id != id);
              this.toastr.success('Éxito al eliminar la matricula', 'Aviso');
            },
            error: (err: any) => {
              this.toastr.error('Error al eliminar la matricula', 'Error');
            },
            complete: () => {

            }
          }
        )
      }
    });
  }

  buscar() {
    const studentCode = this.fr['studentCode'].value

    this.enrollmentService.searchAllEnrollmentHeaders(studentCode).subscribe(
      {
        next: (res) => {
          console.log(res)
          this.enrollments = res
          this.pagedItems = this.enrollments.slice(0, this.itemsPerPage);
        },
        error: (err: any) => {

        },
        complete: () => {

        }
      }

    )
  }

  createForm() {
    this.frmListado = this.formBuilder.group(
      {
        studentCode: ''
      }
    );
  }

  pageChanged(event: PageChangedEvent): void {
    const startItem = (event.page - 1) * this.itemsPerPage;
    const endItem = event.page * this.itemsPerPage;
    // this.currentPage = event.page - 1;
    this.pagedItems = this.enrollments.slice(startItem, endItem);
  }

  // Alias del formulario
  get fr(): { [key: string]: AbstractControl } {
    return this.frmListado.controls;
  }

}
