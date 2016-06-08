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
public class Tag {
    
    private String s;
    private int i;
    
    public Tag(String s, int i){
        this.s = s;
        this.i = i;
    }
    
    public String getS(){
        return s;
    }
    
    public int getI(){
        return i;
    }
    
    public void setI(){
        i = i + 1;
    }
}
