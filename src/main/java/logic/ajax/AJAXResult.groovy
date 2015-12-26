package logic.ajax

/**
 * Created by User on 2015-12-23.
 */
class AJAXResult {

    private final String result;
    private final Boolean ok;

    AJAXResult(String result, Boolean ok) {
        this.result = result
        this.ok = ok;
    }

    String getResult() {
        return result
    }

    boolean isOk() {
        return ok
    }
}
