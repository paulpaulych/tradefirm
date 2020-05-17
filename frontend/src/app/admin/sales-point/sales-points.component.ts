import {Component, OnInit} from '@angular/core';
import {GridCommonComponent} from '../grid-common/grid-common.component';
import {SalesPoint} from './sales-point';
import {GridProperties} from '../grid-common/grid_properties';
import {SalesPointsRepo} from './sales-point-repo';

@Component({
    selector: 'app-sales-points',
    templateUrl: '../grid-common/grid-common.component.html',
    styleUrls: ['../grid-common/grid_common.component.css']
})
export class SalesPointsComponent extends GridCommonComponent<SalesPoint> implements OnInit {

    constructor(repo: SalesPointsRepo) {
        const properties = new GridProperties();
        properties.title = 'Точки продаж';
        properties.columnDefs = [
            {
                headerName: 'Id',
                field: 'id',
                editable: false
            },
            {
                headerName: 'Type',
                field: 'type'
            },
            {
                headerName: 'AreaId',
                field: 'areaId'
            }
        ];
        super(repo, properties);
    }

    ngOnInit() {

    }

}
