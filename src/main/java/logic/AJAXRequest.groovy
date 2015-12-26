package logic

import logic.ajax.AJAXExecutor
import logic.ajax.AJAXResult
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.samplers.AbstractSampler
import org.apache.jmeter.samplers.Entry
import org.apache.jmeter.samplers.SampleResult
import org.apache.jmeter.testelement.property.TestElementProperty

/**
 * Created by User on 2015-12-21.
 */
class AJAXRequest extends AbstractSampler implements Serializable {

    static final String ARGUMENTS = "AJAXRequest.arguments"

    @Override
    SampleResult sample(Entry entry) {
        SampleResult sampleResult = new SampleResult();
        Boolean ok = true;
        StringBuilder data = new StringBuilder();
        sampleResult.sampleLabel = 'Ajax Request';
        sampleResult.dataType = SampleResult.TEXT
        sampleResult.sampleStart();
        Arguments arguments = getArguments();
        AJAXExecutor executor = new AJAXExecutor();
        Queue<AJAXResult> ajaxResults = executor.execute(getThreadContext(), arguments);

        for (AJAXResult ajaxResult : ajaxResults) {
            data.append(ajaxResult.result);
            if (!ajaxResult.ok) {
                ok = false;
            }
        }
        sampleResult.setResponseData(data.toString(), 'UTF-8');
        if (ok) {
            sampleResult.setResponseOK();
        }
        sampleResult.sampleEnd();
        return sampleResult;
    }

    public Arguments getArguments() {
        getProperty(ARGUMENTS).getObjectValue() as Arguments;
    }

    public void setArguments(Arguments args) {
        setProperty(new TestElementProperty(ARGUMENTS, args));
    }
}
