import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { Pacijent } from '../../../models/pacijent';
import { Odeljenje } from '../../../models/odeljenje';
import { Dijagnoza } from '../../../models/dijagnoza';
import { PacijentService } from '../../../services/pacijent-service';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { DijagnozaService } from '../../../services/dijagnoza-service';

@Component({
  selector: 'app-pacijent-dialog',
  imports: [
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './pacijent-dialog.html',
  styleUrl: './pacijent-dialog.css'
})
export class PacijentDialog implements OnInit {

  public flag!: number;
  public odeljenja: Odeljenje[] = [];
  public dijagnoze: Dijagnoza[] = [];

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<PacijentDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Pacijent,
    public pacijentService: PacijentService,
    public odeljenjeService: OdeljenjeService,
    public dijagnozaService: DijagnozaService
  ) { }

  ngOnInit(): void {
    this.loadOdeljenja();
    this.loadDijagnoze();
  }

  public loadOdeljenja(): void {
    this.odeljenjeService.getAllOdeljenja().subscribe({
      next: (data) => {
        this.odeljenja = data;
      },
      error: (error) => {
        console.error('Error fetching odeljenja:', error);
      }
    });
  }

  public loadDijagnoze(): void {
    this.dijagnozaService.getAllDijagnoze().subscribe({
      next: (data) => {
        this.dijagnoze = data;
      },
      error: (error) => {
        console.error('Error fetching dijagnoze:', error);
      }
    });
  }

  public compare(a: any, b: any): boolean {
    return a && b && a.id === b.id;
  }

  public add(): void {
    this.pacijentService.addPacijent(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno dodat pacijent: ' + this.data.ime, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error adding pacijent:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom dodavanja novog pacijenta.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public update(): void {
    this.pacijentService.updatePacijent(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno izmenjen pacijent: ' + this.data.ime, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error updating pacijent:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom izmene pacijenta.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public delete(): void {
    this.pacijentService.deletePacijent(this.data.id).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno obrisan pacijent: ' + this.data.ime, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error deleting pacijent:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom brisanja pacijenta.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste.', 'Zatvori', { duration: 1000 });
  }

}
