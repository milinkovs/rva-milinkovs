import { Routes } from '@angular/router';
import { Bolnica } from './components/main/bolnica/bolnica-component';
import { OdeljenjeComponent } from './components/main/odeljenje/odeljenje-component';
import { Dijagnoza } from './components/main/dijagnoza/dijagnoza';
import { Home } from './components/utility/home/home';
import { Author } from './components/utility/author/author';
import { About } from './components/utility/about/about';

export const routes: Routes = [
  { path: 'bolnica', component: Bolnica },
  { path: 'odeljenje', component: OdeljenjeComponent },
  { path: 'dijagnoza', component: Dijagnoza },
  { path: 'home', component: Home },
  { path: 'author', component: Author },
  { path: 'about', component: About },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];
