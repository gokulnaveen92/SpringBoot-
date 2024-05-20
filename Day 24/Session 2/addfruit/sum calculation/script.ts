function calculate(){
    let num1:number = parseInt((<HTMLInputElement>document.getElementById("num1")).value); 
    let num2:number = parseInt((<HTMLInputElement>document.getElementById("num2")).value); 
    let num3:number = parseInt((<HTMLInputElement>document.getElementById("num3")).value); 
    let num4:number = parseInt((<HTMLInputElement>document.getElementById("num4")).value); 
    let num5:number = parseInt((<HTMLInputElement>document.getElementById("num5")).value); 

    if (isNaN(num1) || isNaN(num2) || isNaN(num3) || isNaN(num4) || isNaN(num5)){
        document.getElementById("errorMessage").innerHTML="Enter all the numbers";
        return;
    }

    let numArr : number[] = [num1,num2,num3,num4,num5];
    let evenArr :number[] = numArr.filter(num=>num%2==0);
    let greater_5 :number[] = numArr.filter(num=>num>5);

    let sum :number= evenArr.reduce((sum,n)=>sum+n,0);

    document.getElementById("sumOfEven").innerHTML="Sum of even numbers: "+sum;
    document.getElementById("numbersGreaterThan5").innerHTML="Numbers greater than 5: "+greater_5;
    
}
