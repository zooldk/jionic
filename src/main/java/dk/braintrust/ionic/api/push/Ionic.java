package dk.braintrust.ionic.api.push;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author slarsen
 *
 */
public class Ionic {
	private static String PUSH_URL = "https://push.ionic.io/api/v1/push";
	private static String PUSH_STATUS_URL = "https://push.ionic.io/api/v1/status/";	
	private static String APP_ID = "";
	private static byte[] SECRET_ENCODED = null;
	
	
	/**
	 * These two parameters can be found at https://apps.ionic.io/ in your specific app.
	 * @param appId (application id)
	 * @param secret (api key - secret)
	 */
	public Ionic(String appId, String secret) {
		SECRET_ENCODED = Base64.encodeBase64(secret.getBytes());
		APP_ID = appId;		
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
		request.addHeader("X-Ionic-Application-Id", APP_ID);
		request.addHeader("content-type", ContentType.APPLICATION_JSON.toString());				
		request.addHeader("Authorization", "Basic " + new String(SECRET_ENCODED));		
		HttpClient client = HttpClientBuilder.create().build();
		request.setEntity(new StringEntity(notification));
		HttpEntity entity = client.execute(request).getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}

	/**
	 * Get a status of a given push message
	 * @param messageId
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String getPushStatus(String messageId) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(PUSH_STATUS_URL + messageId);
		request.addHeader("X-Ionic-Application-Id", APP_ID);
		request.addHeader("content-type", ContentType.APPLICATION_JSON.toString());				
		request.addHeader("Authorization", "Basic " + new String(SECRET_ENCODED));		
		HttpClient client = HttpClientBuilder.create().build();
		HttpEntity entity = client.execute(request).getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}
	

}
