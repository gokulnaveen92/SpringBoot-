let fruits:string[]=[];
 
function addFruit(){
    let fruit:string = (<HTMLInputElement>document.getElementById("fruitInput")).value;
   
    if(!fruit){
        document.getElementById("errorMessage").innerHTML="Fruit name is required.";
        return;
    }
 
    fruits.push(fruit);
    let strfruit:string = fruits.join(",");
 
    document.getElementById("fruitList").innerHTML="Fruits: "+strfruit;
}
