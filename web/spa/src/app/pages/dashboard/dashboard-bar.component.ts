import {Component} from '@angular/core';

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
})
export class DashboardBarComponent {
  results = [
    {
      name: 'p2',
      series: [
        {name: 'won', value: 6},
        {name: 'lost', value: 3},
        {name: 'draw', value: 1},
      ],
    },
    {
      name: 'p1',
      series: [
        {name: 'won', value: 3},
        {name: 'lost', value: 6},
        {name: 'draw', value: 1},
      ],
    },
    {name: 'p3', series: []},
  ];

  showLegend = true;
  noBarWhenZero: false;

  showXAxis = true;
  xAxisLabel = 'Players';

  showYAxis = true;
  yAxisLabel = 'Win Rate';
  colorScheme = {
    domain: ['#00d68f', '#ff3d71', '#fa0'],
  };
}

