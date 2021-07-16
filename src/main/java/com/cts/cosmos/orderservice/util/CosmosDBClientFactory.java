package com.cts.cosmos.orderservice.util;

import com.microsoft.azure.cosmosdb.ConnectionPolicy;
import com.microsoft.azure.cosmosdb.ConsistencyLevel;
import com.microsoft.azure.cosmosdb.rx.AsyncDocumentClient;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Factory class to get a single instance of Document Client
 *
 */
@UtilityClass
@Log4j2
public class CosmosDBClientFactory {

    private static AsyncDocumentClient client;

    //TODO: replace with KeyVault calls
    private static String dbHost = "https://order-service-ue-cosmos-d.documents.azure.com:443/";
    private static String dbKey = "3QkNnxPELUvysDiwXPw4HXG125L7SLq9MMKol5cxdrRjjBVdsPA0QQXGcn3asAp468TrKYYk3FuEDwHnQR03Gw==";

    /**
     * Initiate a new document client or return existing client instance.
     * @return Returns document client for cosmos
     */
    public static synchronized AsyncDocumentClient getDocumentClient() {
        if (null == client) {
            try {
                client = new AsyncDocumentClient.Builder().withServiceEndpoint(dbHost)
                        .withMasterKeyOrResourceToken(dbKey)
                        .withConnectionPolicy(ConnectionPolicy.GetDefault()).withConsistencyLevel(ConsistencyLevel.Session)
                        .build();

                log.info("Connection to Cosmos DB is successful");
            } catch (Exception e) {
                log.error("Error in Cosmos DB client creation : {}", e.getMessage());
            }
        }
        return client;
    }
}
