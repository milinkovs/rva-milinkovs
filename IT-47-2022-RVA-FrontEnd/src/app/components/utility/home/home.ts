import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  imports: [MatToolbarModule, MatIconModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

}
