"rExchange should return the initial value" {
    runBlocking {
        val aggregateResult0 = RCollektive.aggregate(id0) {
            rExchange(initV1, increaseOrDouble)
        }
        val job = launch(Dispatchers.Default) {
            runSimulation(mapOf(aggregateResult0 to MutableStateFlow(emptyList())))
        }
        delay(100)
        job.cancelAndJoin()
        aggregateResult0.toSend.value.senderId shouldBe id0
        aggregateResult0.toSend.value.messages.values shouldContain SingleOutboundMessage(expected2, emptyMap())
    }
}