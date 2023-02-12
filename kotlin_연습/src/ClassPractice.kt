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


fun main(){
    val student = Student("jaehyun")
    student.study()

    val korean = Korean()
    korean.study()

}

