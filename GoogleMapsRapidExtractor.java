public class GoogleMapsRapidExtractor {

   // Given symbol, get HTML
    private static String readHTML(String origin, String destination) {
        In page = new In("https://maps.googleapis.com/maps/api/distancematrix/xml?origins="
                             + origin + "&destinations=" + destination);
        String html = page.readAll();
        
        String distanceAndTime = convert(html, origin, destination);
        return distanceAndTime;
    }
    
    private static String convert(String wholeText, String origin, String destination) {
        
        // strings that indicate what surrounds relevant text we need
        // which is in this case the distance and time
        String delimiterBeg = "<text>";
        String delimiterEnd = "</text>";
        
        // finds the index of where the relevant text markers begin for time
        int timeIndexBeg = wholeText.indexOf(delimiterBeg) + delimiterBeg.length();
        int timeIndexEnd = wholeText.indexOf(delimiterEnd);
        
        // finds the index of where the relevant text markers begin for distance
        int distIndexBeg = wholeText.indexOf(delimiterBeg, timeIndexBeg + 1) + delimiterBeg.length();
        int distIndexEnd = wholeText.indexOf(delimiterEnd, timeIndexEnd + 1);
        
        
        String time;
        String distance;
        
        if (timeIndexBeg < 0 || timeIndexEnd < 0)
        {
            time = "ERROR";
            distance = "ERROR";
        }
        else
        {
            time = wholeText.substring(timeIndexBeg,timeIndexEnd);
            distance = wholeText.substring (distIndexBeg, distIndexEnd);
        }

        return origin + ";" + destination + ";" + time + ";" + distance;
    }
    
    public static void main(String[] args) {
        
        System.out.println("Origin;Location;Time;Distance");
        
        while (!StdIn.isEmpty())
        {
            String origin = StdIn.readString();
            String destination = StdIn.readString();
            System.out.println(readHTML(origin, destination));
        }
    }
}