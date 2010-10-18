package com.appengine.dockstats;

/**
 * <p>
 * Html Encoder
 * </p>
 *
 * @author Sachin Handiekar
 * @version 1.0
 */
public final class HtmlEncoder {

    /**
     * Converts a string into a HTML safe format.
     *
     * @param s
     *           the string to encode
     * @return the encoded string
     */
    public static String encode(String s) {
        char[]       htmlChars   = s.toCharArray();
        StringBuffer encodedHtml = new StringBuffer();

        for (int i = 0; i < htmlChars.length; i++) {
            switch (htmlChars[i]) {
            case '<' :
                encodedHtml.append("&lt;");

                break;

            case '>' :
                encodedHtml.append("&gt;");

                break;

            case '&' :
                encodedHtml.append("&amp;");

                break;

            case '\'' :
                encodedHtml.append("&#39;");

                break;

            case '"' :
                encodedHtml.append("&quot;");

                break;

            case '\\' :
                encodedHtml.append("&#92;");

                break;

            case (char) 133 :
                encodedHtml.append("&#133;");

                break;

            default :
                encodedHtml.append(htmlChars[i]);

                break;
            }
        }

        return encodedHtml.toString();
    }
}
