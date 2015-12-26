package gui

import logic.AJAXRequest
import logic.ajax.AJAXResult
import org.apache.jmeter.config.Argument
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.config.gui.ArgumentsPanel
import org.apache.jmeter.protocol.http.sampler.SoapSampler
import org.apache.jmeter.samplers.gui.AbstractSamplerGui
import org.apache.jmeter.testelement.TestElement
import org.apache.jorphan.gui.ObjectTableModel
import org.apache.jorphan.reflect.Functor

import javax.swing.JPanel
import java.awt.BorderLayout

/**
 * Created by User on 2015-12-21.
 */
class AjaxRequestGui extends AbstractSamplerGui {

    private static final long serialVersionUID = 240L;
    private ArgumentsPanel argumentsPanel;

    AjaxRequestGui() {
        this.init();
    }

    @Override
    String getLabelResource() {
        return "AJAX_SAMPLER"
    }

    @Override
    TestElement createTestElement() {
        AJAXRequest sampler = new AJAXRequest();
        this.modifyTestElement(sampler);
        return sampler;
    }

    @Override
    void modifyTestElement(TestElement sampler) {
        this.configureTestElement(sampler);
        if (sampler instanceof AJAXRequest) {
            AJAXRequest ajaxSampler = sampler as AJAXRequest;
            ajaxSampler.setArguments(this.argumentsPanel.createTestElement() as Arguments)
        }
    }

    public void clearGui() {
        super.clearGui();
        this.argumentsPanel.clear();
    }


    private void init() {
        this.setLayout(new BorderLayout());
        this.setBorder(this.makeBorder())

        this.add(this.makeTitlePanel(), BorderLayout.NORTH);
        this.add(this.makeArgumentsPanel(), BorderLayout.CENTER);

        JPanel streamsCodePane = new JPanel(new BorderLayout());
        this.add(streamsCodePane, BorderLayout.SOUTH);
    }

    @Override
    void configure(TestElement element) {
        super.configure(element);
        AJAXRequest ajaxRequest = element as AJAXRequest;
        this.argumentsPanel.configure(ajaxRequest.arguments);
    }

    private JPanel makeArgumentsPanel() {

        String[] strings = ['Method', 'URL', 'Value']
        Functor[] functors = [new Functor('getName'), new Functor('getValue'), new Functor('getDescription')];
        Functor[] functors2 = [new Functor('setName'), new Functor('setValue'), new Functor('setDescription')];
        Class[] classes = [String.class, String.class, String.class];

        this.argumentsPanel = new ArgumentsPanel('AJAX REQUEST', null, true, false,
                new ObjectTableModel(strings, Argument.class,
                        functors,
                        functors2, classes
                )
        )
    }

}
