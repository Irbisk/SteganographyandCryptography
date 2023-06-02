package cryptography

var isOn = true


fun main() {
    start()
}

fun start() {
    while (isOn) {
        println("Task (hide, show, exit):")
        CommandHandler.handle(Command(readln()))
    }
}
