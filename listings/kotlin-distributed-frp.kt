fun gradient(): AggregateExpression<Double> {
    return loop(Double.POSITIVE_INFINITY) { distance ->
        mux(
            sense(Sensors.IS_SOURCE.sensorID),
            constant(0.0),
            neighbor(distance)
                .withoutSelf()
                .min()
                .map { it + 1 }
        )
    }
}
