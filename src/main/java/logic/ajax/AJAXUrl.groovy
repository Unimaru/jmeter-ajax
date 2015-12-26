package logic.ajax

/**
 * Created by User on 2015-12-23.
 */
class AJAXUrl {

    private final String url;

    AJAXUrl(String url, String request) {
        this.url = url + request;
    }

    String getUrl() {
        return url
    }
}
