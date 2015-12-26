package logic.ajax

import org.apache.jmeter.threads.JMeterContext

/**
 * Created by User on 2015-12-22.
 */
class SessionCookieStorage {

    private final String cookie;
    private final String domain;
    private final String path;

    SessionCookieStorage(JMeterContext ctx) {
        this.cookie = ctx.getVariables().get('COOKIE_JSESSIONID');
        this.domain = ctx.previousResult.URL.host;
        String[] split = ctx.previousResult.URL.path.split("/")
        this.path = '/' + split[0] + '/';
    }

    String getCookie() {
        cookie;
    }

    String getPath() {
        return path
    }

    String getDomain() {
        return domain
    }
}
