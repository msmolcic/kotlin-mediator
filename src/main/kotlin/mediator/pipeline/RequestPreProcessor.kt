package mediator.pipeline

import kotlinx.coroutines.Deferred

/**
 * Defined a request pre-processor for a handler.
 * @param TRequest Request type.
 */
interface RequestPreProcessor<in TRequest> {
    /**
     * Process method executes before calling the handle method on your handler.
     * @param request Incoming request.
     * @return An awaitable deferred.
     */
    fun processAsync(request: TRequest): Deferred<Unit>
}