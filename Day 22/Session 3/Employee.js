class Employee{
    constructor(id,name,role){
        this.id = id;
        this.name=name;
        this.role=role;

        this.getEmployeeInfo = function(){
            return "ID: "+this.id+", Name: "+this.name+", Role: "+this.role;
        }
    }

}

let employeeList = [];

function addEmployee(id,name,role){
    employeeList.push(createEmployee(id,name,role));
}

function createEmployee(id,name,role){
    return new Employee(id,name,role)
}

function displayEmployeeList(){
    employeeList.forEach(emp=>
        console.log(emp.getEmployeeInfo())
        );
}

function calculateTotalEmployees(){
    return employeeList.length;
}

module.exports={Employee,addEmployee,displayEmployeeList,calculateTotalEmployees,createEmployee,employeeList};
