package com.example.DonationPlatform.domain;

public enum Rating {
    firstLevel("1",0,0),
    secondLevel("2",1,500),
    thirdLevel("3",2,1000);

    private final String name;
    private final int id;
    private final int requiredAmount;

    Rating(String name, int id, int requiredAmount) {
        this.name = name;
        this.id = id;
        this.requiredAmount = requiredAmount;
    }
}
