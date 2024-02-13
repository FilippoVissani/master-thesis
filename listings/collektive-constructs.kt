interface Aggregate<ID : Any> {
    fun <Initial> exchange(
        initial: Initial,
        body: (Field<ID, Initial>) -> Field<ID, Initial>,
    ): Field<ID, Initial>
    fun <Initial, Return> exchanging(
        initial: Initial,
        body: YieldingScope<Field<ID, Initial>, Field<ID, Return>>,
    ): Field<ID, Return>
    fun <Initial> repeat(initial: Initial, transform: (Initial) -> Initial): Initial
    fun <Initial, Return> repeating(initial: Initial, transform: YieldingScope<Initial, Return>): Return
}