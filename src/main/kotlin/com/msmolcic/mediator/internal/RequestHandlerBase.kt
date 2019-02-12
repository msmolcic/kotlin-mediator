package com.msmolcic.mediator.internal

import com.msmolcic.mediator.ServiceFactory
import com.msmolcic.mediator.getInstance
import java.lang.Exception
import java.lang.IllegalStateException

internal abstract class RequestHandlerBase {
    companion object {
        inline fun <reified THandler> getHandler(factory: ServiceFactory): THandler {
            var handler: THandler
            try {
                handler = factory.getInstance()
            } catch (e: Exception) {
                throw IllegalStateException(
                    """Error constructing handler for request of type ${THandler::class.java}.
                    | Register your handlers with the container. See the samples in GitHub for examples.""".trimMargin()
                )
            }
            if (handler == null) {
                throw IllegalStateException(
                    """Handler was not found for request of type ${THandler::class.java}.
                    | Register your handlers with the container. See the samples in GitHub for examples.""".trimMargin()
                )
            }
            return handler
        }
    }
}