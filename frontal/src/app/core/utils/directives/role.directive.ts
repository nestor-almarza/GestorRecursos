import { Directive, Input, TemplateRef, ViewContainerRef } from "@angular/core";
import { Role } from "../../model/role/role.enum";
import { SessionService } from "../../auth/session.service";

@Directive({
    selector: '[hasRole]'
})
export class RoleDirective{

    roles!: Role[];

    constructor(
        private templateRef: TemplateRef<any>,
        private viewContainer: ViewContainerRef,
        private sessionService: SessionService
    ){}

    @Input()
    set hasRole(roles: Role[]){
        this.roles = Array.isArray(roles) ? roles : [roles];

        let userRole = this.sessionService.getUserSessionData().role;

        if(roles.some(r => r === userRole)){
            this.viewContainer.createEmbeddedView(this.templateRef);
        } 
        else {
            this.viewContainer.clear();
        }
    }
}