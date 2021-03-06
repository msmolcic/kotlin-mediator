package com.msmolcic.mediator.pipeline

import com.msmolcic.mediator.PipelineBehavior
import kotlinx.coroutines.Deferred

/**
 * Behavior for executing all RequestPreProcessor{TRequest,TResponse} instances before handling the request.
 * @param TRequest Request type.
 * @param TResponse Response type.
 */
class RequestPreProcessorBehavior<TRequest, TResponse>(
    private val preProcessors: Iterable<RequestPreProcessor<TRequest>>
) : PipelineBehavior<TRequest, TResponse> {
    /**
     * {@inheritDoc}
     */
    override suspend fun handleAsync(request: TRequest, next: () -> Deferred<TResponse>): Deferred<TResponse> {
        preProcessors.forEach { it.processAsync(request) }
        return next()
    }
}