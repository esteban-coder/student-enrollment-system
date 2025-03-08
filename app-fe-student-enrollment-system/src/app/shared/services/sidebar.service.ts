import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class SidebarService {

  menu: any = [

    {
      titulo: "Panel",
      isSubMenu: false,
      url: '/page/panel',
      idItem:1,
      subMenu: []
    },
    {
      titulo: "Maintenance",
      isSubMenu: true,
      url: '/page/mantenimiento',
      idItem:2,
      subMenu: [
        { titulo: "Instructors", url: "/page/mantenimiento/instructors" },
        { titulo: "Courses", url: "/page/mantenimiento/courses" },
        { titulo: "Sections", url: "/page/mantenimiento/sections" },
        { titulo: "Students", url: "/page/mantenimiento/students" },
      ]
    },
    {
      titulo: "Security",
      isSubMenu: true,
      url: '/page/seguridad',
      idItem:2,
      subMenu: [
        { titulo: "Usuarios", url: "/page/seguridad/usuarios" },
        { titulo: "Roles", url: "/page/seguridad/roles" },
      ]
    },
    {
      titulo: "Process",
      isSubMenu: true,
      url: '/page/enrollments',
      idItem:3,
      subMenu: [
        { titulo: "Enrollment", url: "/page/procesos/enrollments/listado" },
      ]
    },

  ];
  constructor() {}
}
