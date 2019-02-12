package mediator.internal

import mediator.ServiceFactory
import mediator.getInstance
import java.lang.Exception

internal abstract class RequestHandlerBase {
    companion object {
        inline fun <reified THandler> getHandler(factory: ServiceFactory): THandler {
            var handler: THandler

            try {
                handler = factory.getInstance()
            } catch (e: Exception) {
                TODO("Throw invalid operation exception.")
            }

            if (handler == null) {
                TODO("Throw invalid operation exception.")
            }

            return handler
        }
    }
}