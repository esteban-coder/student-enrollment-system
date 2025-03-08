import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../shared/components/header/header.component';
import { SidebarComponent } from '../shared/components/sidebar/sidebar.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-page',
  standalone: true,
  imports: [
    HeaderComponent,
    SidebarComponent,
    RouterOutlet

  ],
  templateUrl: './page.component.html',
  styles: [
  ]
})
export class PageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
