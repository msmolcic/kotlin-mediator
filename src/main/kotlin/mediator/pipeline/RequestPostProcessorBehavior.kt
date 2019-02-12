package mediator.pipeline

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import mediator.PipelineBehavior

/**
 * Behavior for executing all RequestPostProcessor{TRequest,TResponse} instances after handling the request.
 * @param TRequest Request type.
 * @param TResponse Response type.
 */
class RequestPostProcessorBehavior<TRequest, TResponse>(
    private val postProcessors: Iterable<RequestPostProcessor<TRequest, TResponse>>
) : PipelineBehavior<TRequest, TResponse> {
    /**
     * {@inheritDoc}
     */
    override suspend fun handleAsync(request: TRequest, next: () -> Deferred<TResponse>): Deferred<TResponse> {
        val response = next().await()
        postProcessors.forEach { it.processAsync(request, response) }
        return CompletableDeferred(response)
    }
}