fun <ID : Any, R> aggregate(
    localId: ID,
    rInboundMessages: MutableStateFlow<List<InboundMessage<ID>>> = MutableStateFlow(emptyList()),
    compute: Aggregate<ID>.() -> StateFlow<R>,
): RAggregateResult<ID, R> = RAggregateContext(localId, rInboundMessages).run {
    RAggregateResult(localId, compute(), rOutboundMessages(), rState())
}
