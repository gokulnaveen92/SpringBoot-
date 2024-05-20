
function wordCount(){
    let word = (<HTMLInputElement>document.getElementById("wordInput")).value;
    
    if(!word){
        document.getElementById("sp1").innerHTML="Please enter a sentence!";
        return;
    }

    let count:number = 0;

    for(let i=0;i<word.length;i++){
        if(word[i]!=" "){
            count++;
        }
    }

    let first = word[0];
    let last = word[word.length-1];

    document.getElementById("sp1").innerHTML="Original: "+word;
    document.getElementById("sp2").innerHTML="Word Length(Excluding Spaces): "+count;
    document.getElementById("sp3").innerHTML="First Character: "+first;
    document.getElementById("sp4").innerHTML="Last Character: "+last;


}
