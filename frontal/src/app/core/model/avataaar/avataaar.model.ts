import { IUser } from "../user/user.interface";

export interface Avataaar {

    [key: string]: string | IUser | undefined;
    
    id?: string;
    user?: IUser;
    skinColor: string;
    topType: string;
    hairColor: string;
    eyebrowType: string;
    eyeType: string;
    accessoriesType: string;
    facialHairType: string;
    mouthType: string;
    clotheType: string;
}

