var name = "demo";
console.log(name);
var num1 = 10,num2 = 5,sum;
sum = num1 + num2;
console.log(sum);
var isStudent = true;
console.log(isStudent);

function calculateArea(radius){
    var area =Math.PI*radius*radius;
    return area;
}

//for run it in node.js runtime environment
module.exports={calculateArea,name,sum,isStudent};
