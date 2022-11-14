import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MapGeocoder, MapInfoWindow, MapMarker } from '@angular/google-maps';
import Dexie from 'dexie';
import { Subscription } from 'rxjs';
import { DataBase } from '../models';
import { ItemService } from '../service/ItemService';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent extends Dexie implements OnInit, OnDestroy {



  sub$!: Subscription
  resp: any[] = []
  datasource: any[] = []
  datasource1: any[] = []
  datasource2: any[] = []
  datasource3: any[] = []



  data!: Dexie.Table<DataBase, string>;


  constructor(private itemSvc: ItemService, geocoder: MapGeocoder) {
    super('FlightData')
    this.version(2).stores({
      data:'++id'
    })

    

    geocoder.geocode({
      address: this.itemSvc.flight.origin + ' airport'
    }).subscribe(({results}) => {
      // console.log('>>>>>> Origin results map', results);
      // console.log(results[0].geometry.location);
      this.origin = results[0].geometry.location
      // console.log('>>>>>> Origin results map', this.origin);
      // console.log(this.origin.lat);
      // console.log(this.origin.lng);
    });

    geocoder.geocode({
      address: this.itemSvc.flight.destination + ' airport'
    }).subscribe(({results}) => {
      // console.log('>>>>>> Destination results map',results);
      // console.log(results[0].geometry.location);
      this.destination = results[0].geometry.location
      // console.log('>>>>>> Destination results map', this.destination);
      // console.log(this.destination.lat);
      // console.log(this.destination.lng);
    });

    



   }

  origin:any
  destination:any




  ngOnInit(): void {

    this.resp = this.itemSvc.res
    // console.info(">>>>> check",  this.itemSvc.res)
    this.data.add({email: this.itemSvc.email, data: this.resp})

    // console.info(">>>>> information",  this.resp)
    // console.info(">>>>> items",  this.resp[0])
    // console.info(">>>>> best option",  this.resp[0][0].items)
    // console.info(">>>>> link",  this.resp[0][0].items[0].deeplink)
    // console.info(">>>>> origin",  this.resp[0][0].items[0].legs[0].origin)
    // console.info(">>>>> origin name",  this.resp[0][0].items[0].legs[0].origin.name)

    // console.info(">>>>> destination",  this.resp[0][0].items[0].legs[0].destination)
    // console.info(">>>>> destination name",  this.resp[0][0].items[0].legs[0].destination.name)


    this.datasource = this.resp[0][0].items;
    this.datasource1 = this.resp[0][1].items;
    this.datasource2 = this.resp[0][2].items;
    this.datasource3 = this.resp[0][3].items;
    console.info(">>>>> Datasource",  this.datasource)
    console.info(">>>>> Datasource",  this.datasource[0].legs[0].origin.name)




    /// only 1 item in array
    /// this.resp[0][0].items - best
    /// this.resp[0][1].items - fastest
    /// this.resp[0][2].items - cheapest
    /// this.resp[0][3].items - direct


    

    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      }
    })



  }

  ngOnDestroy(): void {
      this.sub$.unsubscribe()
  }


  /////  MAP


  zoom = 12;
  maxZoom = 15;
  minZoom = 8;
  center!: google.maps.LatLngLiteral;
  options: google.maps.MapOptions = {
    zoomControl: false,
    scrollwheel: false,
    disableDoubleClickZoom: true,
    mapTypeId: 'hybrid',
    maxZoom:this.maxZoom,
    minZoom:this.minZoom,
  }
  markers = []  as  any;
  infoContent = ''

  
  

  addMarker() {
    this.addMark(this.origin, "Origin")
    this.addMark(this.destination, "Destination")
  }


  addMark(item:any, str: string) {

    this.markers.push({

      position: item,
      label: {
        color: 'red',
        text: 'Marker label ' + str,
      },
      title: 'Marker title ' + str,
      draggable: true,
      options: { animation: google.maps.Animation.DROP },
    });
  }

  @ViewChild(MapInfoWindow, { static: false })
  info!: MapInfoWindow;
  
  openInfo(marker: MapMarker, content: string) {
    this.infoContent = content;
    this.info.open(marker)
  }

  //// Map end

  //// Table

  displayedColumns: string[] = ['position', 'name', 'depart', 'arrival', 'time', 'weight', 'symbol'];

  

  /// Table end



}
