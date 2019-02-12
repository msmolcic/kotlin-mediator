package com.msmolcic.mediator.internal

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import com.msmolcic.mediator.PipelineBehavior
import com.msmolcic.mediator.Request
import com.msmolcic.mediator.RequestHandler
import com.msmolcic.mediator.ServiceFactory
import com.msmolcic.mediator.getInstances

internal class MediatorRequestHandlerWrapper<TRequest : Request<TResponse>, TResponse> :
    RequestHandlerWrapper<TResponse>() {
    override suspend fun handleAsync(request: Request<TResponse>, serviceFactory: ServiceFactory): Deferred<TResponse> {
        val handler = {
            getHandler<RequestHandler<TRequest, TResponse>>(
                serviceFactory
            )
                .handleAsync(request as TRequest)
        }
        return serviceFactory
            .getInstances<PipelineBehavior<TRequest, TResponse>>()
            .reversed()
            .fold(handler) { next, pipeline ->
                { runBlocking { pipeline.handleAsync(request as TRequest, next) } }
            }()
    }
}