package http4klearning


fun main(){
    startApplication(Dependencies(Configuration()))
}

fun startApplication(dependencies: Dependencies){
    val application = Application(dependencies)
}



