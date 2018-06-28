package test.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

public class TestStronaGlownaEndpoint {
	@Test
	public void testGetStronaGlownaEndpoint() throws IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/strona_glowna.xhtml");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	}
}
