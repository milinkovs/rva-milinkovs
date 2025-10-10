import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { Odeljenje } from '../../../models/odeljenje';
import { Bolnica } from '../../../models/bolnica';
import { OdeljenjeDialog } from '../../dialogs/odeljenje-dialog/odeljenje-dialog';

@Component({
  selector: 'app-odeljenje',
  imports: [
    CommonModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './odeljenje-component.html',
  styleUrl: './odeljenje-component.css'
})
export class OdeljenjeComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', 'naziv', 'lokacija', 'bolnica', 'actions'];
  dataSource = new MatTableDataSource<Odeljenje>;
  subscription!: Subscription;

  constructor(private odeljenjeService: OdeljenjeService, private dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.subscription = this.odeljenjeService.getAllOdeljenja().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data);
      },
      error: (error) => {
        console.error('Error fetching bolnice:', error.message + ' ' + error.name);
      }
    });
  }

  public openDialog(flag: number, id?: number, naziv?: string, lokacija?: string, bolnica?: Bolnica): void {
    const dialogRef = this.dialog.open(OdeljenjeDialog, { data: { id, naziv, lokacija, bolnica } });

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.loadData();
      }
    });
  }
}
