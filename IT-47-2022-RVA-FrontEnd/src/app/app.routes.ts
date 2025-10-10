import { Routes } from '@angular/router';
import { Bolnica } from './components/main/bolnica/bolnica-component';
import { OdeljenjeComponent } from './components/main/odeljenje/odeljenje-component';
import { Pacijent } from './components/main/pacijent/pacijent-component';
import { Home } from './components/utility/home/home';
import { Author } from './components/utility/author/author';
import { About } from './components/utility/about/about';

export const routes: Routes = [
  { path: 'bolnica', component: Bolnica },
  { path: 'odeljenje', component: OdeljenjeComponent },
  { path: 'pacijent', component: Pacijent },
  { path: 'home', component: Home },
  { path: 'author', component: Author },
  { path: 'about', component: About },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];
