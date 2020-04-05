/* Name: Sai Manogna Pentyala
Andrew: spentyal
Task: Project 1 Task 2a*/
package com.andrew.spentyal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Model class to do the processing necessary for the servlet - web scraping the zoo site to get the
 * bird names, bird images and the photographer name
 */
public class BirdProcessingModel {

    /**
     * hits the zoo site and gets the names of all the birds
     **/
    public List<String> getBirdTotalList() throws Exception {

        //list that contains the names of all the birds
        List<String> birdList = new ArrayList<>();

        //connecting to the zoo site to get the html page content
        Document document;
        try {
            document = Jsoup.connect("https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/").get();
        } catch (Exception e) {
            throw new Exception("error");
        }
        Element formElement = document.getElementById("pix");

        //scraping based on the dropdown list - using the option tag
        Elements inputElements = formElement.getElementsByTag("option");

        for (Element inputElement : inputElements) {
            String value = inputElement.attr("value");
            // adding the name of the birds in the list, which has been retrieved based on the value attribute
            birdList.add(value);
        }

        return birdList;
    }

    /**
     * creates a sublist of at the most 10 birds based on the input bird chosen
     **/
    public List<String> getBirdSubTotalList(String inputBird, List<String> birdList) {

        //list of bird names(at the most 10) that contain the inputBird name
        List<String> birdSubList = new ArrayList<>();
        int count = 0;

        //to make it random, each time the user enters the same bird
        Collections.shuffle(birdList);

        for (String s : birdList) {
            if (s.toLowerCase().contains(inputBird.toLowerCase())) {
                birdSubList.add(s);
                count++;
                if (count >= 10) {
                    break;
                }
            }
        }

        Collections.sort(birdSubList);
        return birdSubList;
    }

    /**
     * hit the zoo site with the bird name appended to the url, so as to fetch the image and the photographer
     **/
    public List<String> getBirdResultList(String birdName) throws Exception {

        //list of two values - image url and the photographer name
        List<String> birdResultList = new ArrayList<>();

        String url = "https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?";

        //to handle apostrophe
        String actualBirdName = birdName.replace(" '", "%27");
        //to handle space
        actualBirdName = actualBirdName.replace(" ", "+");

        String actualUrl = url + "pix=" + actualBirdName;

        //connecting to the page which contains the images and the photographers of the bird chosen
        Document document;

        try {
            document = Jsoup.connect(actualUrl).get();
        } catch (Exception e) {
            throw new Exception("error");
        }

        Element formElement = document.getElementById("picgrid");
        //scraping based on the img tag for the image url
        Elements inputImgElements = formElement.getElementsByTag("img");

        int size = inputImgElements.size();
        Random random = new Random();
        int randomNo = 0;
        if (size <= 20) {
            randomNo = random.nextInt(size);
        } else {
            randomNo = random.nextInt(20);
        }

        //scraping based on the img src attribute for the image url - getting a random image url based on randomNo
        String src = inputImgElements.get(randomNo).attr("src");
        //scraping based on the anchor tag for the photographer name - getting a random name based on randomNo
        Element link = formElement.select("a").get(randomNo);
        String photographerName = link.text();

        //adding the image url
        birdResultList.add(src);
        //adding the photographer name
        birdResultList.add(photographerName.replace("©", ""));

        return birdResultList;
    }
}
