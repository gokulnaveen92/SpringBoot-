class User{
    constructor(public name :string,public email:string,public password:string,public country:string){
    }
    
    validateData = function(){
        const emaiPattern : boolean = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.email);
        let pro = new Promise((resolve,reject)=>{
            if(!this.name){
                reject("Name must not be empty.");
                return;
            }else if(!emaiPattern){
                reject("Email must be a valid email address.");
                return;
            }else if(this.password.length<8){
                reject("Password must be at least 8 characters long.");
                return;
            }else if(this.country === "none"){
                reject("Country must be selected from the predefined list.");
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
    let password:string = (<HTMLInputElement>document.getElementById("password")).value;
    let country:string = (<HTMLInputElement>document.getElementById("country")).value;

    let user = new User(name,email,password,country);
    user.validateData().then((s) => {
        document.getElementById("errorMessage").innerHTML="";
        document.getElementById("successMessage").innerHTML=""+s;
    }).catch((e)=>{
        document.getElementById("errorMessage").innerHTML=""+e;
    });
    
}
