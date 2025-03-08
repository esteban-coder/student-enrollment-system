import { Course } from "./course";
import { Instructor } from "./instructor";

export interface SectionWithCourse {
    id: number;
    course: Course;
    sectionCode: string;
    instructor: Instructor;
    academicPeriod: string;
    startDate: string;
    endDate: string;
    schedule: string;
    roomNumber: string;
    maxCapacity: number;
    enrolledStudentCount: number;
    status: string;
}