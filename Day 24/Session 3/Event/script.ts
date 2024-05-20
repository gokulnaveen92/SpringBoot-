class Attendee{
    constructor(public name:string,public email:string,public phoneNumber:string,public event:string){

    }

    validateData=function(){
        const emailPattern : boolean = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.email);
        let pro = new Promise((resolve,reject)=>{
            if(!this.name){
                reject("Name must not be empty.");
                return;
            }else if(!emailPattern){
                reject("Email must be a valid email address.");
                return;
            }else if(this.phoneNumber.length<10){
                reject("Phone number must be a 10-digit number.");
                return;
            }else if(this.event === "none"){
                reject("Event must be selected from the predefined list.");
                return;
            }else{
                resolve("Success! Registration is complete.");
            }
        });
        return pro;
    }
}

function submitForm(){
        let name:string = (<HTMLInputElement>document.getElementById("name")).value;
        let email:string = (<HTMLInputElement>document.getElementById("email")).value;
        let phoneNumber:string = (<HTMLInputElement>document.getElementById("phoneNumber")).value;
        let event:string = (<HTMLInputElement>document.getElementById("event")).value;
    
        let attendee = new Attendee(name,email,phoneNumber,event);
        attendee.validateData().then((s) => {
            document.getElementById("errorMessage").innerHTML="";
            document.getElementById("successMessage").innerHTML=""+s;
        }).catch((e)=>{
            document.getElementById("errorMessage").innerHTML=""+e;
        });
}
