import { Component, OnDestroy, OnInit, Input, OnChanges, SimpleChanges, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource, MatTableModule, MatTable } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { PacijentService } from '../../../services/pacijent-service';
import { Pacijent } from '../../../models/pacijent';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { PacijentDialog } from '../../dialogs/pacijent-dialog/pacijent-dialog';
import { Odeljenje } from '../../../models/odeljenje';

@Component({
  selector: 'app-pacijent',
  imports: [
    CommonModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './pacijent-component.html',
  styleUrl: './pacijent-component.css'
})
export class PacijentComponent implements OnInit, OnDestroy, OnChanges, AfterViewInit {

  displayedColumns = ['id', 'ime', 'osiguranje', 'datumRodjenja', 'dijagnoza', 'odeljenje', 'actions'];
  dataSource: MatTableDataSource<Pacijent> = new MatTableDataSource<Pacijent>();
  subscription!: Subscription;

  @ViewChild(MatTable) table!: MatTable<Pacijent>;
  @ViewChild(MatSort) sort!: MatSort;
  @Input() selektovanoOdeljenje!: Odeljenje;

  constructor(private pacijentService: PacijentService,
    private dialog: MatDialog
  ) {
  }


  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  ngOnInit() {
    // this.loadData();
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.filterPredicate = (data: Pacijent, filter: string) => {
      const dataStr = (
        data.id +
        data.ime +
        (data.zdrOsiguranje ? 'da' : 'ne') +
        (data.dijagnoza?.naziv || '') +
        (data.odeljenje?.id || '')
      ).toLowerCase();
      return dataStr.includes(filter);
    };
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selektovanoOdeljenje'] && this.selektovanoOdeljenje) {
      this.loadData();
    }
  }

  loadData() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.dataSource.data = [];

    this.subscription = this.pacijentService.getPacijentiByOdeljenje(this.selektovanoOdeljenje.id).subscribe({
      next: (data) => {
        this.dataSource.data = data || [];
        this.dataSource.sort = this.sort;
        if (this.table) {
          this.table.renderRows();
        }
      },
      error: (error) => {
        console.error('Error fetching pacijenti:', error);
        this.dataSource.data = [];
        if (this.table) {
          this.table.renderRows();
        }
      }
    });
  }

  public openDialog(flag: number, id?: number, ime?: string, zdrOsiguranje?: boolean, datumRodjenja?: Date, odeljenje?: any, dijagnoza?: any): void {
    const dialogRef = this.dialog.open(PacijentDialog,
      {
        data: {
          id, ime, zdrOsiguranje, datumRodjenja,
          odeljenje: odeljenje || this.selektovanoOdeljenje,
          dijagnoza

        }
      });
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(result => {
      setTimeout(() => {
        this.loadData();
      }, 300);
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
