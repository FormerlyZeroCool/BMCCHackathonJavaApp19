package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

public class DynamoIO {

	

    static AmazonDynamoDB dynamoDB;
    static DynamoDB dB;
    /**
     * The only information needed to create a client are security credentials
     * consisting of the AWS Access Key ID and Secret Access Key. All other
     * configuration, such as the service endpoints, are performed
     * automatically. Client parameters, such as proxies, can be specified in an
     * optional ClientConfiguration object when constructing a client.
     *
     * @see com.amazonaws.auth.BasicAWSCredentials
     * @see com.amazonaws.auth.ProfilesConfigFile
     * @see com.amazonaws.ClientConfiguration
     */
    public DynamoIO()
    {
    	 ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
         try {
             credentialsProvider.getCredentials();
         } catch (Exception e) {
             throw new AmazonClientException(
                     "Cannot load the credentials from the credential profiles file. " +
                     "Please make sure that your credentials file is at the correct " +
                     "location (/Users/melwermuth/.aws/credentials), and is in valid format.",
                     e);
         }
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion("us-east-1")
            .build();
        dB=new DynamoDB(dynamoDB);
    }
    public ArrayList<Item> getAllToDoItems(String tableName)
    {
    	ArrayList<Item> itemList=new ArrayList<Item>();
        Table table = dB.getTable(tableName);

        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("task");

        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);

            Iterator<Item> iter = items.iterator(); 
            int i=0;
            while (iter.hasNext()) 
            {
                itemList.add(iter.next());
                //System.out.println(itemList.get(i));
                i++;
            }
        }
        catch (Exception e) {
            System.err.println("Unable to scan the table:");
            System.err.println(e.getMessage());
        }
    	return itemList;
    }
    public ArrayList<Item> getAllCalendarItems(String tableName)
    {
    	ArrayList<Item> itemList=new ArrayList<Item>();
        Table table = dB.getTable(tableName);

        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("userId, movieTitle");

        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);

            Iterator<Item> iter = items.iterator();
            int i=0;
            while (iter.hasNext()) {
                itemList.add(iter.next());
                //System.out.println(itemList.get(i));
                i++;
            }

        }
        catch (Exception e) {
            System.err.println("Unable to scan the table:");
            System.err.println(e.getMessage());
        }
    	return itemList;
    }
    public Map<String, AttributeValue> newMovie(String movie, String userId) 
    {
    	String tableName="testTable";
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("movieTitle2", new AttributeValue(movie));
        item.put("userId", new AttributeValue(userId));
        PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        return item;
    }
	
}
