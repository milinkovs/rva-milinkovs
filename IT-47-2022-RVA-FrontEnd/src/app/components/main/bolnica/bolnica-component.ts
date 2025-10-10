import { Component, OnDestroy, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { BolnicaService } from '../../../services/bolnica-service';
import { Subscription } from 'rxjs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BolnicaDialog } from '../../dialogs/bolnica-dialog/bolnica-dialog';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-bolnica',
  imports: [
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSortModule,
    MatPaginatorModule
  ],
  templateUrl: './bolnica-component.html',
  styleUrl: './bolnica-component.css'
})
export class Bolnica implements OnInit, OnDestroy, AfterViewInit {
  displayedColumns: string[] = ['id', 'naziv', 'adresa', 'budzet', 'actions'];
  dataSource = new MatTableDataSource<Bolnica>;
  subscription!: Subscription;
  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;


  constructor(private bolnicaService: BolnicaService, private dialog: MatDialog) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  public loadData() {
    this.subscription = this.bolnicaService.getAllBolnice().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        console.error('Error fetching bolnice:', error.message + ' ' + error.name);
      }
    });
  }

  public openDialog(flag: number, id?: number, naziv?: string, adresa?: string, budzet?: number): void {
    const dialogRef = this.dialog.open(BolnicaDialog, { data: { id, naziv, adresa, budzet } });

    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.loadData();
      }
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
