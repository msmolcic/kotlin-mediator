package mediator.pipeline

import kotlinx.coroutines.Deferred

/**
 * Defines a request post-processor for a request.
 * @param TRequest Request type.
 * @param TResponse Response type.
 */
interface RequestPostProcessor<in TRequest, in TResponse> {
    /**
     * Process method executes after the handle method on your handler.
     * @param request Request instance.
     * @param response Response instance.
     * @return An awaitable deferred.
     */
    fun processAsync(request: TRequest, response: TResponse): Deferred<Unit>
}