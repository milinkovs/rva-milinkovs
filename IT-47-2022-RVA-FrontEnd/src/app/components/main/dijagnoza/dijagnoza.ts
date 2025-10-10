import { Component, OnDestroy, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { Dijagnoza as DijagnozaModel } from '../../../models/dijagnoza';
import { DijagnozaService } from '../../../services/dijagnoza-service';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { DijagnozaDialog } from '../../dialogs/dijagnoza-dialog/dijagnoza-dialog';

@Component({
  selector: 'app-dijagnoza',
  imports: [
    CommonModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatSortModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './dijagnoza.html',
  styleUrl: './dijagnoza.css'
})
export class Dijagnoza implements OnInit, OnDestroy, AfterViewInit {

  displayedColumns = ['id', 'naziv', 'opis', 'oznaka', 'actions'];
  dataSource = new MatTableDataSource<DijagnozaModel>();
  subscription!: Subscription;

  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  constructor(
    private dijagnozaService: DijagnozaService,
    private dialog: MatDialog
  ) { }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.loadData();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  loadData() {
    this.subscription = this.dijagnozaService.getAllDijagnoze().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        console.error('Error fetching dijagnoze:', error.message + ' ' + error.name);
      }
    });
  }

  public openDialog(flag: number, id?: number, naziv?: string, opis?: string, oznaka?: string): void {
    const dialogRef = this.dialog.open(DijagnozaDialog, {
      data: { id, naziv, opis, oznaka }
    });

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
