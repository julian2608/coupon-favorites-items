package com.coupon.favorites.items.topfavorites.infrastructure.repository;

import com.coupon.favorites.items.topfavorites.domain.entity.ErrorFavorites;
import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import com.mongodb.MongoException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class ItemFavoriteRepositoryImpl implements ItemFavoriteRepository {

    MongoTemplate mongoTemplate;
    private final String nameCollection;
    private final String nameFieldId;
    private final String nameFieldQuantity;

    private static final Logger logger = LoggerFactory.getLogger(ItemFavoriteRepositoryImpl.class);


    public ItemFavoriteRepositoryImpl(
            MongoTemplate mongoTemplate,
            @Value("${spring.data.mongodb.collection.name.items-favorites}") String nameCollection,
            @Value("${spring.data.mongodb.collection.field.name.id}") String nameFieldId,
            @Value("${spring.data.mongodb.collection.field.name.quantity}") String nameFieldQuantity
    ) {
        this.mongoTemplate = mongoTemplate;
        this.nameFieldId = nameFieldId;
        this.nameFieldQuantity = nameFieldQuantity;
        this.nameCollection = nameCollection;
    }

    @PostConstruct
    private void init (){
        createIndex();
    }

    @Override
    public void incrementQuantity(Collection<String> itemFavorites) {
        CompletableFuture.runAsync(() -> {
            try {
                BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, ItemFavorite.class);

                for (String itemId : itemFavorites) {
                    Query query = new Query(Criteria.where(nameFieldId).is(itemId));
                    Update update = new Update().inc(nameFieldQuantity, 1);
                    bulkOps.upsert(query, update);
                }
                bulkOps.execute();
            }catch (Exception e){
                logger.error(ErrorFavorites.ErrorUpdateFavorites.getMessage(), e);
            }
        });
    }

    @Override
    public List<ItemFavorite> getTopFavorites(int maxTop) {
        try {
            Query query = new Query();
            query.limit(maxTop);
            query.with(Sort.by(Sort.Direction.DESC, nameFieldQuantity));

            return mongoTemplate.find(query, ItemFavorite.class, nameCollection);
        }catch (Exception e){
            logger.error(ErrorFavorites.ErrorGettingFavorites.getMessage(), e);
            return List.of();
        }
    }

    private void createIndex(){
        if (!mongoTemplate.collectionExists(nameCollection)) {
            mongoTemplate.createCollection(nameCollection);
        }

        if (!indexExists(nameCollection, nameFieldId)) {
            mongoTemplate.indexOps(nameCollection).ensureIndex(new Index().on(nameFieldId, Sort.Direction.ASC));
        }
    }

    private boolean indexExists(String collectionName, String fieldName) {
        return mongoTemplate.indexOps(collectionName).getIndexInfo().stream()
                .anyMatch(indexInfo -> indexInfo.getName().equals(fieldName));
    }
}
