import { IUser } from "../user/user.interface";

export interface Session {
    hash: string;
    user: IUser;
}