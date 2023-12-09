package com.example.catatankeuangan.model;

public class TransactionRequest {

//    @Field("category_id") Integer category_id,
//    @Field("user_id") Integer user_id,
//    @Field("description") String description,
//    @Field("amount") Integer amount,
//    @Field("type") String type

    private Integer id;
    private Integer category_id;
    private Integer user_id;
    private String description;
    private Integer amount;
    private String type;

    public TransactionRequest(String id, Integer category_id, Integer user_id, String description, Integer amount, String type) {
        this.id = Integer.valueOf(id);
        this.category_id = category_id;
        this.user_id = user_id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }
}
