import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TextFormatterPipe } from './pipes/text-formatter.pipe';
import { TextFormatterComponent } from './text-formatter/text-formatter.component';
import { FormsModule } from '@angular/forms';
import { LowerCasePipe, TitleCasePipe, UpperCasePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    TextFormatterPipe,
    TextFormatterComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    UpperCasePipe,
    LowerCasePipe,
    TitleCasePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
