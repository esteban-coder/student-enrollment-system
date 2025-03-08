import { Routes } from '@angular/router';
import { LoginComponent } from './public/login/login.component';
import { PageComponent } from './page/page.component';
import { PanelComponent } from './page/components/panel/panel.component';
import { NoPageFoundComponent } from './shared/components/no-page-found/no-page-found.component';
import { StudentRegistroComponent } from './page/components/students/student-registro/student-registro.component';
import { EnrollmentRegistroComponent } from './page/components/enrollments/enrollment-registro/enrollment-registro.component';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },

  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'page',
    component: PageComponent,

    children: [
      {
        path: 'panel',
        component: PanelComponent,
        data: { titulo: 'Panel', modulo: 'Index' },
      },

      // Mantenimientos
      // Students
      {
        path: 'mantenimiento/students',
        loadComponent: () =>
          import(
            './page/components/students/student-listado/student-listado.component'
          ).then((p) => p.StudentListadoComponent),
        //component: StudentListadoComponent,
        data: { titulo: 'Students', modulo: 'Mantenimiento' },
      },

      {
        path: 'mantenimiento/students/registro',
        component: StudentRegistroComponent,
        data: { titulo: 'Students', modulo: 'Mantenimiento' },
      },
      {
        path: 'mantenimiento/students/registro/:id',
        component: StudentRegistroComponent,
        data: { titulo: 'Students', modulo: 'Mantenimiento' },
      },

      // Enrollments
      // Productos

      {
        path: 'procesos/enrollments/listado',
        loadComponent: () =>
          import(
            './page/components/enrollments/enrollment-listado/enrollment-listado.component'
          ).then((p) => p.EnrollmentListadoComponent),
        data: { titulo: 'Enrollments', modulo: 'Procesos' },
      },

      {
        path: 'procesos/enrollments/registro',
        loadComponent: () =>
          import(
            './page/components/enrollments/enrollment-registro/enrollment-registro.component'
          ).then((p) => p.EnrollmentRegistroComponent),
        data: { titulo: 'Enrollments - Registro', modulo: 'Procesos' },
      },
      {
          path: "procesos/enrollments/registro/:studentCode",
          component: EnrollmentRegistroComponent,
          data: { titulo: "Enrollments", modulo: "Procesos" },
      },

      { path: '', redirectTo: 'panel', pathMatch: 'full' },

      { path: '**', component: NoPageFoundComponent },
    ],
  }
];
