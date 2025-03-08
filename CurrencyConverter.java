import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a numeric value: ");
        double amount = scanner.nextDouble();

        String currencyWords = convertToCurrencyWords(amount);
        System.out.println("RINGGIT MALAYSIA: " + currencyWords);
    }

    public static String convertToCurrencyWords(double value) {
        long ringgit = (long) value;
        int sen = (int) ((value - ringgit) * 100);

        String ringgitWords = convertToWords(ringgit);
        String senWords = convertToWords(sen);

        return ringgitWords + " AND " + senWords + " CENTS";
    }

    public static String convertToWords(long number) {
        String[] units = {"", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
        String[] tens = {"", "TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"};
        String[] teens = {"", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};

        StringBuilder words = new StringBuilder();

        if (number >= 1000) {
            words.append(units[(int) (number / 1000)]).append(" THOUSAND ");
            number %= 1000;
        }

        if (number >= 100) {
            words.append(units[(int) (number / 100)]).append(" HUNDRED ");
            number %= 100;
        }

        if (number >= 20) {
            words.append(tens[(int) (number / 10)]).append(" ");
            number %= 10;
        } else if (number >= 11) {
            words.append(teens[(int) (number - 10)]).append(" ");
            number = 0;
        }

        if (number > 0) {
            words.append(units[(int) number]).append(" ");
        }

        return words.toString().trim();
    }
}
