package net.novatech.jbprotocol.auth;

import java.io.IOException;
import java.net.URL;

import org.json.JSONObject;

import com.google.gson.Gson;

import net.novatech.library.utils.NetworkUtils;

public abstract class AuthService {
	
	public static URL YGGDRASIL_AUTH_URL = NetworkUtils.toURL("https://authserver.mojang.com/authenticate");
	public static URL YGGDRASIL_REFRESH_URL = NetworkUtils.toURL("https://authserver.mojang.com/refresh");
	public static URL YGGDRASIL_VALIDATE_URL = NetworkUtils.toURL("https://authserver.mojang.com/validate");
	public static URL YGGDRASIL_INVALIDATE_URL = NetworkUtils.toURL("https://authserver.mojang.com/invalidate");
	
	public static URL MICROSOFT_LOGIN_ENDPOINT = NetworkUtils.toURL("https://login.live.com/oauth20_authorize.srf?redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=code&locale=en&client_id=00000000402b5328");
	public static URL MICROSOFT_TOKEN_ENDPOINT = NetworkUtils.toURL("https://login.live.com/oauth20_token.srf");
	public static URL MICROSOFT_XSTS_ENDPOINT = NetworkUtils.toURL("https://xsts.auth.xboxlive.com/xsts/authorize");
	public static URL MICROSOFT_MINECRAFT_ENDPOINT = NetworkUtils.toURL("https://api.minecraftservices.com/authentication/login_with_xbox");
	public static URL MICROSOFT_ACCOUNT_ENDPOINT = NetworkUtils.toURL("https://api.minecraftservices.com/minecraft/profile");
	public static URL XBL_AUTH = NetworkUtils.toURL("https://user.auth.xboxlive.com/user/authenticate");
	public static URL MICROSOFT_CODE_ENDPOINT = NetworkUtils.toURL("https://login.microsoftonline.com/consumers/oauth2/v2.0/devicecode");
	public static URL MICROSOFT_CODE_TOKEN_ENDPOINT = NetworkUtils.toURL("https://login.microsoftonline.com/consumers/oauth2/v2.0/token");
	
	public abstract AuthSession authenticate() throws IOException;
	
	public static String doRequest(URL url, Object payload) {
		try {
			if(payload == null) {
				return NetworkUtils.doGet(url);
			} else {
				if(payload instanceof JSONObject) {
					return NetworkUtils.doPost(url,((JSONObject)payload).toString(), "application/json");
				}
				return NetworkUtils.doPost(url, payload instanceof String ? (String) payload : new Gson().toJson(payload), "application/json");
			}
		} catch(IOException ex) {
			return null;
		}
	}
	
}