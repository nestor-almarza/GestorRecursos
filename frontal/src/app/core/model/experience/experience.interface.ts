import { ICandidate } from "../candidate/candidate.interface";

export interface IExperience {
  id?: string;
  company: string;
  title: string;
  startDate: Date;
  endDate: Date | null;
  duration?: number;
  currentlyWorking: boolean;
  description: string;
  candidate: ICandidate;
}