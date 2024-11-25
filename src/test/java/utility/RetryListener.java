package utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Check if retry analyzer is already set, if not, set it
    	
    	System.out.println("Under RetryListener -> transform");
    	
        if (testAnnotation.getRetryAnalyzerClass() == null) {
            // Set the retry analyzer class to FailRetry
        	System.out.println("Under RetryListener -> condition");
            testAnnotation.setRetryAnalyzer(FailRetry.class);
        }else {
        	testAnnotation.setRetryAnalyzer(FailRetry.class);        }
    }
}
