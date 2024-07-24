import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public record Date(int month, int day, int year) { }
    final static List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    final static String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    final static List<String> monthsList = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    public static void main(String[] args) {
        System.out.println("Please provide the first date in MM/DD/YYYY format");
        Date firstDate = getInputAndCheck();
        Date secondDate = getInputAndCheck();
        String firstDay = daysOfWeek[dayOfWeek(firstDate) - 1];
        String secondDay = daysOfWeek[dayOfWeek(secondDate) - 1];
        int calculateDays = getTotalDays(firstDay, secondDay, firstDate, secondDate);
        System.out.println(calculateDays);
    }

    public static int getTotalDays(String firstDay, String secondDay, Date firstDate, Date secondDate) {
        int daysCount = (Math.abs(firstDate.year + 1 - secondDate.year)) * 365;
        for (int i = firstDate.year; i <= secondDate.year; i++) {
            //fix - count is a little too high
            if (isLeapYear(i, 2)) {
                daysCount++;
            }
        }
        daysCount += daysInMonth.get(firstDate.month - 1) - firstDate.day ;
        daysCount += secondDate.day;
        for (int i = firstDate.month + 1; i <= 12; i++) {
            daysCount += daysInMonth.get((i - 1)% 12);
        }
        for (int i = 1; i <= secondDate.month - 1; i++) {
            daysCount += daysInMonth.get((i - 1)% 12);
        }
        return daysCount;
    }

    public static boolean isLeapYear(int year, int month) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            if (month == 1 || month == 2) {
                return true;
            }
        }
        return false;
    }

    public static int dayOfWeek(Date date) {
        int[] monthArray = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
        Map<Integer, Integer> centuryMap = new HashMap<>();
        centuryMap.put(1700 , 4);
        centuryMap.put(1800 , 2);
        centuryMap.put(1900 , 0);
        centuryMap.put(2000 , 6);
        centuryMap.put(2100 , 4);
        centuryMap.put(2200 , 2);
        centuryMap.put(2300 , 0);
        int year = date.year % 100;
        int yearCode = (year + (year / 4)) % 7;
        int monthCode = monthArray[date.month - 1];
        int centuryCode = centuryMap.get(date.year - year);
        int leapYearCode = 0;
        if (isLeapYear(date.year, date.month)) {
            leapYearCode = 1;
        }
        return (yearCode + monthCode + centuryCode - leapYearCode + date.day) % 7;
    }

    public static Date getInputAndCheck() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(true) {
            if(checkFormat(input) && checkDays(input)) {
                break;
            }
            else {
                System.out.println("Invalid input, please reenterr");
                input = scanner.nextLine();
            }
        }
        return praseDate(input.split("/"));
    }


    public static boolean checkDays(String input) {
        int month = Integer.parseInt(input.substring(0, 1));
        int day = Integer.parseInt(input.substring(3, 4));
        if (day > daysInMonth.get(month)) {
            return false;
        }
        return true;
    }

    public static Date praseDate(String[] date) {
        Date date2 = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return date2;
    }

    public static boolean checkFormat(String date) {
        Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }


}