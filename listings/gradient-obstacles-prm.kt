fun Aggregate<Int>.gradient(sourceFlow: StateFlow<Boolean>): StateFlow<Double> =
    rShare(Double.POSITIVE_INFINITY) { fieldFlow ->
        rMux(
            { sourceFlow },
            { MutableStateFlow(0.0) },
            { fieldFlow.mapStates { it.plus(1.0).min(Double.POSITIVE_INFINITY) } },
        )
    }

fun Aggregate<Int>.gradientWithObstacles(nodeTypeFlow: StateFlow<NodeType>): StateFlow<Double> =
    rBranch(
        { nodeTypeFlow.mapStates { it == NodeType.OBSTACLE } },
        { MutableStateFlow(-1.0) },
        { gradient(nodeTypeFlow.mapStates { it == NodeType.SOURCE }) },
    )
