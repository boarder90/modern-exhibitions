import {Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {YearlyNetworkDto} from "../../../dtos/YearlyNetworkDto";
import * as d3 from "d3";
import {NetworkService} from "../../../services/HttpServices/network.service";
import {NetworkDto} from "../../../dtos/NetworkDto";
import {AlgorithmsService} from "../../../services/HttpServices/algorithms.service";
import {CityPipe} from "../../../util/city-pipe";

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.scss']
})
export class NetworkComponent implements OnInit, OnDestroy {

  constructor(private networkService: NetworkService, private algorithmsService: AlgorithmsService) { }

  ngOnDestroy(): void {
    d3.selectAll("g").remove();
    }

  @Input('yearly') isYearly: boolean = false;

  @Output() countries: EventEmitter<any> = new EventEmitter();
  @Output() cities: EventEmitter<any> = new EventEmitter();
  @Output() year: EventEmitter<any> = new EventEmitter();
  @Output() artistData: EventEmitter<any> = new EventEmitter();
  @Output() selection: EventEmitter<any> = new EventEmitter();

  @ViewChild('total', {read: ElementRef})
  total!: ElementRef<HTMLElement>;

  @ViewChild('yearly', {read: ElementRef})
  yearly!: ElementRef<HTMLElement>;

  private map: Map<number, YearlyNetworkDto> = new Map<number, YearlyNetworkDto>();
  show: boolean = true;
  networkDto: NetworkDto = new NetworkDto([],[]);
  selected = undefined;
  svgYearly: any;
  svgTotal: any;
  simulation: any;
  nodes: any;
  links: any;
  currentYear!: number;
  color: string = "default";
  countriesArr : string[] = [];
  citiesArr: string[] = [];
  private granularity: boolean = true;

  ngOnInit(): void {
  }

  colorBySex(){
    d3.selectAll("circle").style("fill", (d: any) => d.sex === "M" ? "blue" : (d.sex==="F" ? "red" : "black"));
    this.color="sex";
  }

  colorDefault(){
    d3.selectAll("circle").style("fill", "black");
    this.color="default";
  }

  colorByNationality(){
    d3.selectAll("circle").style("fill", (d:any) => 'var(--' +d.nationality + ')');
    this.color="nationality";
  }

  resizeDefault(){
    d3.selectAll("circle").attr("r", "25");
  }

  getTotalFilteredByCountry(networkIds: number[], country: string, color:string){
    d3.select("svg#total").selectAll("*").remove();
    this.networkService.getNetworkFilteredByCountry(networkIds, country).subscribe(
      data => {
        this.drawNodeLink(data.nodes, data.links, false,color);
      }
    )
  }

  getTotalFilteredByCity(networkIds: number[], country: string, color:string){
    d3.select("svg#total").selectAll("*").remove();
    this.networkService.getNetworkFilteredByCity(networkIds, country).subscribe(
      data => {
        this.drawNodeLink(data.nodes, data.links, false,color);
      }
    )
  }

  getYearlyFilteredByCountry(networkIds: number[], country: string, color: string, year: number, lower: boolean){
    console.log(this.currentYear + " " + color)
    d3.select("svg#networkYearly").selectAll("*").remove();
    this.networkService.getNetworkYearsFilteredByCountry(networkIds, country);
    this.networkService.getMap().subscribe(
      data => {
        this.map = data;
        console.log(this.map)
        this.drawYearly(year, color)
      }
    );
  }

  getYearlyFilteredByCity(networkIds: number[], city: string, color:string, year: number, lower: boolean){
    d3.select("svg#networkYearly").selectAll("*").remove();
    this.networkService.getNetworkYearsFilteredByCity(networkIds, city);
    this.networkService.getMap().subscribe(
      data => {
        this.map = data;
        console.log(this.map)
        this.drawYearly(year, color)
      }
    );
  }


