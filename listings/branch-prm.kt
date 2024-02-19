override fun <T> rBranch(
    condition: () -> StateFlow<Boolean>,
    th: () -> StateFlow<T>,
    el: () -> StateFlow<T>,
): StateFlow<T> {
    val conditionResult = condition()
    return flattenConcat(
        mapStates(conditionResult) { newCondition ->
            if (newCondition) {
                deleteOppositeBranch(newCondition)
                th()
            } else {
                deleteOppositeBranch(newCondition)
                el()
            }
        },
    )
}