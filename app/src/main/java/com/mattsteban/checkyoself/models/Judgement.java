package com.mattsteban.checkyoself.models;

/**
 * Created by Esteban on 8/1/16.
 */
public class Judgement {
    String fieldName;
    int fieldValue;

    public Judgement() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Judgement(String fieldName, int stars){
        this.fieldName = fieldName;
        this.fieldValue = stars;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue = fieldValue;
    }
}
