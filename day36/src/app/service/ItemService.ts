import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { firstValueFrom, lastValueFrom, Subject } from "rxjs"
import { Flight, Login, LoginSummary, Response } from "../models"


@Injectable()
export class ItemService {

  constructor(private http: HttpClient) { }

  email = ''
  onNewResult = new Subject<Response>()
  result = new Response

  res: any[] = []
  flight!: Flight


  updateEmail(str: string) {

    this.email= str

  }

  updateFlight(fligh: Flight) {
    this.flight = fligh 
    fligh.email = this.email

    return fligh
  }


  updateResult(resp: any) {

    this.res.push(resp)
  }



  newRequest(item: Login): Promise<Response> {

    console.log("Request", item)

    const headers = new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Accept', 'application/json')

      return lastValueFrom(
        this.http.post<Response>('/api/display', item, { headers })
          //.pipe()
      )
  }

  getRequest(email:string) : Promise<LoginSummary[]> {
    return firstValueFrom(
      this.http.get<LoginSummary[]>(`/api/display/${email}`)
    )
  }


  newFlight(item: Flight): Promise<Response[]> {

    console.log("Request", item)

    const headers = new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Accept', 'application/json')

      return lastValueFrom(
        this.http.post<Response[]>('/api/up', item, { headers })
          //.pipe()
      )
  }



  // newRequest(): Promise<any> {
  //     return lastValueFrom(
  //       this.http.get<any>(URL)
  //         //.pipe()
  //     )
  // }



}