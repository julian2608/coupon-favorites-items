package com.coupon.favorites.items.topfavorites.infrastructure.repository;

import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class FavoriteRepositoryImpl implements ItemFavoriteRepository {

    MongoTemplate mongoTemplate;

    public FavoriteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Integer incrementQuantity(Collection<String> itemFavorites) {
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, ItemFavorite.class);

        for (String itemId : itemFavorites) {
            Query query = new Query(Criteria.where("_id").is(itemId));
            Update update = new Update().inc("quantity", 1);
            bulkOps.upsert(query, update);
        }

        BulkWriteResult bulkResult = bulkOps.execute();
        return bulkResult.getModifiedCount() + bulkResult.getUpserts().size();
    }

    @Override
    public List<ItemFavorite> getTopFavorites(int maxTop) {
        Query query = new Query();
        query.limit(maxTop);
        query.with(Sort.by(Sort.Direction.DESC, "quantity"));

        return mongoTemplate.find(query, ItemFavorite.class, "item-favorite");
    }
}
