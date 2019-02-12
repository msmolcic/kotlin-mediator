package mediator

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import mediator.internal.MediatorNotificationHandlerWrapper
import mediator.internal.MediatorRequestHandlerWrapper
import mediator.internal.NotificationHandlerWrapper
import mediator.internal.RequestHandlerWrapper
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

/**
 * {@inheritDoc}
 */
open class MediatorBehavior(
    private val serviceFactory: ServiceFactory
) : Mediator {
    companion object {
        private val requestHandlers: ConcurrentHashMap<Type, Any> = ConcurrentHashMap()
        private val notificationHandlers: ConcurrentHashMap<Type, NotificationHandlerWrapper> = ConcurrentHashMap()
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun <TResponse> sendAsync(request: Request<TResponse>): Deferred<TResponse> {
        requireNotNull(request) { "Request can't be null." }
        val requestType = request.javaClass
        val handler = requestHandlers.getOrPut(requestType) {
            MediatorRequestHandlerWrapper<Request<TResponse>, TResponse>()
        } as RequestHandlerWrapper<TResponse>
        return handler.handleAsync(request, serviceFactory)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun <TNotification : Notification> publishAsync(notification: TNotification): Deferred<Unit> {
        requireNotNull(notification) { "Notification can't be null." }
        return publishNotificationAsync(notification)
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun publishAsync(notification: Any): Deferred<Unit> {
        requireNotNull(notification) { "Notification can't be null." }
        require(notification is Notification) { "Notification does not implement Notification interface." }
        return publishNotificationAsync(notification)
    }

    /**
     * Override in a derived class to control how the tasks are awaited.
     * By default the implementation is a foreach and await of each handler.
     * @param allHandlers Iterable of tasks representing invoking each notification handler.
     * @return A deferred representing invoking all handlers.
     */
    protected open fun publishCoreAsync(allHandlers: Iterable<() -> Deferred<Unit>>): Deferred<Unit> {
        allHandlers.forEach { runBlocking { it().await() } }
        return CompletableDeferred()
    }

    private suspend fun publishNotificationAsync(notification: Notification): Deferred<Unit> {
        val notificationType = notification.javaClass
        val handler = notificationHandlers.getOrPut(notificationType) {
            MediatorNotificationHandlerWrapper(notificationType)
        }
        return handler.handleAsync(notification, serviceFactory, ::publishCoreAsync)
    }
}