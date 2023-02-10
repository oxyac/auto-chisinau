package oxyac.shopping.data.parsers;

public interface CarParser {

    void parse();

    void parseUri();

    String getUriToParse();

    void parseWebsiteData();

    String parseFavicon();

}
