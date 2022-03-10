import { IExperience } from "../experience/experience.interface";
import { IUser } from "../user/user.interface";

export interface ICandidate {
    id?: string;
    firstName: string;
    lastName: string;
    birthDate: Date;
    experiences: IExperience[];
    observation: string;
    user?: IUser
}
