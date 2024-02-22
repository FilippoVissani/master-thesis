override fun <T> rBranch(
    condition: () -> StateFlow<Boolean>,
    th: () -> StateFlow<T>,
    el: () -> StateFlow<T>,
): StateFlow<T> {
    val currentPath = stack.currentPath()
    return condition().mapStates { newCondition ->
        currentPath.tokens().forEach { stack.alignRaw(it) }
        val selectedBranch = if (newCondition) th else el
        deleteOppositeBranch(newCondition)
        alignedOn(newCondition) {
            selectedBranch()
        }.also {
            currentPath.tokens().forEach { _ -> stack.dealign() }
        }
    }.flattenConcat()
}