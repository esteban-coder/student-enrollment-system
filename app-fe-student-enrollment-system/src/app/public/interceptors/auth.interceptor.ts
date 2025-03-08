import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  console.log('authInterceptor....');

  const authToken = sessionStorage.getItem('token') || '';

  console.log(authToken)

  const authReq = req.clone({
    setHeaders: {
      //Authorization: `Bearer ${authToken}`
      Authorization: authToken
    }
  });

  return next(authReq);
};
