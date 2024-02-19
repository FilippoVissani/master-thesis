fun <ID : Any, R> aggregate(
    localId: ID,
    inbound: StateFlow<Iterable<InboundMessage<ID>>>,
    compute: Aggregate<ID>.() -> R,
): StateFlow<AggregateResult<ID, R>> {
    val states = MutableStateFlow<State>(emptyMap())
    val contextFlow = mapStates(inbound) {
        AggregateContext(localId, it, states.value)
    }
    val result: StateFlow<AggregateResult<ID, R>> = mapStates(contextFlow) { aggregateContext ->
        aggregateContext.run {
            val aggregateResult = AggregateResult(localId, compute(), messagesToSend(), newState())
            states.update { aggregateResult.newState }
            aggregateResult
        }
    }
    return result
}
