package mediator.internal

import kotlinx.coroutines.Deferred
import mediator.Notification
import mediator.NotificationHandler
import mediator.ServiceFactory
import mediator.getInstances

internal class MediatorNotificationHandlerWrapper<TNotification : Notification> : NotificationHandlerWrapper() {
    companion object {
        inline operator fun <TNotification: Notification> invoke(genericType: Class<TNotification>) =
            MediatorNotificationHandlerWrapper<TNotification>()
    }

    override suspend fun handleAsync(
        notification: Notification,
        serviceFactory: ServiceFactory,
        publish: PublishDelegate
    ): Deferred<Unit> {
        val handlers = serviceFactory
            .getInstances<NotificationHandler<TNotification>>()
            .map { { it.handleAsync(notification) } }
        return publish(handlers)
    }
}