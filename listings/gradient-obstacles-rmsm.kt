fun Aggregate<Int>.gradient(source: Boolean): Double =
    share(Double.POSITIVE_INFINITY) { field ->
        when {
            source -> 0.0
            else -> (field + 1.0).min(Double.POSITIVE_INFINITY)
        }
    }

fun Aggregate<Int>.gradientWithObstacles(nodeType: NodeType): Double =
    if (nodeType == NodeType.OBSTACLE) {
        -1.0
    } else {
        gradient(nodeType == NodeType.SOURCE)
    }
    