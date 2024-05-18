function Movie(title,director,year,genre)
{
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.isPlaying = false;
}

Movie.prototype.play = function(){
        this.isPlaying = true;
        return this.isPlaying;    
}

Movie.prototype.stop = function(){
    this.isPlaying = false;
        return this.isPlaying;
}

// let movies = [];

// let movie1 = new Movie("Leo","loki",2023,"action");
// let movie2 = new Movie("Vikram","loki",2022,"action");
// movies.push(movie1);
// movies.push(movie2);
//Object.defineProperty(movie1,"isPlaying",{value:false,writable:false});
//Object.defineProperty(movie2,"isPlaying",{value:false,writable:false});



// console.log(Object.getOwnPropertyNames(movie1));
// console.log(movie1.year);
// console.log(movie1.title);
// console.log(movie1.director);
// console.log(movie1.play());
// console.log(movie1.play());
// console.log(movie1.stop());
// console.log(movie1.stop());

module.exports=Movie;


