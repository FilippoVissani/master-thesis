class RAggregateContext<ID : Any>(
    override val localId: ID,
    private val rInboundMessages: MutableStateFlow<List<InboundMessage<ID>>> = MutableStateFlow(emptyList()),
) : Aggregate<ID> {

    private val stack = Stack()
    private val rState: MutableStateFlow<State> = MutableStateFlow(emptyMap())
    private val rOutboundMessages: MutableStateFlow<OutboundMessage<ID>> =
        MutableStateFlow(OutboundMessage(localId, emptyMap()))

    fun rState(): StateFlow<State> = rState.asStateFlow()

    fun rOutboundMessages() = rOutboundMessages.asStateFlow()

    @OptIn(DelicateCoroutinesApi::class)
    override fun <T> rExchange(
        initial: T,
        body: (StateFlow<Field<ID, T>>) -> StateFlow<Field<ID, T>>,
    ): StateFlow<Field<ID, T>> {...}

    override fun <T> rBranch(
        condition: () -> StateFlow<Boolean>,
        th: () -> StateFlow<T>,
        el: () -> StateFlow<T>,
    ): StateFlow<T> {...}

    private fun deleteOppositeBranch(condition: Boolean) {...}

    private fun <T> newField(localValue: T, others: Map<ID, T>): Field<ID, T> = Field(localId, localValue, others)

    @Suppress("UNCHECKED_CAST")
    private fun <T> rMessagesAt(path: Path): StateFlow<Map<ID, T>> = mapStates(rInboundMessages) { messages ->
        messages
            .filter { it.messages.containsKey(path) }
            .associate { it.senderId to it.messages[path] as T }
    }

    private fun <T> rStateAt(path: Path, default: T): StateFlow<T> = mapStates(rState) { state ->
        state.getTyped(path, default)
    }

    override fun <R> alignedOn(pivot: Any?, body: () -> R): R {
        stack.alignRaw(pivot)
        return body().also { stack.dealign() }
    }
}
