@OptIn(DelicateCoroutinesApi::class)
override fun <T> rExchange(
    initial: T,
    body: (StateFlow<Field<ID, T>>) -> StateFlow<Field<ID, T>>,
): StateFlow<Field<ID, T>> {
    val messages = rMessagesAt<T>(stack.currentPath())
    val previous = rStateAt(stack.currentPath(), initial)
    val subject = messages.mapStates { m -> newField(previous.value, m) }
    val alignmentPath = stack.currentPath()
    return body(subject).also { flow ->
        flow.onEach { field ->
            val message = SingleOutboundMessage(field.localValue, field.excludeSelf())
            rOutboundMessages.update { it.copy(messages = it.messages + (alignmentPath to message)) }
            rState.update { it + (alignmentPath to field.localValue) }
        }.launchIn(GlobalScope)
    }
}
