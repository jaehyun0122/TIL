import java.io.*;
import java.util.HashSet

fun main(args: Array<String>){
    var br = BufferedReader(InputStreamReader(System.`in`))
    var set = HashSet<Int>()
    var answer = 0;
    for(i in 0..9){
        var num = Integer.parseInt(br.readLine())
        var res = num%42

        if(!set.contains(res)){
            set.add(res)
            answer++
        }

    }
    print(answer)
}
