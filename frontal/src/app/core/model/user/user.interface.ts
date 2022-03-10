import { Avataaar } from "../avataaar/avataaar.model";
import { ICandidate } from "../candidate/candidate.interface";

export interface IUser{
    id?: string,
    email: string,
    password?: string,
    createAt?: Date,
    role?: string,
    candidate?: ICandidate,
    firstName?: string,
    lastName?: string,
    fullName?: string,
    avatar?: Avataaar
}