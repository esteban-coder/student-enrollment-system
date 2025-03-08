export interface Course{
        id: number;
        courseCode: string;
        name: string;
        description: string;
        credits: number;
        // department: Deparment;
        prerequisites: string;
        status: string;
        // sections: Array<Section>
}