package test.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

public class TestNotFoundEndpoint {
    @Test
    public void testGetFailureStrukturaEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/strukura.xhtml");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    
    @Test
    public void testGetFailureKontaktEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/kontak.xhtml");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    
    @Test
    public void testGetFailueSpisKsiazekEndpoint() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/Biblioteka/spisksiazek.xhtml");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
}
