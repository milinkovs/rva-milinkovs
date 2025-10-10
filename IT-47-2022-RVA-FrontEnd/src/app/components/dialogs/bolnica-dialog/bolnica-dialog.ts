import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Bolnica } from '../../../models/bolnica';
import { BolnicaService } from '../../../services/bolnica-service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bolnica-dialog',
  imports: [
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './bolnica-dialog.html',
  styleUrl: './bolnica-dialog.css'
})


export class BolnicaDialog implements OnInit {

  public flag!: number;

  constructor(public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<BolnicaDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Bolnica,
    public bolnicaService: BolnicaService) { }

  ngOnInit(): void {

  }

  public add(): void {
    this.bolnicaService.addBolnica(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno dodata bolnica: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error adding bolnica:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom dodavanja nove bolnice.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public update(): void {
    this.bolnicaService.updateBolnica(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno izmenjena bolnica: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error updating bolnica:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom izmene bolnice.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public delete(): void {
    this.bolnicaService.deleteBolnica(this.data.id).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno obrisana bolnica: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error deleting bolnica:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom brisanja bolnice.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste.', 'Zatvori', { duration: 1000 });
  }
}
