package Retries;

import DistRestSample.Contracts.*;

public interface ISingleMethodPolicy {

	void HadResponse(SimpleResponse response);

	void PerformWaitIfNeeded() throws Exception;

	void ThrowErrorFromResponseIfAppropriate() throws Exception;

	boolean ShouldRetry();

}