  resizeWeighted(ids: number[], yearly: boolean){
    this.algorithmsService.getWeightedDegreeCentrality(ids,null).subscribe(
      data => {
        const max  = data[0].centrality
        data.forEach(
          elem => {
            d3.select("#total").selectAll("circle").filter(function () {
              return d3.select(this).attr("id") == String(elem.id)
            }).attr("r", (20 + (elem.centrality / max)*25))

          })
      }
    )
    this.algorithmsService.getWeightedDegreeCentrality(ids,this.currentYear).subscribe(
      data => {
        const max  = data[0].centrality
        if(max!==0){
          data.forEach(
            elem => {
              d3.select("#networkYearly").selectAll("circle").filter(function () {
                return d3.select(this).attr("id") == String(elem.id)
              }).attr("r", (20 + (elem.centrality / max)*25))
            })
        }
      })
  }

  resizeNonWeighted(ids: number[], yearly: boolean){
    this.algorithmsService.getUnweightedDegreeCentrality(ids,null).subscribe(
      data => {
        const max  = data[0].centrality
        data.forEach(
          elem => {
            d3.select("#total").selectAll("circle").filter(function () {
              return d3.select(this).attr("id") == String(elem.id)
            }).attr("r", (20 + (elem.centrality / max)*25))

          })
      })
    this.algorithmsService.getUnweightedDegreeCentrality(ids,this.currentYear).subscribe(
      data => {
        const max  = data[0].centrality
        if(max!==0){
          data.forEach(
            elem => {
              d3.select("#networkYearly").selectAll("circle").filter(function () {
                return d3.select(this).attr("id") == String(elem.id)
              }).attr("r", (20 + (elem.centrality / max)*25))
            })
        }
      })
  }


  showArtist(d: any, i:any, yearly: boolean) {
    this.artistData.emit(i);
    this.selected = i.id;
    d3.selectAll("circle").filter(function () {
      return d3.select(this).attr("id") == i.id
    })
      .attr('stroke', 'yellow').attr('stroke-width', 7);

    if(yearly){
      this.getConnectionsOfNode(i.id);
    } else {
      this.getConnectionsOfNodeTotal(i.id);
    }

    d3.selectAll("path").filter(function () { // @ts-ignore
      return !(d3.select(this).attr("source") == i.id || d3.select(this).attr("target") == i.id)
    })
      .style("opacity", 0.1);
  }

  showLink(d: any, i:any, yearly: boolean) {
    if(yearly){
      this.year.emit(this.currentYear);
    }

    this.countriesArr = i.countries;
    this.citiesArr = i.cities;
    if(this.granularity){
      this.countries.emit(this.countriesArr);
    } else {
      this.cities.emit(this.citiesArr);
    }

    d3.selectAll("circle").filter(function () { // @ts-ignore
      return !(d3.select(this).attr("id") == i.source.id || d3.select(this).attr("id") == i.target.id)
    }).style("opacity", 0.1);

    d3.selectAll("text").filter(function () { // @ts-ignore
      return !(d3.select(this).attr("id") == i.source.id || d3.select(this).attr("id") == i.target.id)
    }).style("opacity", 0.1);

    d3.selectAll("path").filter(function () { // @ts-ignore
      return !(d3.select(this).attr("source") == i.source.id && d3.select(this).attr("target") == i.target.id)
    })
      .style("opacity", 0.1);
  }


  getConnectionsOfNode(id: number) {
    this.networkService.getHasLinksYearly().subscribe(
      map => {
        if(!map.has(this.currentYear)){
          d3.selectAll("circle").style("opacity", (d:any) => (d.id === id) ? 1 : 0.1);
          d3.selectAll("text").style("opacity", (d:any) => (d.id === id) ? 1 : 0.1);
        } else {
          d3.selectAll("circle").style("opacity", (d:any) => (map.get(this.currentYear)!.has(id+","+d.id)||d.id === id) ? 1 : 0.1);
          d3.selectAll("text").style("opacity", (d:any) => (map.get(this.currentYear)!.has(id+","+d.id)||d.id === id) ? 1 : 0.1);
        }
      }
    )
  }

