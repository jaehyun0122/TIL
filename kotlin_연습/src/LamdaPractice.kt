fun main(){
    println(square(12))
    val a = "Mark"
    println(a.doSomething())
    println(calcGrade(100))

    val lamda : (Double) -> Boolean = {
        number : Double -> number == 3.14
    }
    println(invokeLamda(lamda))
    println(invokeLamda({it > 4}))
    // 매개변수가 하나일 떄는 중괄호 생략가능
    println(invokeLamda { it > 4 })
}


val square : (Int) -> (Int) = {
    number -> number*number
}

val doSomething : String.() -> String = {
    this+" doSomething"
}

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

fun invokeLamda(lamda : (Double) -> Boolean): Boolean {
    return lamda(3.14)
}


