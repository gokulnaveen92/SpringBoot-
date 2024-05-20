function addFruit(){
    let fruit:string = (<HTMLInputElement>document.getElementById("fruitInput")).value;
    let arr1 : string[]=[];

    if(!fruit){
        document.getElementById("errorMessage").innerHTML="Fruit name is required.";
        return;
    }
    
    arr1 = fruit.split(",");
    document.getElementById("errorMessage").innerHTML="";

    document.getElementById("fruitList").innerHTML="Fruits: "+arr1;


}
