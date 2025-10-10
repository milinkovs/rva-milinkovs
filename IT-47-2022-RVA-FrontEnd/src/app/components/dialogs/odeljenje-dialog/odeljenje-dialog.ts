import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Odeljenje } from '../../../models/odeljenje';
import { Bolnica } from '../../../models/bolnica';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { BolnicaService } from '../../../services/bolnica-service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-odeljenje-dialog',
  imports: [
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './odeljenje-dialog.html',
  styleUrl: './odeljenje-dialog.css'
})
export class OdeljenjeDialog implements OnInit {

  public flag!: number;
  public bolnice: Bolnica[] = [];

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<OdeljenjeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Odeljenje,
    public odeljenjeService: OdeljenjeService,
    public bolnicaService: BolnicaService
  ) { }

  ngOnInit(): void {
    this.loadBolnice();
  }

  public loadBolnice(): void {
    this.bolnicaService.getAllBolnice().subscribe({
      next: (data) => {
        this.bolnice = data;
      },
      error: (error) => {
        console.error('Error fetching bolnice:', error);
      }
    });
  }

  public add(): void {
    this.odeljenjeService.addOdeljenje(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno dodato odeljenje: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error adding odeljenje:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom dodavanja novog odeljenja.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public update(): void {
    this.odeljenjeService.updateOdeljenje(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno izmenjeno odeljenje: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error updating odeljenje:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom izmene odeljenja.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public delete(): void {
    this.odeljenjeService.deleteOdeljenje(this.data.id).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno obrisano odeljenje: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error deleting odeljenje:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom brisanja odeljenja.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste.', 'Zatvori', { duration: 1000 });
  }
}

