/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cps506.assignment.pkg1;
/**
 *
 * @author minim_000
 */
public class WebStats {
    
    private static int tagSize;
    private final static Tag tagCount[] = new Tag[100];
    String URL;
    
    public WebStats(){
        tagSize = 0;
    }

    public static void main(String args[]) {
        WebStats w = new WebStats();
        PageStats p;
        if (args.length == 5){
            String visited[] = new String[Integer.parseInt(args[1]) * Integer.parseInt(args[3])];
            p = new PageStats(Integer.parseInt(args[1]), Integer.parseInt(args[3]), args[4], 1, w, 0, visited);
            p.run();
        }
        else if (args.length == 1){
            String visited[] = new String[10*3];
            p = new PageStats(args[0], 1, w, 0, visited);
            p.run();
        }
        else{
            System.out.println("Invalid number of arguments.");
            System.exit(1);
        }
        w.finished();
    }
    
    public synchronized void checkTag(String s){
        int x;
        for (x = 0; x < tagSize; x++){
            if (tagCount[x].getS().equalsIgnoreCase(s)){
                tagCount[x].setI();
                break;
            }
        }
        if (x == tagSize && x < tagCount.length){
            tagCount[x] = new Tag(s, 1);
            tagSize++;
        }
    }
    
    public void finished(){
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
        System.out.println("Statistics Total:");
        for (int x = 0; x < tagSize; x++){
            System.out.println(tagCount[x].getS() + ": " + tagCount[x].getI());
        }
    }
}