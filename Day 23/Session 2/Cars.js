class Car{
    constructor(name,model,year,specs){
        this.name = name;
        this.model = model;
        this.year = year;
        this.specs = specs;
    }

    getCarInfo = function(){
        return "Name: "+this.name+", Model: "+this.model+", Year: "+this.year+", Specs: "+this.specs;
    }
}

let cars = [
    new Car("car 1","mod 1",2007,"specs 1"),
    new Car("car 2","mod 2",2018,"specs 2")
];

cars.forEach(car=>console.log(car.getCarInfo()));

module.exports=Car;
