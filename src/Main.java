import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a date in MM/DD/YYYYY format");
        String input;
        while (true) {
            input = scanner.nextLine();
            Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{4}$");
            Matcher matcher = pattern.matcher(input);
            boolean ifMatch = matcher.matches();
            if (ifMatch) {
                break;
            } else {
                System.out.println("Invalid date, please enter in MM/DD/YYYY format");
            }
        }
        List<String> date  = List.of(input.split("/"));
        List<Integer> dateInteger = date.stream().map(Integer::parseInt).toList();
        int year = dateInteger.get(2) % 100;
        int yearCode = (year + (year / 4)) % 7;
        int[] monthCode = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
        Map<Integer, Integer> centuryCode = new HashMap<>();
        centuryCode.put(1700 , 4);
        centuryCode.put(1800 , 2);
        centuryCode.put(1900 , 0);
        centuryCode.put(2000 , 6);
        centuryCode.put(2100 , 4);
        centuryCode.put(2200 , 2);
        centuryCode.put(2300 , 0);
        int leapYearCode = 0;
        if ((dateInteger.get(2) % 4 == 0 && dateInteger.get(2) % 100 != 0) || dateInteger.get(2) % 400 == 0) {
            if (dateInteger.get(0) == 1 || dateInteger.get(0) == 2) {
                leapYearCode = 1;
            }
        }
        int findDay = yearCode + monthCode[dateInteger.get(0) - 1] + dateInteger.get(1) - leapYearCode;
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
}