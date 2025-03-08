import { Section } from "./section";
import { SectionWithCourse } from "./section-with-course";

export interface EnrollmentDetail {

    id: number
    // enrollment: Enrollment
    section: SectionWithCourse
    credits: number
    status: string;
    withdrawalDate: string;
    withdrawalReason: string;
    creditsEarned: number;
    finalGrade: number;
    gradeStatus: string;

}