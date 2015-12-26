package logic.ajax

import groovy.util.logging.Slf4j
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.testelement.property.CollectionProperty
import org.apache.jmeter.threads.JMeterContext

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by User on 2015-12-22.
 */
@Slf4j
class AJAXExecutor {

    private final Integer TIMEOUT = 5;

    Queue<AJAXResult> execute(JMeterContext ctx, Arguments arguments) {

        AJAXCaller caller = new AJAXCaller();
        SessionCookieStorage sessionCookieStorage = new SessionCookieStorage(ctx);
        Queue<AJAXResult> results = new ConcurrentLinkedQueue<>();
        GString url = """$ctx.previousResult.URL.protocol://$ctx.previousResult.URL.host:$ctx.previousResult.URL.port"""
        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < arguments.argumentCount; i++) {

            pool.submit(caller.startAjax(sessionCookieStorage, arguments.getArgument(i).name, new AJAXUrl(url, arguments.getArgument(i).value), arguments.getArgument(i).description, results));
        }

        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.MINUTES);
            return results;
        } catch (InterruptedException e) {
            return e.printStackTrace();
        }
    }
}
