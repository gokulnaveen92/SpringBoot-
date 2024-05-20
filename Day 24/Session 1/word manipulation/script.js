function validate(){
    let word :string= (<HTMLInputElement>document.getElementById("sentenceInput")).value;

    if(!word){
        document.getElementById("sp1").innerHTML="Please enter a sentence!";
        return;
    }

    // let upp:string = word.toUpperCase();
    // let low :string = word.toLowerCase();
    // let rev:string = word.split("").reverse().join();

    document.getElementById("sp2").innerHTML="Original: "+word;
    document.getElementById("sp3").innerHTML="Uppercase: "+word.toUpperCase();
    document.getElementById("sp4").innerHTML="Lowercase: "+word.toLowerCase();
    document.getElementById("sp5").innerHTML="Reversed: "+word.split('').reverse().join('');

}
