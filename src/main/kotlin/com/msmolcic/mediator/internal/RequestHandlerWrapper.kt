package com.msmolcic.mediator.internal

import com.msmolcic.mediator.Request
import com.msmolcic.mediator.ServiceFactory
import kotlinx.coroutines.Deferred

internal abstract class RequestHandlerWrapper<TResponse> : RequestHandlerBase() {
    abstract suspend fun handleAsync(request: Request<TResponse>, serviceFactory: ServiceFactory): Deferred<TResponse>
}