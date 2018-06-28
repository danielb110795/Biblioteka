package test.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

public class TestMojeKontoEndpoint {
	@Test
	public void testGetMojeKontoEndpoint() throws IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/moje_konto.xhtml");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	}
	
	@Test
	public void testGetRejestracjaEndpoint() throws IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/nowe_konto.xhtml");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	}
	
	@Test
	public void testGetOdzyskajHasloEndpoint() throws IOException {
		HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/odzyskaj_haslo.xhtml");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
	}
}
