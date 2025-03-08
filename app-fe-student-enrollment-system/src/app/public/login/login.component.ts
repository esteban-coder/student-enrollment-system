import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from '../../page/services/login.service';
import { Login } from '../../page/interfaces/login';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {

  frmLogin!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router) {

  }

  ngOnInit() {
    this.frmLogin = this.formBuilder.group({
      userName: ['ADMIN', [Validators.required, Validators.minLength(5)]],
      password: ['123', [Validators.required, Validators.minLength(3)]]
    });
  }

  onSubmit() {

    const login: Login = {
      userName: this.f['userName'].value,
      password: this.f['password'].value
    };
    
    this.router.navigate(['/page/panel']);

/*     this.loginService.login(login)
      .subscribe({
        next: (res: any) => {
          console.log(res)
          this.router.navigate(['/page/panel']);
          //this.router.navigate(['/producto-listado']);
        },
        error: () => {
        }
      }); */

  }

  get f() {
    return this.frmLogin.controls;
  }

}
