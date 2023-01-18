package src;

public class RegularPrice extends Price {
    @Override
    public int getPriceCode() {
        return Movie.REGULAR;
    }

    @Override
    public double getCharge(int daysRented) {
    //determine amounts for each line
        double result = 0;
        result += 2;
        if (daysRented > 2) {
            result += (daysRented-2) *1.5;
        }
        return result;
    }

}
