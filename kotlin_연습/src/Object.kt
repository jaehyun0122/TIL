object CarFactory{
    val cars = arrayListOf<Car>()
    fun mkCar(power : Int) : Car{
        val car = Car(power)
        cars.add(car)
        return car
    }
}

data class Car(val power: Int)

fun main(){

    val car1 = CarFactory.mkCar(10)
    val car2 = CarFactory.mkCar(20)

    println(CarFactory.cars.size)
    println(car1)
    println(car2)
}
