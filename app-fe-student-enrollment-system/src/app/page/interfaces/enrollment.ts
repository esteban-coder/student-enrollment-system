import { EnrollmentDetail } from "./enrollment-detail";
import { Student } from "./student";

export interface Enrollment
{
    id: number
    student: Student;
    academicPeriod: string;
    totalCredits: number;
    totalEnrolledCourses: number;
    comments: string;
    enrollmentDate: string;
    status: string;
    details: Array<EnrollmentDetail>;
    totalCreditsEarned: number;
    weightedAverage: number;
}