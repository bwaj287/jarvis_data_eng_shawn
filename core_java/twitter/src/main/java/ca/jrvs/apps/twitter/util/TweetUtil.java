package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;


public class TweetUtil {

  public static Tweet buildTweet(String text, double longitude, double latitude) {

    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new double[]{longitude, latitude});
    coordinates.setType("Point");

    Tweet tweet = new Tweet();
    tweet.setCoordinates(coordinates);
    tweet.setText(text);
    return tweet;

  }

}