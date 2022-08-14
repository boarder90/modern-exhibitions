import {
  Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild, ViewEncapsulation
} from '@angular/core';
import * as d3 from 'd3';
import {NetworkService} from "../../../services/HttpServices/network.service";
import {NetworkDto} from "../../../dtos/NetworkDto";
import {YearlyNetworkDto} from "../../../dtos/YearlyNetworkDto";
import {Router} from "@angular/router";
import {CountryPipe} from "../../../util/country-pipe";
import {MatrixService} from "../../../services/matrix.service";
import {AlgorithmsService} from "../../../services/HttpServices/algorithms.service";
import {CentralityDto} from "../../../dtos/CentralityDto";
import {CityPipe} from "../../../util/city-pipe";

@Component({
  selector: 'app-matrix',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './matrix.component.html',
  styleUrls: ['./matrix.component.scss']
})
export class MatrixComponent implements OnInit, OnDestroy {

  @ViewChild('matrixTotal', {read: ElementRef})
  total!: ElementRef<HTMLElement>;

  @ViewChild('matrixYearly', {read: ElementRef})
  yearly!: ElementRef<HTMLElement>;

  @Output() countries: EventEmitter<any> = new EventEmitter();
  @Output() cities: EventEmitter<any> = new EventEmitter();
  @Output() year: EventEmitter<any> = new EventEmitter();
  @Output() artistData: EventEmitter<any> = new EventEmitter();
  @Output() selection: EventEmitter<any> = new EventEmitter();

  countryPipe: CountryPipe | undefined;


  constructor(private networkService: NetworkService, private router: Router,
              private matrixService: MatrixService, private algorithmsService: AlgorithmsService) {}

  private network: NetworkDto = new NetworkDto([],[]);
  countriesArr : string[] = [];
  citiesArr: string[] = [];
  private granularity: boolean = true;
  private currentLower!: number;
  private currentUpper!: number;
  private color: string  = "default"
  private centralitiesSet: boolean = false;
  private yearlyMap: any;


  @Input('yearly') isYearly: boolean = false;

  private map: Map<number, YearlyNetworkDto> = new Map<number, YearlyNetworkDto>();
   show: boolean = true;

  ngOnInit(): void {
  }

  ngOnDestroy() {
    if(this.yearlyMap){
      this.yearlyMap.unsubscribe();
    }
  }

  getTotal(networkIds: number[], color:string){
     d3.select("svg").selectAll("*").remove();
     this.networkService.getNetwork(networkIds).subscribe(
      data => {
        this.network= data;
        this.drawMatrixTotal(this.network,  this.matrixService.calculateSizeOfRectTotal(this.network.nodes.length),color);
        }
     )
    }

  getTotalFilteredByCountry(networkIds: number[], country: string, color:string){
    d3.select("svg#matrixTotal").selectAll("*").remove();
    this.networkService.getNetworkFilteredByCountry(networkIds, country).subscribe(
      data => {
        this.network= data;
        //console.log(this.network)
        this.drawMatrixTotal(this.network,  this.matrixService.calculateSizeOfRectTotal(this.network.nodes.length),color);
      }
    )
  }

  toggleGranularity(){
    this.granularity = !this.granularity;
  }

  getYearlyFilteredByCountry(networkIds: number[], country: string, color: string, year: number, lower: boolean){
    d3.select("svg#matrixYearly").selectAll("*").remove();
    this.networkService.getNetworkYearlyFilteredByCountry(networkIds, country);
    this.drawHalfMatrix(year, lower, color);
  }

  getYearlyFilteredByCity(networkIds: number[], city: string, color:string, year: number, lower: boolean){
    d3.select("svg#matrixYearly").selectAll("*").remove();
    this.networkService.getNetworkYearlyFilteredByCity(networkIds, city);
    this.drawHalfMatrix(year, lower, color);
  }

