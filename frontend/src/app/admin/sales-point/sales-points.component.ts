import {Component, OnInit} from '@angular/core';
import {GridBaseComponent} from '../grid-common/grid-base.component';
import {SalesPoint} from './sales-point';
import {GridProperties, NumberParser} from '../grid-common/grid_properties';
import {SalesPointsRepo} from './sales-point-repo';

@Component({
    selector: 'app-sales-points',
    templateUrl: '../grid-common/grid-common.component.html',
    styleUrls: ['../grid-common/grid_common.component.css']
})
export class SalesPointsComponent extends GridBaseComponent<SalesPoint> implements OnInit {

    constructor(repo: SalesPointsRepo) {
        const properties = new GridProperties();
        properties.title = 'Точки продаж';
        properties.columnDefs = [
            {
                headerName: 'Id',
                field: 'id',
                editable: false,
                filter: 'agNumberColumnFilter',
                floatingFilter: true,
                filterParams: {
                  resetButton: true,
                  closeOnApply: true,
                }
            },
            {
                headerName: 'Type',
                field: 'type',
                filter: 'agTextColumnFilter',
                floatingFilter: true,
                filterParams: {
                  resetButton: true,
                  closeOnApply: true,
                }
            },
            {
                headerName: 'AreaId',
                field: 'areaId',
                valueParser: NumberParser,
                sortable: false
            }
        ];
        super(repo, properties);
    }

    ngOnInit() {

    }
}