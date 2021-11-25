package com.example.diaryclf;

public class words {

    private int id;
    private String name;
    private String meaning;
    private String example;

    public words(){

    }

    public words(String name, String meaning, String example){
        this.name = name;
        this.meaning = meaning;
        this.example = example;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
