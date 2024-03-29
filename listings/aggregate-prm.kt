fun <ID : Any, R> aggregate(
    localId: ID,
    rInboundMessages: StateFlow<Iterable<InboundMessage<ID>>>,
    compute: Aggregate<ID>.() -> StateFlow<R>,
    ): RAggregateResult<ID, R> = RAggregateContext(localId, rInboundMessages).run {
        RAggregateResult(localId, compute(), rOutboundMessages(), rState())
    }

data class RAggregateResult<ID : Any, R>(
    val localId: ID,
    val result: StateFlow<R>,
    val toSend: StateFlow<OutboundMessage<ID>>,
    val state: StateFlow<State>,
)
