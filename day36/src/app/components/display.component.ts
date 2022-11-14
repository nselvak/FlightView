import { Component, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MapInfoWindow, MapMarker, MapGeocoder } from '@angular/google-maps';
import { Router } from '@angular/router';
import { Observable, Observer, Subject, takeWhile } from 'rxjs';
import { Login, LoginSummary } from '../models';
import { ItemService } from '../service/ItemService';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit {
  

  constructor(private router: Router, private fb: FormBuilder, private itemSvc: ItemService) {

   }

  origin:any
  destination:any


  active = 1;

  
  form!: FormGroup
  form2!: FormGroup

  send!: FormGroup
  
  addItem = false
  listItems = false

  loginF = false
  registerF = false

  users: LoginSummary[] = []


  @Output()
  newRequest = new Subject<Login>()

  private createForm(): FormGroup{
    return this.fb.group({
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      password: this.fb.control<string>('', [Validators.required]),
      key: this.fb.control<number>(1),
    })
  }  

  public ngOnInit(): void {


  }

  processForm() {
    const reg: Login= this.form.value as Login
    console.info(">>> Login: ", reg)
    // this.newReg.next(reg)
    // this.router.navigate(["/display", this.form.get('email')?.value])

    this.itemSvc.getRequest(reg.email)
    .then(result=> {
      this.users = result
      this.itemSvc.updateEmail(reg.email)
      console.info(">>> Get request (email): ", this.form.get('email')?.value)
      console.info(">>> Get request (user): ", this.users)


      if (this.users.length==1 && this.users[0].email==this.form.get('email')?.value &&  this.users[0].password==this.form.get('password')?.value) {
        this.router.navigate(['/up'])
        } else {
          this.lf()
    
        }

    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })

    


    


  }

  processRegister() {
    this.form2.controls['key'].setValue(0)
    const reg: Login= this.form2.value as Login
    console.info(">>> Registration: ", reg)

    this.itemSvc.getRequest(reg.email)
    .then(result=> {
      this.users = result
      this.itemSvc.updateEmail(reg.email)
      console.info(">>> Get request (email): ", this.form2.get('email')?.value)
      console.info(">>> Get request (user): ", this.users)
      if (this.users.length==0) {
        this.itemSvc.newRequest(reg)
        this.router.navigate(['/up'])
      } else {
        this.rf()
      }
    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })

  }

  add(){
    this.addItem = true
    this.listItems = false
    this.loginF = false
    this.registerF = false
    this.form = this.createForm()
  }

  list() {
    this.addItem = false
    this.listItems = true
    this.loginF = false
    this.registerF = false
    this.form2 = this.createForm()

  }

  lf() {
    this.addItem = false
    this.listItems = false
    this.loginF = true
    this.registerF = false

  }
  rf() {
    this.addItem = false
    this.listItems = false
    this.loginF = false
    this.registerF = true

  }

 

}


