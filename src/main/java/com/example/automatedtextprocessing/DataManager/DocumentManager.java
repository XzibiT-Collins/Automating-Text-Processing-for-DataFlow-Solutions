package com.example.automatedtextprocessing.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentManager {
    public final Map<String,Document> documentDatabase = new HashMap<>();

   //get Document
    public Document getDocument(String id){
       return documentDatabase.get(id);
    }

    //add new document
    public void addDocument(Document doc){
        documentDatabase.put(doc.getId(),doc);
    }

    //get all documents
    public List<Document> getAllDocuments(){
        return new ArrayList<>(documentDatabase.values());
    }

    //update document
    public boolean updateDocument(String id,String newTitle,String newContent){
        Document doc = documentDatabase.get(id);

        if(doc != null){
            doc.setTitle(newTitle);
            doc.setContent(newContent);

            return true;
        }
        return false;
    }

    //delete document
    public boolean deleteDocument(String id){
        if(documentDatabase.containsKey(id)){
            documentDatabase.remove(id);
            return true;
        }
        return false;
    }
}
