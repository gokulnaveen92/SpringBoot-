function calculateEMI(){
    let loanAmt = parseFloat(document.getElementById("loanAmount").value);
    let intrRate = parseFloat(document.getElementById("interestRate").value);
    let loanTm = parseFloat(document.getElementById("loanTerm").value);
    let result = document.getElementById("result");

    let loanCheck =/[0-9]+/.test(loanAmt);
    let iRCheck = /[0-9]+/.test(intrRate);
    let loanTmCheck = /[0-9]+/.test(loanTm);

    if(!(loanCheck && iRCheck && loanTmCheck)){
        result.innerHTML="Please enter valid values.";
        result.classList.add("red");
        return;
    }else if(loanAmt>10000000){
        result.innerHTML="Invalid loan amount.Maximum allowed: Rs. 1 crore.";
        result.classList.add("red");
        return;
    }else if(intrRate>40){
        result.innerHTML="Invalid interest rate.Maximum allowed: 40%.";
        result.classList.add("red");
        return;
    }else if(loanTm>120){
        result.innerHTML="Invalid loan term.Maximum allowed: 120 months.";
        result.classList.add("red");
        return;
    }else{
        let mir = intrRate/100/12;
        let emi = (loanAmt*mir*Math.pow(1+mir,loanTm))/(Math.pow(1+mir,loanTm)-1);
        emi = emi.toFixed(2);
        if(emi<=1000){
            result.innerHTML="EMI: RS. "+emi;
            result.classList.remove("red");
            result.classList.add("green");
        }else{
            result.innerHTML="EMI: RS. "+emi;
            result.classList.add("red");   
        }
    }
}
