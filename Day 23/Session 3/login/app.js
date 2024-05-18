function validateLogin(){
    let usernmae = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let result = document.getElementById("result");

    let upper = /[A-Z]+/.test(password);
    let lower = /[a-z]+/.test(password);
    let dig = /[0-9]+/.test(password);
    let spl = /[!@#$%&*_]+/.test(password);

    if(usernmae==="" || password===""){
        result.innerHTML="Please enter both username and password.";
        result.classList.add("red");
        return;
    }else if(usernmae.length<=6){
        result.innerHTML="Username must be more than 6 characters.";
        result.classList.add("red");
        return;
    }else if(upper && lower && dig && spl){
        result.innerHTML="Login successful.";
        result.classList.remove("red");
        result.classList.add("green");
    }else{
        result.innerHTML="Invalid password.";
        result.classList.add("red");
    }

}
