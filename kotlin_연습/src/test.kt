import java.util.*

fun main(){
//    check(20)
    ignoreNull("String")

}

fun forAndwhile(){
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

    var b = 0
    while(b < 10){
        println(b)
        b++
    }
}

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

fun isNull(){
    var city = "jeonju"
    var name : String? = null // null을 사용하려면 '?'를 붙여줘야한다.

    var cityUpperCase = city.uppercase()
    var nameUpperCase = name?.uppercase()

    var fullName = city+(name?:"no name") // name이 null이면 "no name" 아니면 name

}

fun ignoreNull(str : String?){
    var mNotNull : String = str!! // 절대 null이지 않을 경우

    var address : String? = null

    // address가 null이 아닐 경우만 실행
    address?.let {
        println("${address}")
    }
}

