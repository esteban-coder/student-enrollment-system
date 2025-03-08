// import { APP_BASE_HREF } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { SidebarService } from '../../services/sidebar.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  menuItems: any[];

  constructor(private sidebarService:SidebarService/*,
    @Inject(APP_BASE_HREF) public baseHref:string*/) {
    this.menuItems = this.sidebarService.menu;
   }

  ngOnInit(): void {

  }

}
