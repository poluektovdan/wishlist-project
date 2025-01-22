package org.example.wishlist;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface DBWishlistService {
    void createWishlist(int userId);
    void deleteWishlist(int userId);
    void getWishlists(int userId);
    Optional<Integer> findWishlistId(int userId);
    void updateWishesInWishlist(int wishlistID, String wishName) throws JsonProcessingException;
    String getWishlist(int wishlistID);
}
