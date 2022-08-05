import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NetworkService} from "../../../services/HttpServices/network.service";
import {ArtistService} from "../../../services/HttpServices/artist.service";
import {NetworkDto} from "../../../dtos/NetworkDto";
import {ArtistDto} from "../../../dtos/ArtistDto";
import * as d3 from "d3";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-network-detail',
  templateUrl: './network-detail.component.html',
  styleUrls: ['./network-detail.component.scss']
})
export class NetworkDetailComponent implements OnInit {

  @Output() egoEmit: EventEmitter<any> = new EventEmitter();
  @Output() loaded: EventEmitter<any> = new EventEmitter();

  constructor(private networkService: NetworkService, private artistService: ArtistService) { }

  networkDto: NetworkDto = new NetworkDto([],[]);
  artistDto!: ArtistDto;
  show: boolean = false;
  selected = undefined;
  lastEgo = -1;
  ego: number = 0;
  private lastEgo$: BehaviorSubject<number> = new BehaviorSubject<number>(-1);

  ngOnInit(): void {
  }

  getArtistById(id: number){
    d3.selectAll("g").remove();
    this.networkService.getTotalEgoNetwork(id,0).subscribe(
      data => {
        this.lastEgo$.subscribe(
         lastEgo=> {
           this.lastEgo = lastEgo;
         })
        this.networkDto = data;
        this.loaded.emit(false);
        this.ego = this.networkDto.nodes[0].id;
        this.drawNodeLink();
      }
    )
  }

  setEgo(){
    this.lastEgo$.next(this.networkDto.nodes[0].id);
  }

  showArtist(d: any, i:any){

    this.egoEmit.emit(i);

    if(!this.selected){
      this.selected = i.id;
      d3.selectAll("circle").filter(function() {return d3.select(this).attr("id") == i.id})
        .attr('stroke', 'yellow').attr('stroke-width',7);
    } else {
      const value = this.selected;
      d3.selectAll("circle").filter(function() {
        return d3.select(this).attr("id") == value})
        .attr('stroke', 'white').attr('stroke-width',1.5);
      this.selected = i.id;
      d3.selectAll("circle").filter(function() { // @ts-ignore
        return d3.select(this).attr("id") == i.id})
        .attr('stroke', 'yellow').attr('stroke-width',7);
    }

    this.artistService.getArtistsById(i.id).subscribe(
      artist => {
        this.artistDto = artist;
        console.log(artist)
      }
    )
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
  drawNodeLink(){
    const links = this.networkDto.links.map(d => Object.create(d));
    const nodes = this.networkDto.nodes.map(d => Object.create(d));

    const simulation = d3
      .forceSimulation(nodes)
      .force(
        'link',
        d3.forceLink(links).id((d:any) => d.id),
      )
      .force('charge', d3.forceManyBody().strength(-60))
      .force('x', d3.forceX(0))
      .force('y', d3.forceY(0))
      .force(
        'collide',
        d3.forceCollide(60),
      )
      .force('center', d3.forceCenter(window.innerWidth, window.innerHeight)).stop();

    for (var i = 0; i < 250; ++i) simulation.tick();

    const drag = (simulation: d3.Simulation<any, any>) => {
      function dragstarted(event: { active: any; }, d: { fx: any; x: any; fy: any; y: any; }) {
        simulation.stop();
      }

      function dragged(event: { x: any; y: any; }, d: { fx: any; fy: any; x:any;y:any;}) {
        simulation.restart();
        d.fx = event.x;
        d.fy = event.y;
      }

      // @ts-ignore
      return d3.drag().on('start', dragstarted).on('drag', dragged);
    };

    // @ts-ignore
    const svg = d3
      .select<SVGSVGElement,any>('svg')
      .attr('preserveAspectRatio', 'xMaxYMax meet')
      .attr('viewBox', '0 0' + ' ' + window.innerWidth + ' ' + window.innerHeight)
      .call(d3.zoom<SVGSVGElement,any>().transform, d3.zoomIdentity.translate(0, 0).scale(0.5))
      .call(d3.zoom<SVGSVGElement,any>().on('zoom', e => svg.attr('transform', e.transform)))
      .append('g')
      .attr('transform', `translate(${0}, ${0})scale(${0.5})`);

    const link = svg
      .append('g')
      .attr('fill', 'none')
      .attr('stroke-width', 1.5)
      .selectAll('path')
      .data(links)
      .join('path')
      .attr('stroke', 'grey'); //d => color(d.type))

    const node = svg
      .append('g')
      .attr('fill', 'currentColor')
      .attr('stroke-linecap', 'round')
      .attr('stroke-linejoin', 'round')
      .selectAll('g')
      .data(nodes)
      .join('g')
      .call(drag(simulation.restart()));

    node
      .append('circle')
      .attr('stroke', 'white')
      .attr('id', (d: { id: any; }) => d.id)
      .attr('stroke-width', 1.5)
      .attr('r', 25)
      .style("fill", (d:any) => d.id === this.lastEgo ? "green" : ((this.ego === d.id) ? 'yellow' : 'black'))

    node
      .append('text')
      .attr('x', 45)
      .attr('y', '0.31em')
      .text((d: { name: any; }) => d.name)

    node.on('click', (d: any, i: any) =>
    {this.showArtist(d, i);
    });
    node.style('cursor', 'pointer');

    simulation.on('tick', () => {
      link.attr('d', (d: { source: { x: any; y: any; }; target: { x: any; y: any; }; }) => `M${d.source.x},${d.source.y}A${1},0 0 0,1 ${d.target.x},${d.target.y}`);
      node.attr('transform', (d: { x: any; y: any; }) => `translate(${d.x},${d.y})`);
    });
    this.loaded.emit(true);
  }

  colorDefault() {
    d3.selectAll("circle").style("fill", (d:any) => d.id === this.lastEgo ? "green" : ((this.ego === d.id) ? 'yellow' : 'black'));
  }

  colorByNationality() {
    d3.selectAll("circle").style("fill", (d:any) => 'var(--' +d.nationality + ')');
  }

  colorBySex() {
        d3.selectAll("circle").style('fill', (d: any) => d.id === this.lastEgo ? "green" : (this.ego === d.id ? 'yellow' : (d.sex === 'M' ? 'blue' : (d.sex == 'F' ? 'red' : 'grey'))));
  }
}
