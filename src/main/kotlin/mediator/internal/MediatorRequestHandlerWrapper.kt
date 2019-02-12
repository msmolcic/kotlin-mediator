package mediator.internal

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import mediator.PipelineBehavior
import mediator.Request
import mediator.RequestHandler
import mediator.ServiceFactory
import mediator.getInstances

internal class MediatorRequestHandlerWrapper<TRequest : Request<TResponse>, TResponse> :
    RequestHandlerWrapper<TResponse>() {
    override suspend fun handleAsync(request: Request<TResponse>, serviceFactory: ServiceFactory): Deferred<TResponse> {
        val handler = {
            getHandler<RequestHandler<TRequest, TResponse>>(serviceFactory)
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