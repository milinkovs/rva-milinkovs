import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { Dijagnoza } from '../../../models/dijagnoza';
import { DijagnozaService } from '../../../services/dijagnoza-service';

@Component({
  selector: 'app-dijagnoza-dialog',
  imports: [
    MatDialogModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './dijagnoza-dialog.html',
  styleUrl: './dijagnoza-dialog.css'
})
export class DijagnozaDialog {

  public flag!: number;

  constructor(
    public snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<DijagnozaDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Dijagnoza,
    public dijagnozaService: DijagnozaService
  ) { }

  public add(): void {
    this.dijagnozaService.addDijagnoza(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno dodata dijagnoza: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error adding dijagnoza:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom dodavanja nove dijagnoze.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public update(): void {
    this.dijagnozaService.updateDijagnoza(this.data).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno izmenjena dijagnoza: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error updating dijagnoza:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom izmene dijagnoze.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public delete(): void {
    this.dijagnozaService.deleteDijagnoza(this.data.id).subscribe({
      next: (data) => {
        this.snackBar.open('Uspešno obrisana dijagnoza: ' + this.data.naziv, 'U redu', { duration: 2500 });
        this.dialogRef.close(1);
      },
      error: (error) => {
        console.error('Error deleting dijagnoza:', error.message + ' ' + error.name);
        this.snackBar.open('Došlo je do greške prilikom brisanja dijagnoze.', 'Zatvori', { duration: 2500 });
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
  }
}
