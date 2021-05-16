import {Component} from '@angular/core';
import {DashboardService} from './dashboard.service';

@Component({
  selector: 'ngx-dashboard-bar',
  template: `
    <ngx-charts-bar-vertical-stacked
      [results]="results"
      [scheme]="colorScheme"
      [xAxis]="showXAxis"
      [yAxis]="showYAxis"
      [legend]="showLegend"
      [xAxisLabel]="xAxisLabel"
      [showXAxisLabel]="showXAxis"
      [yAxisLabel]="yAxisLabel"
      [noBarWhenZero]="noBarWhenZero"
    >
    </ngx-charts-bar-vertical-stacked>
  `,
  providers: [DashboardService],
})
export class DashboardBarComponent {
  results;

  showLegend = true;
  noBarWhenZero: false;

  showXAxis = true;
  xAxisLabel = 'Players';

  showYAxis = true;
  yAxisLabel = 'Win Rate';
  colorScheme = {
    domain: ['#00d68f', '#ff3d71', '#fa0'],
  };


  constructor(private service: DashboardService) {
    this.service
      .getStatistics()
      .subscribe(data => this.results = data);
  }
}

