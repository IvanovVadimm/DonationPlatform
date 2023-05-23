package com.example.DonationPlatform.domain.enums;

public enum Rating {
    firstLevel("firstLevel", 0),
    secondLevel("secondLevel", 1000),
    thirdLevel("thirdLevel", 2000),
    fourthLevel("fourthLevel", 3000),
    fifthLevel("fifthLevel", 4000);
    private String name;
    private int requiredAmount;

    Rating(String name, int requiredAmount) {
        this.name = name;
        this.requiredAmount = requiredAmount;
    }

    public String getNameOfRatingLevel() {
        return name;
    }

    public int getRequiredAmountOfRatingLevel() {
        return requiredAmount;
    }
}