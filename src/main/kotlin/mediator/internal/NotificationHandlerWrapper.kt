package mediator.internal

import kotlinx.coroutines.Deferred
import mediator.Notification
import mediator.ServiceFactory

internal typealias PublishDelegate = (Iterable<() -> Deferred<Unit>>) -> Deferred<Unit>

internal abstract class NotificationHandlerWrapper {
    abstract suspend fun handleAsync(
        notification: Notification,
        serviceFactory: ServiceFactory,
        publish: PublishDelegate
    ) : Deferred<Unit>
}