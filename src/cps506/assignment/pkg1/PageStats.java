/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cps506.assignment.pkg1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author minim_000
 */
public class PageStats implements Runnable {

    private static int tagSize;
    private final WebStats w;
    private static Tag tagCount[] = new Tag[100];
    private final int maxPages, maxPath, path;
    private int pages, visitedIndex;
    String visited[];
    String URL;

    public PageStats(String MyURL, int currentPath, WebStats w, int vi, String v[]) {
        maxPages = 10;
        pages = 0;
        maxPath = 3; //fix this shit
        path = currentPath;
        URL = MyURL;
        this.w = w;
        tagSize = 0;
        visited = v;
        visitedIndex = vi;
    }

    public PageStats(int MyPages, int MyPath, String MyURL, int currentPath, WebStats w, int vi, String v[]) {
        maxPages = MyPages;
        pages = 0;
        maxPath = MyPath;
        path = currentPath;
        URL = MyURL;
        this.w = w;
        tagSize = 0;
        visited = v;
        visitedIndex = vi;
    }

    public void checkTag(String s) {
        w.checkTag(s);
        int x;
        for (x = 0; x < tagSize; x++) {
            if (tagCount[x].getS().equalsIgnoreCase(s)) {
                tagCount[x].setI();
                break;
            }
        }
        if (x == tagSize) {
            tagCount[x] = new Tag(s, 1);
            tagSize++;
        }
    }

    public void finished() {
        for (int x = 0; x < tagSize; x++){
            for (int y = 1; y < tagSize - x; y++){
                if (tagCount[y-1].getS().compareTo(tagCount[y].getS()) > 0){
                    Tag temp = tagCount[y-1];
                    tagCount[y-1] = tagCount[y];
                    tagCount[y] = temp;
                }
            }
        }
        System.out.println();
        System.out.println("Statistics: " + URL);
        for (int x = 0; x < tagSize; x++) {
            System.out.println(tagCount[x].getS() + ": " + tagCount[x].getI());
        }
    }

    @Override
    public void run() {
        PageStats ps[] = new PageStats[maxPages];
        visited[visitedIndex] = URL;
        int index1;
        int index2;
        try {
            URL website = new URL(URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                index1 = inputLine.indexOf('<', 0);
                if (inputLine.contains("<")) {
                    while (index1 >= 0) {
                        if (inputLine.charAt(index1 + 1) == '!' || inputLine.charAt(index1 + 1) == '/') {
                            index1 = -1;
                            continue;
                        }
                        index2 = Math.min(inputLine.indexOf(' ', index1), inputLine.indexOf('>', index1));
                        if (index2 == -1) {
                            index2 = Math.max(inputLine.indexOf(' ', index1), inputLine.indexOf('>', index1));
                        }
                        if (index1 != -1 && index2 != -1) {
                            checkTag((inputLine.substring(index1 + 1, index2)));
                        }
                        index1 = inputLine.indexOf('<', index1 + 1);
                    }
                }
                if (inputLine.contains("a href=")) {
                    index1 = inputLine.indexOf("a href=") + 8;
                    index2 = inputLine.indexOf("\"", index1);
                    if (index2 != -1) {
                        String URL2 = inputLine.substring(index1, index2);
                        boolean visit = false;
                        for (int x = 0; x < visitedIndex; x++){
                                if (visited[x].equalsIgnoreCase(URL2))
                                    visit = true;
                            }
                        if (URL2.contains("http://") && pages < maxPages && path < maxPath && !visit) {
                            visitedIndex++;
                            visited[visitedIndex] = URL2;
                            ps[pages] = new PageStats(maxPages, maxPath, URL2, path+1, w, visitedIndex, visited);
                            ps[pages].run();
                            pages++;
                        }
                    }
                }
            }
        } catch (MalformedURLException mue) {
        } catch (IOException ioe) {
        }
        finished();
    }
}
