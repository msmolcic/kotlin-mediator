package com.msmolcic.mediator

import kotlinx.coroutines.Deferred

/**
 * Defines a handler for a request.
 * @param TRequest The type of request being handled.
 * @param TResponse The type of response from the handler.
 */
interface RequestHandler<in TRequest : Request<TResponse>, TResponse> {
    /**
     * Handles a request.
     * @param request The request.
     * @return Response from the request.
     */
    fun handleAsync(request: TRequest): Deferred<TResponse>
}
