import * as moment from "moment";
import { ICandidate } from "../../model/candidate/candidate.interface";
import { IExperience } from "../../model/experience/experience.interface";

export function parseExperience(experience: IExperience) : IExperience {
    let startDate = new Date(experience.startDate);
    let endDate = new Date(experience.endDate!);

    return{
        ...experience,
        startDate: startDate,
        endDate: endDate
    }
}

export function parseCandidate(candidate: ICandidate) : ICandidate {
    let bdate = moment(candidate.birthDate).format("YYYY-MM-DD");

    let observations = candidate.observation.trim();
    let observation = observations ? observations : '';

    let experiences = candidate.experiences.map(experience => parseExperienceInCandidate(experience, candidate.id));

    return{
        ...candidate,
        firstName: candidate.firstName.trim(),
        lastName: candidate.lastName.trim(),
        birthDate: new Date(bdate),
        observation: observation,
        experiences: experiences
    }
}

function parseExperienceInCandidate(experience: IExperience, id?: string) : IExperience {
    let startDate = moment(experience.startDate).format("YYYY-MM-DD");
    let endDate;

    if(experience.currentlyWorking){
        endDate = null;
    }
    else{
        endDate = moment(experience.endDate);
        endDate = endDate.day(endDate.endOf('month').day()).format("YYYY-MM-DD");
    }

    let candidate = <ICandidate>{id: id};

    return{
        ...experience,
        title: experience.title.trim(),
        company: experience.company.trim(),
        description: experience.description.trim(),
        startDate: new Date(startDate),
        endDate: endDate ? new Date(endDate) : null,
        candidate: experience.candidate ? experience.candidate : candidate
    }
}

export function parseExperienceDatesToInput(experiences: IExperience[]) : IExperience[] {
    return experiences.map(experience =>{
        let endDate;
        
        if(!experience.currentlyWorking){
            endDate = moment(experience.endDate).format("YYYY-MM");
        }
        else endDate = moment().format("YYYY-MM");

        return{
            ...experience,
            endDate: new Date(endDate)
        }
    });
}