class Student{
    constructor(name,rollNo,department){
        this.name = name;
        this.rollNo = rollNo;
        this.department=department;

        this.getStudentInfo=function(){
            return"Name: "+this.name+", Roll No: "+this.rollNo+", Department: "+this.department;
        }
    }
}

let studentList=[];

function addStudent(name,rollNo,department){
    studentList.push(createStudent(name,rollNo,department))
}

function createStudent(name,rollNo,department){
    return new Student(name,rollNo,department);
}

function displayStudentList(){
    studentList.forEach(student=>
        console.log(student.getStudentInfo())
        )
}

function calculateTotalStudents(){
    return studentList.length;
}

module.exports={Student,addStudent,displayStudentList,calculateTotalStudents,createStudent,studentList};
