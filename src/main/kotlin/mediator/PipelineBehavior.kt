package mediator

import kotlinx.coroutines.Deferred

/**
 * Represents an async continuation for the next task to execute in the pipeline.
 * @param TResponse Response type.
 * @return Awaitable deferred returning a {TResponse}.
 */
typealias RequestHandlerDelegate<TResponse> = () -> Deferred<TResponse>

/**
 * Pipeline behavior to surround the inner handler.
 * Implementations add additional behavior and await the next function.
 * @param TRequest Request type.
 * @param TResponse Response type.
 */
interface PipelineBehavior<in TRequest, TResponse> {
    /**
     * Pipeline handler. Perform any additional behavior and await the next function as necessary.
     * @param request Incoming request.
     * @param next Awaitable function representing the next action in the pipeline.
     * Eventually this function represents the handler.
     * @return Awaitable deferred containing the {TResponse}.
     */
    suspend fun handleAsync(request: TRequest, next: RequestHandlerDelegate<TResponse>) : Deferred<TResponse>
}