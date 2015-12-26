package logic.ajax

import groovy.util.logging.Slf4j
import org.apache.commons.io.IOUtils
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.CookieStore
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.cookie.BasicClientCookie
import org.apache.http.util.EntityUtils

/**
 * Created by User on 2015-12-21.
 */
@Slf4j
class AJAXCaller {

    Runnable startAjax(final SessionCookieStorage sessionCookieStorage,
                       final String method, final AJAXUrl currentURL, final String param,
                       final Queue<AJAXResult> results) {

        Runnable runnable = new Runnable() {
            @Override
            void run() {
                HttpClient client = new DefaultHttpClient(); // Use Apache Commons HTTPClient to perform GET request
                Boolean ok = false;
                HttpResponse response;
                if (method.toLowerCase() == 'get') {
                    HttpGet get = new HttpGet(currentURL.url + param);
                    CookieStore cookieStore = new BasicCookieStore();
                    BasicClientCookie cookie = createCookie();
                    cookieStore.addCookie(cookie);
                    client.setCookieStore(cookieStore);
                    response = client.execute(get);
                } else if (method.toLowerCase() == 'post') {
                    HttpPost post = new HttpPost(currentURL.url);
                    post.setHeader("Content-type", "application/json");
                    post.setEntity(new StringEntity(param, "UTF8"));
                    CookieStore cookieStore = new BasicCookieStore();
                    BasicClientCookie cookie = createCookie()
                    cookieStore.addCookie(cookie);
                    client.setCookieStore(cookieStore);
                    response = client.execute(post);
                } else {
                    throw new IllegalRequestValueExceptions('Bad value');
                }
                HttpEntity entity = response.getEntity();
                if (response.statusLine.statusCode == 200) {
                    ok = true;
                }
                results.add(new AJAXResult(currentURL.url + '  :  ' + IOUtils.toString(entity.getContent(), "UTF-8") + "\n", ok));
                EntityUtils.consume(entity);
            }

            private BasicClientCookie createCookie() {
                BasicClientCookie cookie = new BasicClientCookie('JSESSIONID', sessionCookieStorage.cookie);
                cookie.setDomain(sessionCookieStorage.domain)
                cookie.setPath(sessionCookieStorage.path);
                return cookie;
            }
        }
        return runnable;
    }

}