  getTotalFilteredByCity(networkIds: number[], city: string, color: string){
    d3.select("svg#matrixTotal").selectAll("*").remove();
    this.networkService.getNetworkFilteredByCity(networkIds, city).subscribe(
      data => {
        this.network= data;
        this.drawMatrixTotal(this.network, this.matrixService.calculateSizeOfRectTotal(this.network.nodes.length), color);
      }
    )
  }

  getYearly(networkIds: number[], color: string){
    d3.select("svg").selectAll("*").remove();
    this.networkService.getNetworkYears(networkIds);
    this.initNetworksMap(color);
  }

  resetLocationFilter(networkIds: number[], year: number, lower: boolean, color: string){
    this.networkService.getNetworkYears(networkIds);
    this.drawHalfMatrix(year, lower, color);
  }

   drawHalfMatrix(e: number, lower: boolean, color: string){
     this.yearlyMap = this.networkService.getMap().subscribe(
      data => {
        this.map = data;
        let matrix;
        if(e==1905 /*initial value*/){
          matrix = this.matrixService.getLowerMatrix(this.map.get(1904)!,1904);
          this.drawMatrixYearly(this.map.get(1904)!, matrix, this.map.get(1904)!.nodes.length, "lower", 1904, color);
          matrix = this.matrixService.getUpperMatrix(this.map.get(1905)!,1905);
          this.drawMatrixYearly(this.map.get(1905)!, matrix, this.map.get(1905)!.nodes.length, "upper", 1905, color);
        } else {
          if (lower) {
            matrix = this.matrixService.getUpperMatrix(this.map.get(e-1)!,e-1);
            this.drawMatrixYearly(this.map.get(e-1)!, matrix, this.map.get(e-1)!.nodes.length, "upper", e-1, color);
            matrix = this.matrixService.getLowerMatrix(this.map.get(e)!,e);
            this.drawMatrixYearly(this.map.get(e)!, matrix, this.map.get(e)!.nodes.length, "lower", e, color);
          } else {
            matrix = this.matrixService.getLowerMatrix(this.map.get(e-1)!,e-1);
            this.drawMatrixYearly(this.map.get(e-1)!, matrix, this.map.get(e-1)!.nodes.length, "lower", e-1, color);
            matrix = this.matrixService.getUpperMatrix(this.map.get(e)!,e);
            this.drawMatrixYearly(this.map.get(e)!, matrix, this.map.get(e)!.nodes.length, "upper", e, color);
          }
        }
      }
    );
  }

  colorBySex(){
    d3.selectAll("text[sex]").style("fill", (d: any) => d.sex === "M" ? "blue" : (d.sex==="F" ? "red" : "black"));
    this.color = "sex";
  }

  colorDefault(){
    d3.selectAll("text[sex]").style("fill", "black");
    this.color = "default";
  }

  colorByNationality(){
    d3.selectAll("text[sex]").style("fill", (d:any) => 'var(--' +d.nationality + ')');
    this.color = "nationality";
  }

