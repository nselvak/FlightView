import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Flight } from '../models';
import { ItemService } from '../service/ItemService';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {


  constructor(private router: Router, private fb: FormBuilder, private itemSvc: ItemService) { }
  
  form1!: FormGroup


  private createForm1(): FormGroup{
    return this.fb.group({
      adult: this.fb.control<number>(1, [Validators.required]),
      origin: this.fb.control<string>('', [Validators.required]),
      destination: this.fb.control<string>('', [Validators.required]),
      date: new FormControl(new Date(), [Validators.required]),
      email: this.fb.control<string>(''),

    })
  }  

  public ngOnInit(): void {
    this.form1 = this.createForm1()
  }

  post() {

    // console.info(">>> const date: ", this.form1.controls['date'].value)
    const d = new Date(this.form1.controls['date'].value)
    console.info(">>> const d: ", d)
    const dde = d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate()
    console.info(">>> const dde: ", dde)

    this.form1.controls['date'].setValue(dde.toString())
    const reg: Flight= this.form1.value as Flight
    console.info(">>> Flight: ", reg)
    // this.newReg.next(reg)
    // this.router.navigate(["/display", this.form.get('email')?.value])



    const flight:Flight = this.itemSvc.updateFlight(reg)
    console.info(">>> Flight: ", flight)

    this.itemSvc.newFlight(flight)
    .then(result=> {
      console.info(">>> Get request (email): ", this.form1.get('email')?.value)
      console.info(">>> Get request (user): ", result)
      
      // this.itemSvc.onNewResult.next(result)
      this.itemSvc.updateResult(result)
      
      this.router.navigate(["/result"])
    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })
    
  }

}
