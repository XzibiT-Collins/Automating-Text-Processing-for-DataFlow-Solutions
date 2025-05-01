package com.example.automatedtextprocessing.DataManager;

public class Document {
    String id;
    String title;
    String content;

    Document(String id,String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    //getters
    public String getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    //setters
    public void setTitle(String newTitle){
        if(newTitle.isEmpty()){
            //throw custom error
            return;
        }
        this.title = newTitle;
    }

    public void setContent(String content){
        this.content = content;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(!(object instanceof Document)){
            return false;
        }

        Document that = (Document) object;
        return id.equals(that.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    @Override
    public String toString(){
        return "["+ id + "] " + title ;
    }
}
