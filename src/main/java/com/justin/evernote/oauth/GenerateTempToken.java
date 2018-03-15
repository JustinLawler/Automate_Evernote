/**
 * 
 */
package com.justin.evernote.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.EvernoteApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;

/**
 * More details on Evernote oAuth here:
 * 		- https://dev.evernote.com/doc/articles/authentication.php/
 * 
 * Web example code here:
 * 		- https://github.com/evernote/evernote-sdk-java/blob/master/sample/oauth/src/main/webapp/index.jsp
 *
 * Uses Java oAuth library scribe - https://github.com/scribejava/scribejava
 */
public class GenerateTempToken
{
	private static final Class<? extends EvernoteApi> PROVIDER_CLASS = EvernoteApi.Sandbox.class;
	static final EvernoteService EVERNOTE_SERVICE = EvernoteService.SANDBOX;
	private static final String YOUR_API_KEY = "jdpl28";
	private static final String YOUR_API_SECRET = "b11886e736ed70d7";
	
	/**
	 */
	public static void main(String[] args) throws Exception
	{
		OAuthService service = new ServiceBuilder()
				.provider(PROVIDER_CLASS)
				.apiKey(YOUR_API_KEY)
                .apiSecret(YOUR_API_SECRET)
                .callback("http://justinlawler.net")
                .build();
		
		//getRequestToken(service);
		
		String requestToken = "jdpl28.1622A8BE800.687474703A2F2F6A757374696E6C61776C65722E6E6574.79D43742389FF8A347CC93FBED164DE3";
		String requestTokenSecret = "167484A8A2ABEADF0A57575339F2788F";
		String oauth_verifier = "E08A82F131AE20855E06CC7B3FC13D87";
		getAccessToken(service, requestToken, requestTokenSecret, oauth_verifier);
	}
	
	/**
	 * Generate the URL the user uses to authenticate our app against.
	 *
	 * Returns something like:
	 * 		https://sandbox.evernote.com/OAuth.action
	 *				?oauth_token=jdpl28.1622577B1F1.687474703A2F2F6A757374696E6C61776C65722E6E6574.526D9D2F02653063EB02A47B628A489A 
	 */
	public static String getRequestToken(OAuthService service)
	{
		Token scribeRequestToken = service.getRequestToken();
        String requestToken = scribeRequestToken.getToken();
        String requestTokenSecret = scribeRequestToken.getSecret();

		String authUrl = EVERNOTE_SERVICE.getAuthorizationUrl(scribeRequestToken.getToken());
		
		System.out.println("++++++ Request Token +++++++++");
        System.out.println("" + requestToken);
        System.out.println("" + requestTokenSecret);
        System.out.println("" + authUrl);
        System.out.println("+++++++++++++++++++++++++++++");
        
        return authUrl;
	}
	
	/**
	 * Once user navigates & validates on Evernote, they will be redirected to something like:
	 *		http://justinlawler.net/
	 *				?oauth_token=jdpl28.1622577B1F1.687474703A2F2F6A757374696E6C61776C65722E6E6574.526D9D2F02653063EB02A47B628A489A
	 *				&oauth_verifier=DB1C1480FB667C9C64D1FA82276EAF2C
	 *				&sandbox_lnb=false
	 */
	private static void getAccessToken(
				OAuthService service,
				String requestToken,
				String requestTokenSecret,
				String oauth_verifier)
	{
        Token scribeRequestToken = new Token(requestToken, requestTokenSecret);
        Verifier scribeVerifier = new Verifier(oauth_verifier);
        Token scribeAccessToken = service.getAccessToken(scribeRequestToken, scribeVerifier);
        EvernoteAuth evernoteAuth = EvernoteAuth.parseOAuthResponse(EVERNOTE_SERVICE, scribeAccessToken.getRawResponse());
        String accessToken = evernoteAuth.getToken();
        String noteStoreUrl = evernoteAuth.getNoteStoreUrl();
        
        System.out.println("++++++ Access Token +++++++++");
        System.out.println("" + accessToken);
        System.out.println("" + noteStoreUrl);
        System.out.println("+++++++++++++++++++++++++++++");
	}
}
