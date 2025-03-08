import { EnrollmentDetailRequest } from "./enrollment-detail-request";

export interface EnrollmentRequest{
    studentId: number;
    comments: string;
    enrollmentDetails: Array<EnrollmentDetailRequest>;
}