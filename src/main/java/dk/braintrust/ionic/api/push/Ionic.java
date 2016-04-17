package dk.braintrust.ionic.api.push;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author slarsen
 *
 */
public class Ionic {
    private static String PUSH_URL = "https://api.ionic.io/push/notifications";
    private static String PUSH_STATUS_URL = "https://api.ionic.io/push/notifications/";
    private final String API_TOKEN;
    
    /**
     * This parameter can be found at https://apps.ionic.io/ in your specific app.
     * @param token (api token)
     */
    public Ionic(String token) {
        API_TOKEN = token;   
    }
    
    /**
     * Push your notification to the ionic framework and hence your 
     * app (with the app id defined in the constructor of Ionic class)
     * @param notification (JSON formated string)
     * @return result of the push message. it will write that it is enqueued if successful.
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String push(String notification) throws ClientProtocolException, IOException {
        HttpPost request = new HttpPost(PUSH_URL);
        request.addHeader("content-type", ContentType.APPLICATION_JSON.toString());             
        request.addHeader("Authorization", "Bearer " + API_TOKEN);      
        CloseableHttpClient client = HttpClientBuilder.create().build();
        request.setEntity(new StringEntity(notification));
        return executeSafeRequest(client, request);
    }

    /**
     * Get a status of a given push message
     * @param messageId
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public String getPushStatus(String messageId) throws ClientProtocolException, IOException {
        HttpGet request = new HttpGet(PUSH_STATUS_URL + messageId);
        request.addHeader("content-type", ContentType.APPLICATION_JSON.toString());     
        CloseableHttpClient client = HttpClientBuilder.create().build();
        return executeSafeRequest(client, request);
    }
    
    private String executeSafeRequest(CloseableHttpClient client, HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpEntity entity = null;
        String response = "";
        try {
            entity = client.execute(request).getEntity();
            response = EntityUtils.toString(entity, "UTF-8");
        } finally {
             client.close();
        }
        return response;
    }
}
