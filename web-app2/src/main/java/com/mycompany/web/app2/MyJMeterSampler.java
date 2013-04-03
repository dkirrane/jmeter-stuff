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
        return super.getDefaultParameters();
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);

        if (null == server || !(server.isRunning())) {
            startServer();
        }
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        super.teardownTest(context);
    }

    @Override
    public SampleResult runTest(JavaSamplerContext jsc) {
        throw new UnsupportedOperationException("Not supported yet.");

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
