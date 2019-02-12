package mediator

import java.lang.reflect.Type

/**
 * Factory method used to resolve all services. For multiple instances, it will resolve against Iterable{T}.
 * @param Type Type of service to resolve.
 * @return An instance of type {Type}.
 */
typealias ServiceFactory = (Type) -> Any

inline fun <reified T> (ServiceFactory).getInstance(): T = this(T::class.java) as T

inline fun <reified T> (ServiceFactory).getInstances(): Iterable<T> =
    this(HashSet<T>()::class.java) as Iterable<T>
