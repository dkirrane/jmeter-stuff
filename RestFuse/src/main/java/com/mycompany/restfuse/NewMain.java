package com.mycompany.restfuse;

import com.eclipsesource.restfuse.HttpJUnitRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runners.model.InitializationError;

/**
 *
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runner runner = null;
        try {
            runner = new HttpJUnitRunner(GetResource1Test.class);
        } catch (InitializationError ex) {
            Logger.getLogger(GetResource1Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        JUnitCore juc = new JUnitCore();
        Result result = juc.run(runner);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
