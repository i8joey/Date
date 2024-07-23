import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public record Date(int month, int day, int year, int dayOfWeek) { }

    final static int sunday = 0;
    final static int monday = 1;
    final static int tuesday = 2;
    final static int wednesday = 3;
    final static int thursday = 4;
    final static int friday = 5;
    final static int saturday = 6;
    final static int[] monthCode = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please provide two dates in MM/DD/YYYY format");
            return;
        }
        final var start = parseDate(args[0]);
        final var end = parseDate(args[1]);

        final var days = daysBetween(start, end, friday);
        System.out.println("Number of Fridays between " + start + " and " + end + " is " + days);
    }

    static Date parseDate(String input) {
        final var parts = input.split("/");
        var parsedYear = Integer.parseInt(parts[0]);
        var parsedMonth = Integer.parseInt(parts[1]);
        var parsedDay = Integer.parseInt(parts[2]);
        Map<Integer, Integer> centuryCode = new HashMap<>();
        centuryCode.put(1700 , 4);
        centuryCode.put(1800 , 2);
        centuryCode.put(1900 , 0);
        centuryCode.put(2000 , 6);
        centuryCode.put(2100 , 4);
        centuryCode.put(2200 , 2);
        centuryCode.put(2300 , 0);
        int yearCode = (parsedYear + ((parsedYear%100) / 4)) % 7;
        int monthCode = Main.monthCode[parsedMonth - 1];
        int century = centuryCode.get(parsedYear % 100);
        int leapYearCode = 0;
        if ((parsedYear % 4 == 0 && parsedYear % 100 != 0) || parsedYear % 400 == 0) {
            if (parsedMonth == 1 || parsedMonth == 2) {
                leapYearCode = 1;
            }
        }
        int findDay = (yearCode + monthCode + century + parsedDay - leapYearCode) % 7;
        Date date = new Date(parsedMonth, parsedDay, parsedYear, findDay);
        return date;
    }

    static int daysBetween(Date start, Date end, int day) {

    }




    public static void foo(String[] args) {


        int year = dateInteger.get(2) % 100;



        int leapYearCode = 0;


        for (Map.Entry<Integer, Integer> entry : centuryCode.entrySet()) {
            if (entry.getKey() == dateInteger.get(2) - year) {
                findDay += entry.getValue();
                break;
            }
        }
        int theDay = findDay % 7;
        Map<Integer, String> dayCode = new HashMap<>();
        dayCode.put(0, "Sunday");
        dayCode.put(1, "Monday");
        dayCode.put(2, "Tuesday");
        dayCode.put(3, "Wednesday");
        dayCode.put(4, "Thursday");
        dayCode.put(5, "Friday");
        dayCode.put(6, "Saturday");
        System.out.println("The day on " + input + " is a " + dayCode.get(theDay));
        System.out.println(leapYearCode);
    }

    public boolean correctFormat(String input) {
        Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}