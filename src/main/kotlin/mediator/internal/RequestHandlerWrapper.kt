package mediator.internal

import kotlinx.coroutines.Deferred
import mediator.Request
import mediator.ServiceFactory

internal abstract class RequestHandlerWrapper<TResponse> : RequestHandlerBase() {
    abstract suspend fun handleAsync(request: Request<TResponse>, serviceFactory: ServiceFactory): Deferred<TResponse>
}