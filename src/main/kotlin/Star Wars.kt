package http4klearning

data class Planets(val planets: List<Planet>)

data class Planet(
    val name: String,
    val rotation_period: String,
    val orbital_period: String,
    val diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    val surface_water: String,
    val population: String,
    val residents: List<String> = emptyList(),
    val films: List<String> = emptyList(),
    val url: String
)