class Task{
    constructor(public task:string,public description:string,public priority:string,public dueDate:string){

    }

    validateData = function(){
        let pro = new Promise((resolve,reject)=>{
            if(!this.task){
                reject("Title must not be empty.");
                return;
            }else if(!this.description){
                reject("Description must not be empty.");
                return;
            }else if(this.priority === "none"){
                reject("Priority must be selected from the predefined list.");
            }else if(!this.dueDate){
                reject("Due date must not be empty.");
                return;
            }else{
                resolve("Added the task successfully.");
            }
        });
         return pro;
    }
}

function submitForm(){
    let name:string = (<HTMLInputElement>document.getElementById("title")).value;
    let description:string = (<HTMLInputElement>document.getElementById("description")).value;
    let priority:string = (<HTMLInputElement>document.getElementById("priority")).value;
    let dueDate:string = (<HTMLInputElement>document.getElementById("dueDate")).value;

    let task = new Task(name,description,priority,dueDate);
    task.validateData().then((s) => {
        document.getElementById("errorMessage").innerHTML="";
        document.getElementById("successMessage").innerHTML=""+s;
    }).catch((e)=>{
        document.getElementById("errorMessage").innerHTML=""+e;
    });
}
