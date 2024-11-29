import java.util.Stack

fun main() {
    print("Nhập dãy infix:")
    var input= readLine()?:""
    var postfix=infixPostfix(input)
    println("Biểu thức Postfix là :$postfix")
    println("Kết quả sau biểu thức Postfix là :${evaluePostfix(postfix)}")
}

fun infixPostfix(strings:String):String{
     val stack=Stack<Char>()
    val postfix=StringBuilder()

    for (j in strings.indices){
        var i=strings[j]
        when {
            i.isLetterOrDigit() -> postfix.append(i)
            i=='(' ->stack.push(i)
            i==')' ->{
                while (stack.isNotEmpty()&&stack.peek() != '('){
                           postfix.append(stack.pop())
                    }
                stack.pop()
            }
            else -> {
                while (stack.isNotEmpty()&& getPrecedence(i) <= getPrecedence(stack.peek())){
                    postfix.append(stack.pop())
                }
                stack.push(i)
            }
        }
    }
    while (stack.isNotEmpty()){
        postfix.append(stack.pop())
    }
    return postfix.toString()
 }
fun getPrecedence(i: Char): Int {
    return when (i) {
        '+', '-' -> 1
        '*', '/' -> 2
        '^' -> 3
        else -> -1
    }
}
fun evaluePostfix(postfix:String):Int{
    val stack=Stack<Int>()
    for (i in postfix){
        when{
            i.isDigit() -> stack.push(i.toString().toInt())
            else -> {
                val a=stack.pop()
                val b=stack.pop()
                val result=when (i){
                    '+' -> b+a
                    '-' -> b-a
                    '*' -> b*a
                    '/' -> {
                        if (a==0) throw ArithmeticException("Không thể chia cho 0")
                        b/a
                    }
                    '^' -> Math.pow(b.toDouble(),a.toDouble()).toInt()
                    else -> throw IllegalArgumentException("Toán tử không hợp lệ ")
                }
                stack.push(result)
            }
        }
    }
    return stack.pop()

}