package http4klearning

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import com.typesafe.config.ConfigFactory
import org.http4k.core.Method
import org.http4k.core.Status.Companion.OK

class Application(){

    val pingPongHandler: HttpHandler = { _ -> Response(OK).body("pong!") }

    val greetHandler: HttpHandler = { req: Request ->
        val name: String? = req.query("name")
        Response(OK).body("hello ${name ?: "unknown!"}")
    }

    val routing: RoutingHttpHandler = routes(
        "/ping" bind Method.GET to pingPongHandler,
        "/greet" bind Method.GET to greetHandler
    )
}

open class Dependancies(val configuration: Configuration){

}