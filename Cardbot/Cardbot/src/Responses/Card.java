package Responses;

public class Card {

    private String rarity;
    private int price;
    private String name;
    private int amount;

    public Card(String rarity, String name, int amount) {
        this.rarity = rarity;
        //ToDo Price
        this.name = name;
        this.amount = amount;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s - %s", this.rarity, this.name, this.amount);
    }

    public String shopPrint()
    {
        return String.format("%s - %s - %s / ", this.rarity, this.name, this.price, this.price*0.7);
    }
}