  getConnectionsOfNodeTotal(id: number) {
    this.networkService.getHasLinks().subscribe(
      map => {
          d3.selectAll("circle").style("opacity", (d:any) => (map.has(id+","+d.id)||d.id === id) ? 1 : 0.1);
          d3.selectAll("text").style("opacity", (d:any) => (map.has(id+","+d.id)||d.id === id) ? 1 : 0.1);

      }
    )
  }

  getYearly(networkIds: number[]) {
    d3.selectAll("text").style("opacity", 1);
    d3.selectAll("path").style("opacity",1);
    d3.selectAll("circle").style("opacity", 1);
    this.networkService.getNetworkYears(networkIds);
    this.networkService.getMap().subscribe(
      data => {
        this.map = data;
        this.drawYearly(1904, "default")
      }
    );
  }

  resetLocationFilter(networkIds: number[], year: number, lower: boolean, color: string){
    this.networkService.getNetworkYears(networkIds);
    this.networkService.getMap().subscribe(
      data => {
        this.map = data;
        this.drawYearly(year, color)
      }
    );
  }

  drawYearly(year: number, color: string){
    this.currentYear = year;
    this.networkService.getMap().subscribe(
      data => {
        this.drawNodeLink(data.get(year)!.nodes, data.get(year)!.links, true, color);
      }
    )
  }

  emitArtist(d: any,i: any){
    console.log(i.id);
    d3.selectAll("text")
      .filter(function() {
        return d3.select(this).attr("id") === (String(i.id))
      }).style("font-weight","bolder");
  }

  mouseOut(){
    this.artistData.emit(null);
    this.countries.emit(null);
    this.cities.emit(null);
    d3.selectAll("text").style("font-weight", "normal");
    d3.selectAll("text").style("opacity", 1);
    d3.selectAll("path").style("opacity",1);
    d3.selectAll("circle").style("opacity", 1);
    d3.selectAll("circle").attr('stroke', 'white').attr('stroke-width', 1.5);
  }

  toggleGranularity(){
    this.granularity = !this.granularity;
    console.log(this.granularity)
  }

