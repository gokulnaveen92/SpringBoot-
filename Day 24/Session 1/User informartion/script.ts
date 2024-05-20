function validate(){
    let name : string = (<HTMLInputElement>document.getElementById("nameInput")).value;
    let age : number = parseInt((<HTMLInputElement>document.getElementById("ageInput")).value);
    let hobbies : string = (<HTMLInputElement>document.getElementById("arrayInput")).value;
    let isStudent:boolean = JSON.parse((<HTMLSelectElement>document.getElementById("isStudentSelect")).value);
    let isTrue:boolean = isStudent === true;

    // type hobby = {
    //     name:string
    // }
    // let hobbieArr :hobby[] = hobbies.split(",").map((hobby) => ({ name: hobby.trim() }));;

    // type hobby = {
    //     name:string
    // }]
    //hobbies = hobbies.trim();
    let hobbieArr :string[] = hobbies.split(",");
    hobbieArr.forEach(hob=>hob.trim());
    
    document.getElementById("sp1").innerHTML="Name: "+name+", Type: "+typeof name;
    document.getElementById("sp2").innerHTML="Age: "+age+", Type: "+typeof age;
    document.getElementById("sp3").innerHTML="Hobbies: "+hobbieArr+", Type: "+typeof hobbieArr;
    document.getElementById("sp4").innerHTML="Student: "+isTrue+", Type: "+typeof isTrue;
    
}
