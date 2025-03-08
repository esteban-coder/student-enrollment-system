import { Section } from "./section";

export interface CourseWithSections {
    id: number;
    courseCode: string;
    name: string;
    description: string;
    credits: number;
    // department: Deparment;
    prerequisites: string;
    status: string;
    sections: Array<Section>;
}