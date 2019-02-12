package mediator

import kotlinx.coroutines.Deferred

/**
 * Defines a handler for a notification.
 * @param TNotification The type of notification being handled.
 */
interface NotificationHandler<in TNotification : Notification> {
    /**
     * Handles a notification.
     * @param notification The notification.
     */
    fun handleAsync(notification: Notification): Deferred<Unit>
}