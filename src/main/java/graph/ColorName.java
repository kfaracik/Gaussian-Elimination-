package graph;

import java.util.HashSet;
import java.util.Set;

public class ColorName {

    private Set<String> availableColorMap;

    ColorName() {
        availableColorMap = colorMap;
    }

    public String getColor() {
        if (availableColorMap.isEmpty()) {
            availableColorMap = colorMap;
        }

        String colorName = availableColorMap.iterator().next();
        availableColorMap.remove(availableColorMap.iterator().next());
        return colorName;
    }

    private static final Set<String> colorMap = new HashSet<String>() {
        {
            add("gold");
            add("cyan");
            add("aliceblue");
            add("coral4");
            add("antiquewhite");
            add("antiquewhite4");
            add("aquamarine");
            add("aquamarine4");
            add("azure3");
            add("azure4");
            add("beige");
            add("bisque2");
            add("bisque4");
            add("blueviolet");
            add("brown");
            add("brown1");
            add("burlywood4");
            add("cadetblue");
            add("cadetblue1");
            add("cadetblue4");
            add("chartreuse");
            add("chartreuse2");
            add("chartreuse4");
            add("chocolate");
            add("chocolate1");
            add("chocolate2");
            add("cornsilk4");
            add("cornflowerblue");
            add("cyan4");
            add("darkgoldenrod1");
            add("darkgoldenrod");
            add("darkgoldenrod3");
            add("darkgreen");
            add("darkkhaki");
            add("darkolivegreen");
            add("darkolivegreen1");
            add("darkolivegreen3");
            add("darkorchid");
            add("darkorchid3");
            add("darksalmon");
            add("darkseagreen");
            add("darkslategray");
            add("darkslateblue");
            add("darkslategray3");
            add("deeppink");
            add("deepskyblue1");
            add("dodgerblue");
            add("deepskyblue4");
            add("dodgerblue2");
            add("dodgerblue4");
            add("firebrick");
            add("firebrick3");
            add("ghostwhite");
            add("gold2");
            add("gold3");
            add("gold4");
            add("goldenrod");
            add("goldenrod4");
        }
    };
}