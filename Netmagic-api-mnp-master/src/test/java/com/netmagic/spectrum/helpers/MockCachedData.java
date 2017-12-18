package com.netmagic.spectrum.helpers;

import java.io.IOException;

import org.mockito.Mockito;

import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dto.shoppingcart.request.CachedShoppingCartDetails;
import com.netmagic.spectrum.dto.user.response.UserResponseTemp;

public class MockCachedData {

    private static final String TEMP_USER_RESPONSE = "responses/userAuthentication/tempUserResponse.json";

    private static final String GET_CART_RESPONSE = "responses/shoppingCart/getCartResponse.json";

    static public UserResponseTemp mockCachedTempUser(CacheService<UserResponseTemp> userCacheServiceMock)
            throws IOException {
        UserResponseTemp responseTemp = ResourceLoader.readAndGetObject(TEMP_USER_RESPONSE, UserResponseTemp.class);
        Mockito.when(userCacheServiceMock.get(Mockito.any())).thenReturn(responseTemp);
        return responseTemp;
    }

    public static CachedShoppingCartDetails mockCachedShoppingCart(
            CacheService<CachedShoppingCartDetails> cartCacheService) throws IOException {
        CachedShoppingCartDetails cachedShoppingCartDetails = ResourceLoader.readAndGetObject(GET_CART_RESPONSE,
                CachedShoppingCartDetails.class);
        Mockito.when(cartCacheService.get(Mockito.any())).thenReturn(cachedShoppingCartDetails);
        return cachedShoppingCartDetails;
    }
}
