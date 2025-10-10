import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-about',
  imports: [MatToolbarModule, MatIconModule],
  templateUrl: './about.html',
  styleUrl: './about.css'
})
export class About {

}
