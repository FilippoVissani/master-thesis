fun <ID : Any, R> aggregate(
    localId: ID,
    inbound: StateFlow<Iterable<InboundMessage<ID>>>,
    compute: Aggregate<ID>.() -> R,
): StateFlow<AggregateResult<ID, R>> {
    val states = MutableStateFlow<State>(emptyMap())
    val contextFlow = inbound.mapStates {
        AggregateContext(localId, it, states.value)
    }
    return contextFlow.mapStates { aggregateContext ->
        aggregateContext.run {
            AggregateResult(localId, compute(), messagesToSend(), newState()).also {
                states.update { this.newState() }
            }
        }
    }
}
