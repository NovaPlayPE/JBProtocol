package net.novatech.jbprotocol.auth;

import java.io.IOException;
import java.net.URL;

import net.novatech.library.utils.NetworkUtils;

public abstract class AuthService {
	
	public static URL YGGDRASIL_AUTH_URL = NetworkUtils.toURL("https://authserver.mojang.com/authenticate");
	public static URL YGGDRASIL_REFRESH_URL = NetworkUtils.toURL("https://authserver.mojang.com/refresh");
	public static URL YGGDRASIL_VALIDATE_URL = NetworkUtils.toURL("https://authserver.mojang.com/validate");
	public static URL YGGDRASIL_INVALIDATE_URL = NetworkUtils.toURL("https://authserver.mojang.com/invalidate");
	
	public abstract AuthSession authenticate() throws IOException;
	
}