package com.example.DonationPlatform.domain;

public enum Rating {
    x1("1",0,0),
    x2("2",1,500),
    x3("3",2,1000),
    x4("4",3,1500);

    private final String name;
    private final int id;
    private final int requiredAmount;

    Rating(String name, int id, int requiredAmount) {
        this.name = name;
        this.id = id;
        this.requiredAmount = requiredAmount;
    }
}
