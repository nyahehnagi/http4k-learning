package http4klearning

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

import org.http4k.server.Jetty
import org.http4k.server.asServer

import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
import java.net.URL


class Application(val dependencies: Dependencies) {

    val renderHTML = RenderHTML()
    val pingPongHandler: HttpHandler = { _ -> Response(OK).body("pong") }

    val greetHandler: HttpHandler = { request: Request ->
        val name: String? = request.query("name")
        Response(OK).body("hello ${name ?: "unknown!"}")
    }

    val planetHandler: HttpHandler = { _ ->
        Response(OK).body(renderHTML.getPlanet)
    }

    val planetReciever: HttpHandler = { request: Request ->
        val planetNumber: String? = request.form(("planet"))
        val response = getPlanet(planetNumber)
        Response(OK).body(response)
    }

    fun getPlanet(planet: String?): String {
        val endpoint = dependencies.configuration().starWarsConfig.planetsEndPoint
        val starWarsPlanetEndPoint  = "https://swapi.co/api/planets/$planet/?format=json"
        val mapper = jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        var planetValue  = mapper.readValue<Planet>(URL(starWarsPlanetEndPoint))
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(planetValue)
    }

    val routing: RoutingHttpHandler = routes(
        "/ping" bind GET to pingPongHandler,
        "/greet" bind GET to greetHandler,
        "/planet" bind GET to planetHandler,
        "/planet" bind POST to planetReciever
    )

    val routingServer = routing.asServer(Jetty(9000)).start()

}

class RenderHTML() {
    val getPlanet =
        "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>" +
                "<form method=\"post\">\n" +
                "  planet: <input type=\"text\" name=\"planet\"><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form>" +
                "</body>\n" +
                "</html>"
}

/* TODO Come back to this to understand how this works
open class Dependancies(val configuration: Configuration){

}
*/
