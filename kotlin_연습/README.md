## 1. 함수
- fun 함수명(변수명 : 타입) : 반환형(없을 경우 Unit or 생략가능)
````
fun hello(a : Int, b : Int) : Int{
    return a+b
}

fun test() : Unit{
    print("test")
}
````

## 2. val vs var
- val : value, 변하지 않는 값
- var : variable, 변할 수 있는 값
변수 선언과 초기화 동시에 할 시 타입 선언하지 않아도 코틀린이 할당해준다.
선언만 할 경우 변수 타입 선언
````
val a : Int
val b : Int = 100
val c = 100
````

## 3. String Template
````
val name : "jaehyun"
println("my name is ${name}) // my name is jaehyun
println("true or false ${1==2}) // false
````

$를 출력하고 싶으면 '\'를 붙여주면 된다.

## 4. 조건식
1-1. if
````
fun getMin(a : Int, b : Int) : Int{
    if(a>b){ return a }
    else return b
}
 ````
1-2. java에서의 3항 연산자가 없음.
````
fun getMin(a : Int, b : Int) = if(a>b) a else b
````

2. when
````
fun check(age : Int){
    when(age){
        12 -> println("12")
        20 -> println("20")
        30,31 -> println("30 or 31")
    }

    when(age){
        in 10..19 -> println("10~19")
        in 20..29 -> println("20~29")
    }

    // else 반드시 있어야 된다.
    var a = when(age){
        12 -> 1
        20 -> 20
        else -> 0
    }

    println("age is ${a}")
}
````

## 5. Expression vs Statement
코틀린의 모든 함수는 Expression. 
반환 값이 없더라도 Unit을 반환.
java에서의 void는 Statement.

## 6. Array, List
1. List 
   1. List : List의 값을 변경할 수 없다. 특정 인덱스의 값은 가져올수 있지만 인덱스의 값을 변경 불가.
   2. Mutable List(arrayList) : 특정 인덱스의 값을 변경할 수 있다. 
   ````
   val arr = arrayListOf<Int>()
    arr.add(10)
    arr.add(20)
    arr[1] = 30
   ````

## 7. for, while
1. for
````
    var a = arrayListOf<Int>(1,2)
    for (i in 0..a.size){
        println(i)
    }
    for (number in a){
        println(number)
    }
    
    for((index, number) in a.withIndex()){
        println("${index}+1 번째 번호 : ${number}")
    }
    
    for (i in 0..12 step 2){
        println(i)
    }

    for (i in 10 downTo 6){
        println(i)
    }
````
2. while
````
    var b = 0
    while(b < 10){
        println(b)
        b++
    }
````

## 8. Nullable, NonNull
java에서는 컴파일 시점에 NPE을 확인할 수 없다.(런타임 시점에서만 확인가능)
````
var city = "jeonju"
    // null을 사용하려면 '?'를 붙여줘야한다.
    var name : String? = null 

    var cityUpperCase = city.uppercase()
    
    // 변수 앞에 '?'를 붙이면 null이 아닐 경우만 
    var nameUpperCase = name?.uppercase()

    // name이 null이면 "no name" 아니면 name
    var fullName = city+(name?:"no name")
````

````
fun ignoreNull(str : String?){
    // 절대 null이지 않을 경우
    var mNotNull : String = str!!

    var address : String? = null
    
    // address가 null이 아닐 경우만 실행
    address?.let {
        println("${address}")
    }
````

## 9. Class
1-1. 생성자
````
class Student constructor(name : String){

    val name = name

    fun study(){
        println("Study hard!!")
    }
}

class Student (name : String){
    
    val name = name

    fun study(){
        println("Study hard!!")
    }
}

// 필드대신 선언 가능
class Student (val name : String){

    fun study(){
        println("Study hard!!")
    }
}

// 기본값 지정
class Student (val name : String = "jaehyun"){

    fun study(){
        println("Study hard!!")
    }
}
````

1-2. 생성자 여러개일 경우
java에서의 생성자 오버라이딩
kotlin에서 부생성자를 생성
````
class Student (val name : String){

    constructor(name:String, age:Int) : this(name){
        // do
    }
    
    fun study(){
        println("Study hard!!")
    }
}
````
2. 상속
상위 class에 open을 붙여줘야된다.
코틀린에서 class는 final이기 때문에
````
open class Student (val name : String = "Anonymous"){

    constructor(name:String, age:Int) : this(name){
        // do
    }

    open fun study(){
        println("Study hard!!")
    }
}

class Korean : Student(){
    override fun study(){
        println("study korean")
    }

}
````

## 10. Lamda
val(var) Name : Type = {argumentList -> codeBody}
````
            inputType outputType
val square : (Int) -> (Int) = {
    number -> number*number
}
````
- 확장함수
````
fun main(){
    val a = "Mark"
    println(a.doSomething()) // Mark doSomething
}

val doSomething : String.() -> String = {
   // this는 확장함수를 호출하는 객체
    this+" doSomething"
}
````
- 람다 리턴
````
val calcGrade : (Int) -> String = {
    // input매개변수가 하나이면 it으로 사용할 수 있다.
    when(it){
        in 0..30 -> "c"
        in 31..60 -> "B"
        in 61..90 -> "A"
        in 91..100 -> "S"
        // String이 반환타입이기 때문에
        // else 없으면 컴파일 에러
        else -> "Error"
    }
}
````
- 람다 활용
````
fun main(){
   val lamda : (Double) -> Boolean = {
           number : Double -> number == 3.14
       }
       println(invokeLamda(lamda))
       println(invokeLamda({it > 4}))
       // 매개변수가 하나일 떄는 중괄호 생략가능
       println(invokeLamda { it > 4 })
 }
 
 fun invokeLamda(lamda : (Double) -> Boolean): Boolean {
    return lamda(3.14)
}
````

## 11. DataClass
toString, hashCOde, equals, copy => 컴파일시 만들어진다.

````
data class Ticket(val name : String, val destination : String, val date : String)
class TicketNormal(val name : String, val destination : String, val date : String)

fun main(){
    val ticketA = Ticket("Mark", "HongKong", "2023-02-13 ")
    val ticketB = TicketNormal("Mark", "HongKong", "2023-02-13 ")
   
   // toString이 생성되므로 ticketA는 데이터 
   // ticketB는 주소값
    println(ticketA) // Ticket(name=Mark, destination=HongKong, date=2023-02-13 )
    println(ticketB) // TicketNomal@17c68925
}
````

## 12. Companion Object
자바의 static 기능.

## 13. object
Singleton Pattern. 프로그램 실행시 한번만 생성된다.
````
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
````
