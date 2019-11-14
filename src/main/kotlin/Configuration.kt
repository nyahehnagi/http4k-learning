package http4klearning

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.github.config4k.*

data class StarWarsConfig(
    val planetsEndPoint : String
)

data class GlobalConfig(
    val starWarsConfig: StarWarsConfig
)

class Configuration {
    val config: Config = ConfigFactory.load()

    operator fun invoke(): GlobalConfig =  config.extract()

}


open class Dependencies(val configuration: Configuration) {

}