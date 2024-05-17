function checkNumberSign(num){
    if(num>0){
        return "The number is positive.";
    }else if(num==0){
        return "The number is zero.";
    }else{
        return "The number is negative.";
    }
}

function countDownFromTen(){
    /*let x=10;
    while(x>0){
        console.log(x);
        x--;
    }*/

    for(let i=10;i>0;i--){
        console.log(i);
    }
}

console.log(checkNumberSign(10));
module.exports={countDownFromTen,checkNumberSign};
