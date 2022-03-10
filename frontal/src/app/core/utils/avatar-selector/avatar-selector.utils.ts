import { Avataaar } from "../../model/avataaar/avataaar.model"
import { AspectLists } from "./aspect-lists.utils";
import { CustomIterator } from "./custom-iterator.utils";

export class AvatarSelector {

    avatar: Avataaar;

    aspects: {[key: string]: CustomIterator} = {
        skinColor: new CustomIterator(AspectLists.skinColor),
        topType: new CustomIterator(AspectLists.topType),
        hairColor: new CustomIterator(AspectLists.hairColor),
        eyebrowType: new CustomIterator(AspectLists.eyebrowType),
        eyeType: new CustomIterator(AspectLists.eyesType),
        accessoriesType: new CustomIterator(AspectLists.accessoriesType),
        facialHairType: new CustomIterator(AspectLists.facialHairType),
        mouthType: new CustomIterator(AspectLists.mouthType),
        clotheType: new CustomIterator(AspectLists.clotheType),
    }

    constructor(avatar: Avataaar){
        this.avatar = avatar;

        // Initialize avatar selector for the current avatar values
        for (let key in this.aspects){
            this.aspects[key].findAndSetIndex(this.avatar[key])
        }
    }

    getRandomAvatar(){
        for (let key in this.aspects){
            this.avatar[key] = this.aspects[key].random();
        }

        return this.avatar;
    }

    next(aspect: string){

        return this.aspects[aspect].next()
    }

    back(aspect: string){
        return this.aspects[aspect].back()
    }

}