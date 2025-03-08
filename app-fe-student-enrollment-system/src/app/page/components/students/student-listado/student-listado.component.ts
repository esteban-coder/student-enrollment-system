import { Component, OnInit } from '@angular/core';
import { StudentService } from '../../../services/student.service';
import { Student } from '../../../interfaces/student';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PageChangedEvent, PaginationModule } from 'ngx-bootstrap/pagination';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-student-listado',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    PaginationModule,
    FormsModule
  ],
  templateUrl: './student-listado.component.html',
  styleUrl: './student-listado.component.css'
})
export class StudentListadoComponent implements OnInit {

  students: Array<Student> = [];

  frmListado: FormGroup = new FormGroup({});

  currentPage = 1;
  pagedItems:Array<Student>=[];
  itemsPerPage=5;
  totalItems?:number;

  constructor(
    private studentService: StudentService,
    private formBuilder: FormBuilder,
    private router:Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.createForm();
    this.buscar();
  }

  createForm(): void {
    this.frmListado = this.formBuilder.group(
      {
        name: ['']
      }
    );
  }

  get fr(): { [key: string]: AbstractControl } {
    return this.frmListado.controls;
  }

  buscar(): void {
    const name = this.fr['name'].value;

    this.studentService.findByNombre(name).subscribe(
      {
        next: (res) => {
          this.students = res;
          this.pagedItems = this.students.slice(0, this.itemsPerPage);
        },
        error: (err: any) => {

        },
        complete: () => {

        }
      }
    );

  }

  limpiar() {
    this.fr['name'].setValue('');
    this.buscar();
  }

  eliminar(id:number, name:string ) {

    Swal.fire({
      title: "Aviso",
      text: "Confirma la eliminación del producto "+ name + "!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Si!",
      cancelButtonText:"No"
    }).then((result) => {
      if (result.isConfirmed) {

        this.studentService.delete(id).subscribe(
          {
            next: (res) => {
              console.log(res)
              //this.buscar()
              //this.students = this.students.filter(item => item.id != id);
              this.pagedItems = this.pagedItems.filter(item => item.id != id);
              this.toastr.success('Successfully deleted the student', 'Aviso');
            },
            error: (err: any) => {
              this.toastr.error('Error deleting student', 'Error');
            },
            complete: () => {}
          }
        )
      }
    });
  }

  nuevo(){
    this.router.navigate(["/page/mantenimiento/students/registro"])
  }


  pageChanged(event: PageChangedEvent): void {
    const startItem = (event.page - 1) * this.itemsPerPage;
    const endItem = event.page * this.itemsPerPage;
    this.pagedItems = this.students.slice(startItem, endItem);
  }

}
