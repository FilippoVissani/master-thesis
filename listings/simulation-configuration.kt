enum class NodeType {
    SOURCE,
    OBSTACLE,
    DEFAULT,
}

val environment = Environment.manhattanGrid(5, 5)

val reactiveSensors = (0..<environment.devicesNumber).map {
    MutableStateFlow(
        when (it) {
            0 -> NodeType.SOURCE
            2, 7, 12 -> NodeType.OBSTACLE
            else -> NodeType.DEFAULT
        },
    )
}
