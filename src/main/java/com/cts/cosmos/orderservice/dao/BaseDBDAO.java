package com.cts.cosmos.orderservice.dao;

import com.cts.cosmos.orderservice.util.AppConstants;
import com.cts.cosmos.orderservice.util.CosmosDBClientFactory;
import com.microsoft.azure.cosmosdb.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import rx.Observable;

import java.util.Iterator;
import java.util.List;

@Log4j2
public abstract class BaseDBDAO {

    /**
     * This function gets the document list from cosmos DB container
     *
     * @returns Documents fetched from cosmos DB
     */
    protected List<Document> read(String collectionName, String sql) {
        try {
            final FeedOptions options = new FeedOptions();
            options.setEnableCrossPartitionQuery(true);
            options.setMaxItemCount(-1);

            log.info("Sql query for collection {}: {}", collectionName, sql);
            final Observable<FeedResponse<Document>> documentQueryObservable = CosmosDBClientFactory.getDocumentClient().queryDocuments(
                    String.format(AppConstants.DB_COLLECTION_SYMBOLIC_LINK, AppConstants.DATABASE_ID, collectionName), sql, options);

            final Iterator<FeedResponse<Document>> it = documentQueryObservable.toBlocking().getIterator();
            final FeedResponse<Document> page = it.next();
            final List<Document> documentList = page.getResults();

            log.info("Cosmos DB Data retrieved successfully for collection {}", collectionName);
            return documentList;

        } catch (Exception e) {
            log.error("Error during read from Cosmos DB for collection {}: {}", collectionName, ExceptionUtils.getStackFrames(e));
        }
        return null;
    }

    /**
     * This function updates the records into cosmos DB
     * @returns none
     */
    protected void update(String pk, Document input, Document read) {
        try {
            final RequestOptions options = new RequestOptions();
            options.setPartitionKey(new PartitionKey(pk));
            if (read != null) {
                final Observable<ResourceResponse<Document>> response = CosmosDBClientFactory.getDocumentClient()
                        .replaceDocument(read.getSelfLink(), input, options);
                response.toBlocking().getIterator().next();
                log.info("Cosmos DB update successful for partition key : {}", pk);
            }
        } catch (Exception ex) {
            log.error("Error during updating Cosmos DB record: {}", ExceptionUtils.getStackFrames(ex));
        }
    }

    /**
     * This function inserts/updates the records into cosmos DB
     * @returns none
     */
    protected void upsert(String collectionName, String pk, Document input) {
        try {
            final String collectionLink = String.format(AppConstants.DB_COLLECTION_SYMBOLIC_LINK, AppConstants.DATABASE_ID, collectionName);
            final RequestOptions options = new RequestOptions();
            options.setPartitionKey(new PartitionKey(pk));
            final Observable<ResourceResponse<Document>> response = CosmosDBClientFactory.getDocumentClient()
                    .upsertDocument(collectionLink, input, options, false);
            response.toBlocking().getIterator().next();
        } catch (Exception ex) {
            log.error("Error during updating Cosmos DB record: {}", ExceptionUtils.getStackFrames(ex));
        }
    }

    /**
     * This function creates a new document and inserts the records into it
     * @returns none
     */
    protected void create(String collectionName, String pk, Document input) {
        try {
            final String collectionLink = String.format(AppConstants.DB_COLLECTION_SYMBOLIC_LINK, AppConstants.DATABASE_ID, collectionName);
            final RequestOptions options = new RequestOptions();
            options.setPartitionKey(new PartitionKey(pk));
            final Observable<ResourceResponse<Document>> response = CosmosDBClientFactory.getDocumentClient()
                    .createDocument(collectionLink, input, options, false);
            response.toBlocking().getIterator().next();
            log.info("Cosmos Document create successful for partition key : {}", pk);
        } catch (Exception ex) {
            log.error("Error during insert into Cosmos DB: {}", ExceptionUtils.getStackFrames(ex));
        }
    }
}
