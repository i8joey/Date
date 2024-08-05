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
        String firstDay = daysOfWeek[dayOfWeek(firstDate)];
        String secondDay = daysOfWeek[dayOfWeek(secondDate)];
        int calculateDays = getTotalDays(firstDay, secondDay, firstDate, secondDate);
        System.out.println(calculateDays);
        System.out.println(getFridays(calculateDays, firstDay, secondDay));
    }

    public static int getFridays(int days, String firstDay, String secondDay) {
        int first = Arrays.asList(daysOfWeek).indexOf(firstDay);
        int second = Arrays.asList(daysOfWeek).indexOf(secondDay);
        int weeks = days / 7;
        int friday = 7 * weeks + Math.abs(first - 5 - 1);
        if (friday > days) {
            friday --;
        }
        return friday / 7;
    }

    public static int getTotalDays(String firstDay, String secondDay, Date firstDate, Date secondDate) {
        int daysCount = secondDate.day + Math.abs(firstDate.day - daysInMonth.get(firstDate.month - 1));
        for (int i = firstDate.year; i <= secondDate.year; i ++) {
            int startMonth = 1;
            int endMonth = 12;
            if (i == firstDate.year && firstDate.month != 12) {
                startMonth = firstDate.month + 1;
            }
            else if (firstDate.month == 12 && i == firstDate.year){
                continue;
            }
            if (i == secondDate.year) {
                endMonth = secondDate.month - 1;
            }
            for (int j = startMonth; j <= endMonth; j++) {
                daysCount += daysInMonth.get(j - 1);
            }
        }
        if (isLeapYear(firstDate.year, firstDate.month)) {
            daysCount ++;
        }
        if (isLeapYear(secondDate.year, secondDate.month)) {
            daysCount++;
        }
        for (int i = firstDate.year + 1; i <= secondDate.year - 1; i++) {
            if (isLeapYear(i, 2)) {
                daysCount++;
            }
        }
        return daysCount;
    }

    public static boolean isLeapYear(int year, int month) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            if (month < 3) {
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
        int month = Integer.parseInt(input.substring(0, 2));
        int day = Integer.parseInt(input.substring(3, 5));
        if (day > daysInMonth.get(month - 1)) {
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