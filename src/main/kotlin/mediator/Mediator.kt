package mediator

import kotlinx.coroutines.Deferred

/**
 * Defines a mediator to encapsulate request/response and publishing interaction patterns.
 */
interface Mediator {
    /**
     * Asynchronously send a request to a single handler.
     * @param TResponse Response type.
     * @param request Request object.
     * @return A deferred that represents the send operation. The deferred result contains the handler response.
     */
    suspend fun <TResponse> sendAsync(request: Request<TResponse>): Deferred<TResponse>

    /**
     * Asynchronously send a notification to multiple handlers.
     * @param notification Notification object.
     * @return A deferred that represents the publish operation.
     */
    suspend fun publishAsync(notification: Any): Deferred<Unit>

    /**
     * Asynchronously send a notification to multiple handlers.
     * @param notification Notification object.
     * @return A deferred that represents the publish operation.
     */
    suspend fun <TNotification : Notification> publishAsync(notification: TNotification): Deferred<Unit>
}