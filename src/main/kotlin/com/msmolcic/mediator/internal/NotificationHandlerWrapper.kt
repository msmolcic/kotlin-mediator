package com.msmolcic.mediator.internal

import com.msmolcic.mediator.Notification
import com.msmolcic.mediator.ServiceFactory
import kotlinx.coroutines.Deferred

internal typealias PublishDelegate = (Iterable<() -> Deferred<Unit>>) -> Deferred<Unit>

internal abstract class NotificationHandlerWrapper {
    abstract suspend fun handleAsync(
        notification: Notification,
        serviceFactory: ServiceFactory,
        publish: PublishDelegate
    ) : Deferred<Unit>
}