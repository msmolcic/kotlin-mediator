package com.msmolcic.mediator.internal

import kotlinx.coroutines.Deferred
import com.msmolcic.mediator.Notification
import com.msmolcic.mediator.NotificationHandler
import com.msmolcic.mediator.ServiceFactory
import com.msmolcic.mediator.getInstances

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