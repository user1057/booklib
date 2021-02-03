import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BooklibSharedModule } from 'app/shared/shared.module';
import { BooklibCoreModule } from 'app/core/core.module';
import { BooklibAppRoutingModule } from './app-routing.module';
import { BooklibHomeModule } from './home/home.module';
import { BooklibEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    BooklibSharedModule,
    BooklibCoreModule,
    BooklibHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BooklibEntityModule,
    BooklibAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class BooklibAppModule {}
