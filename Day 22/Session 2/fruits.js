//write your code here
const fruits = ["apple","banana","orange","grape","watermelon"];

function addFruit(fruit){
    fruits.push(fruit);
}

function removeFruit(fruit){
    let x = fruits.indexOf(fruit);
    if(x!=-1)
    fruits.splice(x,1);
}

addFruit("lemon");
console.log(fruits);

removeFruit("grape");
console.log(fruits);

module.exports={addFruit,removeFruit,fruits};

