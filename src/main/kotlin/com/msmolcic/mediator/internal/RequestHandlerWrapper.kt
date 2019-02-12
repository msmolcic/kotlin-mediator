package com.msmolcic.mediator.internal

import kotlinx.coroutines.Deferred
import com.msmolcic.mediator.Request
import com.msmolcic.mediator.ServiceFactory

internal abstract class RequestHandlerWrapper<TResponse> : RequestHandlerBase() {
    abstract suspend fun handleAsync(request: Request<TResponse>, serviceFactory: ServiceFactory): Deferred<TResponse>
}