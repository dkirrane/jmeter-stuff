package com.mycompany.web.app2;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 */
public class MyJMeterSampler extends AbstractJavaSamplerClient {

    private static final ReentrantLock lock = new ReentrantLock();
    private static Server server;
    private static final Map<String, String> WORKID_MAP = new ConcurrentHashMap<String, String>();

    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("memcached_servers", "localhost:11211");
        defaultParameters.addArgument("username", "testuser");
        defaultParameters.addArgument("password", "testpasswd");
        return defaultParameters;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        if (null == server || !(server.isRunning())) {
            startServer();
        }
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        try {
            server.stop();
        } catch (Exception ex) {
            Logger.getLogger(MyJMeterSampler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext jsc) {
        SampleResult result = new SampleResult();
        boolean success = true;
        result.sampleStart();

        //
        // Write your test code here.
        //
        NewJerseyClient client = new NewJerseyClient();
        Object response = client.getXml();
        // do whatever with response
        client.close();

        result.sampleEnd();
        result.setSuccessful(success);
        return result;
    }

    private void startServer() {
        lock.lock();
        try {
            if (null != server) {
                return;
            }
            Handler handler = new AbstractHandler() {
                @Override
                public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                    response.setContentType("text/html;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_OK);
                    baseRequest.setHandled(true);
                    response.getWriter().println("<h1>Hello World</h1>");
                }
            };

            server = new Server(8080);
            server.setHandler(handler);
            try {
                server.start();
            } catch (Exception ex) {
                Logger.getLogger(MyJMeterSampler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            lock.unlock();
        }
    }
}
