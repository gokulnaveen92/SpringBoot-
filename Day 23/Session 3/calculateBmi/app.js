function calculateBMI(){
    let weight = parseFloat(document.getElementById("weight").value);
    let height = parseFloat(document.getElementById("height").value);

    document.getElementById("invalidwt").innerHTML="";
    document.getElementById("invalidht").innerHTML="";
    

    if(isNaN(weight) || weight>640){
        document.getElementById("invalidwt").innerHTML="Invalid weight.";
        document.getElementById("invalidwt").classList.add("red");
        return;
    }
    
    if(isNaN(height) || height>280){
        document.getElementById("invalidht").innerHTML="Invalid height.";
        document.getElementById("invalidht").classList.add("red");
        return;
    }

    height = height/100;

    let BMI = weight/(height*height);
    BMI = BMI.toFixed(2);
    document.getElementById("result").innerHTML="BMI: "+BMI;

    if(BMI<18.5){
        document.getElementById("weightStatus").innerHTML="Weight Status: Under Weight";
        document.getElementById("weightStatus").classList.add("red");
    }
    else if(BMI>=18.5 && BMI<=24.9){
        document.getElementById("weightStatus").innerHTML="Weight Status: Healthy Weight";
        document.getElementById("weightStatus").classList.add("green");
    }
    else if(BMI>=25.0 && BMI<=29.9){
        document.getElementById("weightStatus").innerHTML="Weight Status: Over Weight";
        document.getElementById("weightStatus").classList.add("red");
    }
    else if(BMI>29.9){
        document.getElementById("weightStatus").innerHTML="Weight Status: Obesity";
        document.getElementById("weightStatus").classList.add("red");
    }
}
