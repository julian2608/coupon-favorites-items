package com.coupon.favorites.items.topfavorites.infrastructure.repository;

import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteRepositoryImpl implements ItemFavoriteRepository {

    MongoTemplate mongoTemplate;

    public FavoriteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Integer incrementQuantityById(String itemFavorite) {

        Query query = new Query(Criteria.where("_id").is(itemFavorite));
        Update update = new Update().inc("quantity", 1);

        UpdateResult updatedItemFavorite = mongoTemplate.upsert(query, update, ItemFavorite.class, "item-favorite");

        return updatedItemFavorite.getMatchedCount() != 0L || updatedItemFavorite.getModifiedCount() != 0L
                ? 1 : 0;
    }

    @Override
    public List<ItemFavorite> getTopFavorites(int maxTop) {
        Query query = new Query();
        query.limit(maxTop);
        query.with(Sort.by(Sort.Direction.DESC, "quantity"));

        return mongoTemplate.find(query, ItemFavorite.class, "item-favorite");
    }
}
