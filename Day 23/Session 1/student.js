class Student{
    constructor(name,age,grades = []){
        this.name = name;
        this.age = age;
        this.grades = grades;

    }

}

let students = [];

let student1 = new Student("gokul",22,[86,87,89]);
let student2 = new Student("loki",21,[90,92,97]);

Student.prototype.addGrade = function(grade){
    this.grades.push(grade);
}

Student.prototype.getAverageGrade = function(){
    let total = this.grades.reduce((sum,g)=>sum+g,0);
    let len = this.grades.length;
    return total/len;
}




student1.addGrade(87);
console.log(student1.name);
console.log(student1.age);
console.log(student1.grades);
console.log(student1.getAverageGrade());

module.exports = Student;
