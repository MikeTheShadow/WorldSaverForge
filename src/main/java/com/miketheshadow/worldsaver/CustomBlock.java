package com.miketheshadow.worldsaver;

import com.miketheshadow.worldsaver.storage.DataStorage;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class CustomBlock {

    public String rotation = "none";
    public String material;
    private final Document document = new Document();
    public int x;
    public int y;
    public int z;

    public CustomBlock(HashMap<String, String> dataMap) {
        for(Map.Entry<String, String> item : dataMap.entrySet()) {
            document.put(item.getKey(),item.getValue());
        }
    }

    public void save() {
        String blockID = x + "|" + y + "|" + z;

        document.put("x",x);
        document.put("y",y);
        document.put("z",z);

        document.put("rotation",rotation);
        document.put("material",material);
        document.put("uuid",blockID);
        if(DataStorage.DATABASE.find(new Document("uuid",blockID)).first() != null) return;
        DataStorage.DATABASE.insertOne(document);
    }
}