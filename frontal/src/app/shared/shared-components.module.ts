import { NgModule } from "@angular/core";
import { AvataaarComponent } from "./components/avataaar/avataaar.component";


const COMPONENTS = [
    AvataaarComponent
]

@NgModule({
    declarations: COMPONENTS,
    exports: COMPONENTS
})
export class SharedComponentsModule {}