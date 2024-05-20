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

    let min_num = Math.min(...numArr);
    let max_num = Math.max(...numArr);
    let total = numArr.reduce((sum,n)=>sum+n,0);

    document.getElementById("minimumNo").innerHTML="Minimum number: "+min_num;
    document.getElementById("maximumNo").innerHTML="Maximum number: "+max_num;
    document.getElementById("sumOfAllNumbers").innerHTML="Sum of all numbers: "+total;



}
