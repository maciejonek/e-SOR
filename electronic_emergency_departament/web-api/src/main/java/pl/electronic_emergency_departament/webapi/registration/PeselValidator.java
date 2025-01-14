package pl.electronic_emergency_departament.webapi.registration;

public class PeselValidator {

    public static int getChecksum(String pesel) {
        int[] tab = new int[11];
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1};

        for (int i = 0; i < 11; i++) {
            tab[i] = getDigitFromPos(pesel, i);
        }

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += weights[i] * tab[i];
        }
        sum = 10 - (sum % 10);
        return sum % 10;
    }

    public static int getBirthYear(String pesel) {
        if (!isPeselValid(pesel))
            return -1;

        int year = 10 * getDigitFromPos(pesel, 0) + getDigitFromPos(pesel, 1);
        int month = 10 * getDigitFromPos(pesel, 2) + getDigitFromPos(pesel, 3);

        if (month > 80 && month < 93) {
            year += 1800;
        } else if (month > 0 && month < 13) {
            year += 1900;
        } else if (month > 20 && month < 33) {
            year += 2000;
        } else if (month > 40 && month < 53) {
            year += 2100;
        } else if (month > 60 && month < 73) {
            year += 2200;
        }
        return year;
    }

    public static int getBirthMonth(String pesel) {
        if (!isPeselValid(pesel))
            return -1;

        int month = 10 * getDigitFromPos(pesel, 2) + getDigitFromPos(pesel, 3);

        if (month > 80 && month < 93) {
            month -= 80;
        } else if (month > 20 && month < 33) {
            month -= 20;
        } else if (month > 40 && month < 53) {
            month -= 40;
        } else if (month > 60 && month < 73) {
            month -= 60;
        }
        return month;
    }

    public static int getBirthDay(String pesel) {
        if (!isPeselValid(pesel))
            return -1;

        return 10 * getDigitFromPos(pesel, 4) + getDigitFromPos(pesel, 5);
    }

    public static String getSex(String pesel) {
        if (!isPeselValid(pesel))
            return "Nie zwalidowano numeru PESEL";

        int genderPos = getDigitFromPos(pesel, 9);

        if (genderPos % 2 == 0)
            return "Kobieta";
        return "Mężczyzna";
    }

    public static boolean isPeselValid(String pesel) {
        if (pesel == null || pesel.length() != 11)
            return false;

        return getDigitFromPos(pesel, 10) == getChecksum(pesel);
    }

    private static int getDigitFromPos(String number, int pos) {
        return Character.getNumericValue(number.charAt(pos));
    }
}
