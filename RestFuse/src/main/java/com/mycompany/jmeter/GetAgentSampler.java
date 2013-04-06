package com.mycompany.jmeter;

import java.io.Serializable;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * See example
 * http://newspaint.wordpress.com/2012/11/28/creating-a-java-sampler-for-jmeter/
 *
 * This Sampler can be compiled and the resulting jar placed in the lib/ext
 * subdirectory of the JMeter distribution.
 */
public class GetAgentSampler extends AbstractJavaSamplerClient implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggingManager.getLoggerForClass();

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);

    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("URL", "http://www.google.com/");
        defaultParameters.addArgument("SEARCHFOR", "newspaint");
        return defaultParameters;
    }

    public SampleResult runTest(JavaSamplerContext context) {
        LOG.info("Running test.");

        // pull parameters
        String urlString = context.getParameter("URL");
        String searchFor = context.getParameter("SEARCHFOR");

        SampleResult result = new SampleResult();
        result.sampleStart(); // start stopwatch

        try {
            java.net.URL url = new java.net.URL(urlString + "?q=" + searchFor);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection(); // have to cast connection
            connection.setRequestMethod("GET");
            connection.connect();

            result.sampleEnd(); // stop stopwatch
            result.setSuccessful(true);
            result.setResponseMessage("Successfully performed action");
            result.setResponseCodeOK(); // 200 code
        } catch (Exception e) {
            result.sampleEnd(); // stop stopwatch
            result.setSuccessful(false);
            result.setResponseMessage("Exception: " + e);

            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString());
            result.setDataType(org.apache.jmeter.samplers.SampleResult.TEXT);
            result.setResponseCode("500");
        }

        return result;
    }
}