  reColor(){
    if(this.color==="default"){
      this.colorDefault()
    } else if (this.color === "sex"){
      this.colorBySex();
    } else {
      this.colorByNationality();
    }
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  private drawMatrixTotal(network: NetworkDto, size: number, color: string) {
    const matrix = this.matrixService.getTotalMatrix(network);
    const a = size;

    const pipe = new CityPipe();

    let b = d3.select("svg#matrixTotal").classed("svg-content-responsive", true)
      .append("g").attr("id","zoomable").attr("transform", "translate("+0+ "," + 0 + ")")
      .append("g");

    b.selectAll("rect")
      .data(matrix)
      .enter()
      .append("rect")
      .attr("transform", "translate("+ 250 + "," +110 + ")")
       .attr("class", "grid").attr("view", "total")
      .attr("width", a)
      .attr("height", a)
      .attr("x", d => d.x * a)
      .attr("y", d => d.y * a)
      .style("fill-opacity", d => d.weight * .2)
      .style("fill", "var(--" + pipe.transform(color) + ")")
      .style('cursor', 'pointer');

    d3.select("#zoomable")
      .append("g")
      .attr("transform", "translate("+(a/2+ 250) + "," + -5 + ")")
      .selectAll("text")
      .append("g")
      .data(network.nodes)
      .enter()
      .append("text").attr("nationality",d => d.nationality)
      .attr("sex", d => d.sex)
      .attr("artistX",  d => d.id)
      .attr("transform",(d,i)=>"translate("+(i * a)+",110) rotate(300)")
      .attr("name", d => d.name)
      .attr("id", d=> d.id)
      .text(d => d.name)
      .style("text-anchor", "start")
      .style("font-size", "20px")

    d3.select("#zoomable")
      .append("g").attr("transform", "translate(" + 240  + ","+(115 + (a/2))+")")
      .selectAll("text")
      .data(network.nodes)
      .enter()
      .append("text").attr("artistY",  d => d.id)
      .attr("y", (d, i) => i * a + a/10)
      .attr("nationality",d => d.nationality)
      .attr("sex", d => d.sex)
      .attr("name", d => d.name)
      .attr("id", d=> d.id)
      .text(d => d.name)
      .style("text-anchor", "end")
      .style("font-size", "20px")

    function zoomed(e: any): void {
      d3.select("#zoomable").attr('transform', e.transform);
    }

    const zoomHandler = d3.zoom<SVGSVGElement,unknown>()
      .on('zoom', zoomed)
      .scaleExtent([1, 5])
    ;
    d3.select<SVGSVGElement,unknown>("svg#matrixTotal").call(zoomHandler);

    d3.selectAll("rect[view='total']").on("mouseover", (d,i)=> this.mouseOverTotalRect(d,i)).on("mouseout", (d, i) => this.mouseOutRect(d,i, false))
     .on("click", (d,i) => this.routeToMap(i));

    d3.selectAll("text").on("mouseover", (d,i) => this.mouseOverText(d,i)).on("mouseout", (d, i)=>this.mouseOutText(d,i));
}

routeToMap(i: any){
  this.selection.emit(i.id);
}

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
mouseOverTotalRect(d: any, i:any) {
    this.countriesArr = i.country;
    this.citiesArr = i.city;
    if(this.granularity){
      this.countries.emit(this.countriesArr);
    } else {
      this.cities.emit(this.citiesArr);
    }
  d3.selectAll("rect[view='total']").filter(function(p:any){return p.x==i.x && p.y==i.y}).raise().style("stroke", "orange").style("stroke-width", 5);
  if(!this.centralitiesSet){
    d3.selectAll("text")
      .filter(function() {
        return d3.select(this).attr("artistX") === (i.id.split(",")[1]) || d3.select(this).attr("artistY") === (i.id.split(",")[0])
      }).style("font-weight","bolder");
    d3.selectAll("text")
      .filter(function() {
        return d3.select(this).attr("artistX") !== (i.id.split(",")[1]) && d3.select(this).attr("artistY") !== (i.id.split(",")[0])
      }).style("opacity","0.4");
  } else {
    d3.selectAll("text")
      .filter(function() {
        return d3.select(this).attr("artistX") === (i.id.split(",")[1]) || d3.select(this).attr("artistY") === (i.id.split(",")[0])
      }).style("text-shadow", "1px 0 yellow, -1px 0 yellow, 0 1px yellow, 0 -1px yellow");
  }
}

  mouseOverText(d: any, i:any) {

    this.artistData.emit(i);
    if(!this.centralitiesSet){
        d3.selectAll("text")
        .filter(function() {
          return d3.select(this).attr("name") === (i.name)
        }).style("font-weight","bolder");
    } else {
      d3.selectAll("text")
        .filter(function() {
          return d3.select(this).attr("name") === (i.name)
        }).style("text-shadow", "1px 0 yellow, -1px 0 yellow, 0 1px yellow, 0 -1px yellow");
    }
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  mouseOverYearlyRect(d: any, i:any, part: string) {
    this.countriesArr = i.country;
    this.citiesArr = i.city;
    if(this.granularity){
      this.countries.emit(this.countriesArr);
    } else {
      this.cities.emit(this.citiesArr);
    }
    this.year.emit(i.year);
    d3.selectAll("rect[view='yearly']").filter(function(p:any){return p.x==i.x && p.y==i.y  || p.x==i.y && p.y==i.x}).raise().style("stroke", "orange").style("stroke-width", 5);

    if(!this.centralitiesSet) {
      if(part==="upper"){
        d3.selectAll("text")
          .filter(function () {
            return d3.select(this).attr("UpperX") === (i.id.split(",")[1]) || d3.select(this).attr("LowerX") === (i.id.split(",")[0]) || d3.select(this).attr("LowerY") === (i.id.split(",")[1]) || d3.select(this).attr("UpperY") === (i.id.split(",")[0])
          }).style("font-weight", "bolder").style("font-size", "20px");
      } else {
        d3.selectAll("text")
          .filter(function () {
            return d3.select(this).attr("UpperX") === (i.id.split(",")[0]) || d3.select(this).attr("LowerX") === (i.id.split(",")[1]) || d3.select(this).attr("LowerY") === (i.id.split(",")[0]) || d3.select(this).attr("UpperY") === (i.id.split(",")[1])
          }).style("font-weight", "bolder").style("font-size", "20px");
      }
      if(part==="upper"){
        d3.selectAll("text")
          .filter(function() {
            return d3.select(this).attr("currentYear")=== null && d3.select(this).attr("UpperX") !== (i.id.split(",")[1]) && d3.select(this).attr("LowerX") !== (i.id.split(",")[0]) &&  d3.select(this).attr("LowerY") !== (i.id.split(",")[1]) && d3.select(this).attr("UpperY") !== (i.id.split(",")[0])
          }).style("opacity","0.2");
      } else {
        d3.selectAll("text")
          .filter(function() {
            return d3.select(this).attr("currentYear")=== null && d3.select(this).attr("UpperX") !== (i.id.split(",")[0]) && d3.select(this).attr("LowerX") !== (i.id.split(",")[1]) &&  d3.select(this).attr("LowerY") !== (i.id.split(",")[0]) && d3.select(this).attr("UpperY") !== (i.id.split(",")[1])
          }).style("opacity","0.2");
      }
    } else {
      if(part==="lower") {
        d3.selectAll("text")
          .filter(function () {
            return d3.select(this).attr("UpperX") === (i.id.split(",")[0]) || d3.select(this).attr("LowerX") === (i.id.split(",")[1]) || d3.select(this).attr("LowerY") === (i.id.split(",")[0])
              || d3.select(this).attr("UpperY") === (i.id.split(",")[1])
          }).style("text-shadow", "1px 0 yellow, -1px 0 yellow, 0 1px yellow, 0 -1px yellow");
      } else {
        d3.selectAll("text")
          .filter(function () {
            return d3.select(this).attr("UpperX") === (i.id.split(",")[1]) || d3.select(this).attr("LowerX") === (i.id.split(",")[0]) || d3.select(this).attr("LowerY") === (i.id.split(",")[1])
              || d3.select(this).attr("UpperY") === (i.id.split(",")[0])
          }).style("text-shadow", "1px 0 yellow, -1px 0 yellow, 0 1px yellow, 0 -1px yellow");
      }
    }
  }


  mouseOutText(d: any, i:any) {
    this.artistData.emit(null);
    if(!this.centralitiesSet){
      d3.selectAll("text")
        .filter(function() {
          return d3.select(this).attr("name") === (i.name)
        }).style("font-weight",20);
    } else {
      d3.selectAll("text")
        .filter(function() {
          return d3.select(this).attr("name") === (i.name)
        }).style("text-shadow","none");
    }
  }

    resizeDefault(){
    this.centralitiesSet = false;
      d3.selectAll("text").style("font-weight", "normal").style("opacity", 1);
    }

    resizeWeighted(ids: number[], yearly: boolean){
    this.centralitiesSet = true;
    if(!yearly){
      this.algorithmsService.getWeightedDegreeCentrality(ids, null).subscribe(
        data => {
          this.resizeByCentralities("", data);
        }
      )
    } else {
      this.algorithmsService.getWeightedDegreeCentrality(ids, this.currentUpper).subscribe(
        data => {
          this.resizeByCentralities("upper", data);
        }
      )
      this.algorithmsService.getWeightedDegreeCentrality(ids, this.currentLower).subscribe(
        data => {
          this.resizeByCentralities("lower", data);
        }
      )
    }
    }

    resizeNonWeighted(ids: number[], yearly: boolean){
    this.centralitiesSet = true;
      if(!yearly){
        this.algorithmsService.getUnweightedDegreeCentrality(ids, null).subscribe(
          data => {
            this.resizeByCentralities("", data);
          }
        )
      } else {
        this.algorithmsService.getUnweightedDegreeCentrality(ids, this.currentUpper).subscribe(
          data => {
            this.resizeByCentralities("upper", data);
          }
        )
        this.algorithmsService.getUnweightedDegreeCentrality(ids, this.currentLower).subscribe(
          data => {
            this.resizeByCentralities("lower", data);
          }
        )
      }
    }

    resizeByCentralities(part: string, centralities: CentralityDto[]){
      const max = centralities[0].centrality;
      centralities.forEach(
        elem => {
          if(elem.centrality/max > 0.66){
            d3.selectAll("text")
              .filter(function() {
                return d3.select(this).attr("id") === (part + String(elem.id))
              }).style("font-weight","900");
          } else if(elem.centrality/max >= 0.33 && elem.centrality/max<=0.66){
            d3.selectAll("text")
              .filter(function() {
                return d3.select(this).attr("id") === (part + String(elem.id))
              }).style("font-weight","500").style("opacity", 0.8);
          } else if(elem.centrality/max>=0.20 && elem.centrality/max <=0.33){
            d3.selectAll("text")
              .filter(function() {
                return d3.select(this).attr("id") === (part + String(elem.id))
              }).style("font-weight","100").style("opacity", 0.8); }
          else {
            d3.selectAll("text")
              .filter(function() {
                return d3.select(this).attr("id") === (part + String(elem.id))
              }).style("font-weight","100").style("opacity",0.3);
          }
        }
      )
    }


  mouseOutRect(d: any, i:any, yearly: boolean) {
    this.countriesArr = [];
    this.countries.emit(this.countriesArr);
    d3.selectAll("rect").style("stroke-width", "1").style("stroke","gray");
    d3.select("#tooltip")
      .style("opacity","0")
    if(!this.centralitiesSet){
      d3.selectAll("text").style("font-weight", "normal");
      d3.selectAll("text").style("opacity",1);
    } else {
      d3.selectAll("text").style("text-shadow","none");
    }
    if(yearly){
      d3.selectAll("text").style("font-size", "18");
    }
  }

  initNetworksMap(color: string){
    this.yearlyMap = this.networkService.getMap().subscribe(
      data => {
        this.map = data;
        //console.log(this.map)
        d3.select("svg#matrixYearly").selectAll("*").remove();
        d3.select("svg#matrixYearly").append("g").attr("id","zoomableYearlyTest").append("g").attr("id","zoomableYearly").attr("transform", "translate("+120 + "," + 90 + ")");
        const matrix = this.matrixService.getLowerMatrix(this.map.get(1904)!,1904);
        this.drawMatrixYearly(this.map.get(1904)!, matrix, this.map.get(1904)!.nodes.length, "lower", 1904, color);
        const matrix2 = this.matrixService.getUpperMatrix(this.map.get(1905)!,1905);
        this.drawMatrixYearly(this.map.get(1905)!, matrix2, this.map.get(1905)!.nodes.length, "upper", 1905, color);
      }
    );
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  private drawMatrixYearly(network: YearlyNetworkDto, matrix: any[], numNodes: number, part: string, year: number, color: string){

    console.log(color)

   d3.select('[id="'+ part + 'X"]').remove();
   d3.select('[id="'+ part + 'Y"]').remove();
   d3.select('[id="'+ part + '"]').remove();
   d3.select('[id="'+ part + '"]').remove();
   d3.select('[id="year'+ part + '"]').remove();
   d3.selectAll("text").filter(function(){
      return d3.select(this).attr("class")==part
    }).remove();

    const scale = this.matrixService.calculateSizeOfRectYearly(numNodes);
    const pipe = new CityPipe();

    if(part=="lower"){
      this.currentLower = year;
      d3.select("#zoomableYearly").append("g").attr("id","year" + part).append("text").attr("currentYear",year).
      attr("x", () =>  -3*scale).
      attr("y", (110 + scale*2+(scale*(numNodes)))).
      text(year).style("font-size","20")
    }
    if(part=="upper") {
      this.currentUpper = year;
      d3.select("#zoomableYearly").append("g").attr("id","year" + part).append("text").
      attr("currentYear", year).attr("x", () => scale * numNodes + scale * 5 + 10).
      attr("y", () => scale).
      text(year).style("font-size","20")
    }

    if(part=="lower"){
      this.drawYearlyRectangles(scale,pipe,part,matrix,color,2);
    } else {
      this.drawYearlyRectangles(scale,pipe,part,matrix,color,1);
    }

    if(part=="upper"){
      d3.select("#zoomableYearly")
        .append("g").attr("id", "upperX")
        .attr("transform","translate("+(scale/2 + scale + 10) + "," + (-scale) + ")")
        .selectAll("text")
        .append("g")
        .data(network.nodes)
        .enter()
        .append("text").attr("class","upper").attr("UpperX",  d => d.id)
        .attr("transform",(d,i)=>"translate("+(i * scale)+",100) rotate(300)")
        .attr("sex", d => d.sex)
        .attr("name", d => d.name)
        .attr("id", d=> "upper" + d.id)
        .text(d => d.name)
        .style("text-anchor","start")
        .style("font-size","18px")

    d3.select("#zoomableYearly")
      .append("g").attr("id", "upperY").
       attr("transform", "translate(0,"+( + (-scale/3 + 115) + ")"))
      .selectAll("text")
      .data(network.nodes)
      .enter()
      .append("text").attr("class","upper").attr("UpperY",  d => d.id)
      .attr("y", (d, i) =>  (i * scale))
      .attr("x", scale * numNodes + scale*1.5 + 10)
      .attr("sex", d => d.sex)
      .attr("name", d => d.name)
      .attr("id", d=> "upper" + d.id)
      .text(d => d.name)
      .style("text-anchor", "start")
      .style("font-size", "18px")
    }

    if(part=="lower") {
      d3.select("#zoomableYearly")
        .append("g").attr("id", "lowerY")
        .attr("transform", "translate(0," + (scale + 115) + ")")
        .selectAll("text")
        .data(network.nodes)
        .enter()
        .append("text").attr("class","lower").attr("LowerY",  d => d.id)
        .attr("y", (d, i) => (i * scale))
        .attr("x", (-scale/2))
        .attr("sex", d => d.sex)
        .attr("name", d => d.name)
        .attr("id", d=> "lower" + d.id)
        .text(d => d.name)
        .style("text-anchor", "end")
        .style("font-size", "18px")

      d3.select("#zoomableYearly")
        .append("g").attr("id", "lowerX")
        .attr("transform", "translate(" + (-(scale/4) + 10) + "," + (scale * numNodes + scale/2 + 125) + ")")
        .selectAll("text")
        .append("g")
        .data(network.nodes)
        .enter()
        .append("text").attr("class","lower").attr("LowerX",  d => d.id)
        .attr("transform", (d, i) => "translate(" + (i * scale) + ",0) rotate(40)")
        .attr("sex", d => d.sex)
        .attr("name", d => d.name)
        .attr("id", d=> "lower" + d.id)
        .text(d => d.name)
        .style("text-anchor", "start")
        .style("font-size", "18px")
    }

    function zoomed(e: any): void {
      d3.select("#zoomableYearlyTest").attr('transform', e.transform);
    }

    const zoomHandler = d3.zoom<SVGSVGElement,unknown>()
      .on('zoom', zoomed)
      .scaleExtent([1, 5])
    ;

    d3.select<SVGSVGElement,unknown>("svg#matrixYearly").call(zoomHandler);

    this.reColor();

    d3.selectAll("rect[view='yearly']").filter(function () {
      return d3.select(this).attr("part")==="upper"
    }).on("mouseover", (d,i)=> this.mouseOverYearlyRect(d,i,"upper")).on("mouseout", (d, i) => this.mouseOutRect(d,i, true)).on("click", (d, i) => this.routeToMap(i));

    d3.selectAll("rect[view='yearly']").filter(function () {
      return d3.select(this).attr("part")==="lower"
    }).on("mouseover", (d,i)=> this.mouseOverYearlyRect(d,i,"lower")).on("mouseout", (d, i) => this.mouseOutRect(d,i, true)).on("click", (d, i) => this.routeToMap(i));
    d3.selectAll("text").on("mouseover", (d,i) => this.mouseOverText(d,i)).on("mouseout", (d, i)=>this.mouseOutText(d,i));
  }

   switchSVG() {
     let svg1 = document.getElementById("matrixTotal")!.style.display;
     if (svg1 === "none") {
       this.total.nativeElement.style.display = "block";
       this.yearly.nativeElement.style.display = "none";
       this.yearly.nativeElement.style.height = "0%";
       this.yearly.nativeElement.style.width = "0%";
       this.total.nativeElement.style.height = "100%";
      this.total.nativeElement.style.width = "100%";
     } else {
       this.total.nativeElement.style.display = "none";
       this.total.nativeElement.style.height  = "0%";
       this.total.nativeElement.style.width = "0%";
       this.yearly.nativeElement.style.display = "block";
      this.yearly.nativeElement.style.height = "100%";
       this.yearly.nativeElement.style.width = "100%";
     }
   }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  drawYearlyRectangles(scale: number, pipe:CityPipe, part: string, matrix: any[], color:string, move: number){
     const half = d3.select("#zoomableYearly").append("g")
       .attr("transform","translate(10,110)")
       .attr("id",part)
       .selectAll("rect")
       .data(matrix)
       .enter()
       .append("rect").attr("view","yearly")
       .attr("class","grid").attr("part", part)
       .attr("width",scale)
       .attr("height",scale)
       .style("fill-opacity", (d:any)=> d.weight * .2)
       .style("fill", "var(--" + pipe.transform(color) + ")")
       .style('cursor', 'pointer');

     if(part==="lower"){
        half.attr("x", (d:any)=> d.x<=d.y? d.x*scale-scale/move : d.x*scale+scale/move)
          .attr("y", (d:any)=> d.x<=d.y? d.y*scale+scale/move : d.y*scale-scale/move)
     } else {
       half.attr("x", (d:any)=> d.x<d.y? d.x*scale-scale/move : d.x*scale+scale/move)
         .attr("y", (d:any)=> d.x<d.y? d.y*scale+scale/move : d.y*scale-scale/move)
     }
   }
}
