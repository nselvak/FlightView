import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';

import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { DisplayComponent } from './components/display.component';
import { RouterModule, Routes } from '@angular/router';
import { UploadComponent } from './components/upload.component';
import { CameraService } from './service/WebcamService';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ResultComponent } from './components/result.component';
import { ItemService } from './service/ItemService';
import { DatePipe } from '@angular/common'
import { MaterialModule } from './material.module';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GoogleMapsModule } from '@angular/google-maps';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment'


const appPath: Routes = [
  { path: 'display', component: DisplayComponent },
  { path: 'up', component: UploadComponent },
  { path: 'display/:email', component: DisplayComponent },
  { path: 'result', component: ResultComponent },
  { path: '**', redirectTo: '/display', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    DisplayComponent,UploadComponent, ResultComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule,
    RouterModule.forRoot(appPath),
    BrowserAnimationsModule,
    DatePipe,
    NgbModule,
    // AgmCoreModule.forRoot({
    //   apiKey: 'AIzaSyCIIFaFv7dF48acg8bKQlTHUwWz2HmmmLs'
    // }),
    GoogleMapsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [CameraService, ItemService],
  bootstrap: [AppComponent]
})
export class AppModule { }
