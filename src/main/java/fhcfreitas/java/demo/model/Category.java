package fhcfreitas.java.demo.model;

public enum Category {
    ACTION("Action"),
    COMEDY("Comedy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime");
    private String omdbCategory;

    Category(String omdbCategory) {
        this.omdbCategory = omdbCategory;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.omdbCategory.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Category didn't found: " + text);
    }

}