  getTotal(ids: number[]){
    d3.selectAll("g").remove();
    this.networkService.getNetwork(ids).subscribe(
      data => {
        this.networkDto = data;
       // this.redrawPathsYearly(1913);
        this.drawNodeLink(this.networkDto.nodes, this.networkDto.links, false, "default");
      }
    )
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
   * Copyright 2012–2020 Mike Bostock
   *
   * Permission to use, copy, modify, and/or distribute this software for any
   * purpose with or without fee is hereby granted, provided that the above
   * copyright notice and this permission notice appear in all copies.
   *
   * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
   * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
   * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
   * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
   * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
   * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
   * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
   */
  drawNodeLink(nodes: any, links: any, yearly: boolean, color: string){
    if(yearly){
      d3.select("#networkYearly").selectAll("*").remove();
    }
        this.links = links.map((d:any) => Object.create(d));
        this.nodes = nodes.map((d:any) => Object.create(d));

         const simulation = d3
          .forceSimulation(this.nodes)
          .force(
            'link',
            d3.forceLink(this.links).id((d: any) => d.id),
          )
          .force('charge', d3.forceManyBody().strength(-60))
          .force('x', d3.forceX(0))
          .force('y', d3.forceY(0))
           .force(
            'collide',
            d3.forceCollide( 70),
          )
          .force('center', d3.forceCenter(window.innerWidth/2, window.innerHeight/2)).stop();

        for (var i = 0; i < 250; ++i) simulation.tick();

        const drag = (simulation: d3.Simulation<any, any>) => {
          function dragstarted(event: { active: any; }, d: { fx: any; x: any; fy: any; y: any; }) {
            simulation.stop();
          }

          function dragged(event: { x: any; y: any; }, d: { fx: any; fy: any; x: any; y: any; }) {
            simulation.restart();
            d.fx = event.x;
            d.fy = event.y;
          }

          // @ts-ignore
          return d3.drag().on('start', dragstarted).on('drag', dragged);
        };

        const pipe = new CityPipe();

        // @ts-ignore
        if(yearly){
          this.svgYearly = d3
            .select<SVGSVGElement, any>('#networkYearly')
            .attr('preserveAspectRatio', 'xMaxYMax meet')
            .attr('viewBox', '0 0' + ' ' + window.innerWidth + ' ' + window.innerHeight)
            .call(d3.zoom<SVGSVGElement, any>().transform, d3.zoomIdentity.translate(0, 0).scale(1))
            .call(d3.zoom<SVGSVGElement, any>().on('zoom', e => this.svgYearly.attr('transform', e.transform)))
            .append('g')
            .attr('transform', `translate(${0}, ${0})scale(${1})`);

          this.drawElems(this.svgYearly, pipe, color, drag, simulation, true);

        } else {
          this.svgTotal = d3
            .select<SVGSVGElement, any>('#total')
            .attr('preserveAspectRatio', 'xMaxYMax meet')
            .attr('viewBox', '0 0' + ' ' + window.innerWidth + ' ' + window.innerHeight)
            .call(d3.zoom<SVGSVGElement, any>().transform, d3.zoomIdentity.translate(0, 0).scale(1))
            .call(d3.zoom<SVGSVGElement, any>().on('zoom', e => this.svgTotal.attr('transform', e.transform)))
            .append('g')
            .attr('transform', `translate(${0}, ${0})scale(${1})`);

          this.drawElems(this.svgTotal, pipe, color, drag, simulation, false);
        }
      }

  switchSVG() {
    let svg1 = document.getElementById("total")!.style.display;
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
   * Copyright 2012–2020 Mike Bostock
   *
   * Permission to use, copy, modify, and/or distribute this software for any
   * purpose with or without fee is hereby granted, provided that the above
   * copyright notice and this permission notice appear in all copies.
   *
   * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
   * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
   * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
   * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
   * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
   * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
   * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
   */
  drawElems(svg: any, pipe: CityPipe, color: string, drag: any, simulation: any, yearly: boolean){
    let link = svg
      .append('g').attr("id", "links")
      .attr('fill', 'none')
      .selectAll('path')
      .data(this.links)
      .join('path')
      .attr('stroke-width', (d:any) => 2 + 2 * d.numExhibitions)
      .attr("source", (d:any)=>d.source.id)
      .attr("target", (d:any)=>d.target.id)
      .style("stroke", "var(--" + pipe.transform(color) + ")");
       link.style('cursor', 'pointer');

    let node = svg
      .append('g')
      .attr('fill', 'currentColor')
      .attr('stroke-linecap', 'round')
      .attr('stroke-linejoin', 'round')
      .selectAll('g')
      .data(this.nodes)
      .join('g')
      .call(drag(simulation.restart()));

    node
      .append('circle')
      .attr('stroke', 'white')
      .attr('id', (d: { id: any; }) => d.id)
      .attr('stroke-width', 1.5)
      .attr('r', 25)
      .attr('fill', 'black');

    node
      .append('text')
      .attr('x', 45)
      .attr('y', '0.31em')
      .attr('id', (d:any) =>d.id)
      .text((d: { name: any; }) => d.name)

    node.on('mouseover', (d: any, i: any) => {
      this.showArtist(d, i, yearly);
    });
    link.on('mouseover', (d: any, i: any) => {
      this.showLink(d, i, yearly);
    });
    link.on('click', (d:any, i:any) => {this.selection.emit(i.source.id + "," +i.target.id)})
    link.on('mouseout', (d:any, i:any) => {this.mouseOut()});
    node.on('mouseout', (d:any, i:any) => {this.mouseOut()});

    simulation.on('tick', () => {
      link.attr('d', (d: { source: { x: any; y: any; }; target: { x: any; y: any; }; }) => `M${d.source.x},${d.source.y}A${1},0 0 0,1 ${d.target.x},${d.target.y}`);
      node.attr('transform', (d: { x: any; y: any; }) => `translate(${d.x},${d.y})`);

    });

    this.reColor();

  }
}
