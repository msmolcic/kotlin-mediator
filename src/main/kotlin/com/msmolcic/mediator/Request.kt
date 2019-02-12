package com.msmolcic.mediator

/**
 * Marker interface to represent a request with a response.
 * @param TResponse Response type.
 */
interface Request<out TResponse> : BaseRequest