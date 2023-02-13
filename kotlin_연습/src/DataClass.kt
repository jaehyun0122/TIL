fun main(){
    val ticketA = Ticket("Mark", "HongKong", "2023-02-13 ")
    val ticketB = TicketNormal("Mark", "HongKong", "2023-02-13 ")

    println(ticketA)
    println(ticketB)
}

data class Ticket(val name : String, val destination : String, val date : String)
class TicketNormal(val name : String, val destination : String, val date : String)
// toString, hashCOde, equals, copy => 컴파일시 만들어진다.
