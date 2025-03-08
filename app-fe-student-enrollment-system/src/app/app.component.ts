import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
  title = 'app-fe-student-enrollment-system';
  curso = 'Full-Stack Java ( Spring Boot & Angular 18)'
  nombre='Aristedes Novoa'


}
