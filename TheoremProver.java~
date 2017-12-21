import java.io.*;
import java.util.*;
//import PropositionalParser.*;

//alpha 
//p^q
//--p
//-(pvq)
//-(p>q)

//beta
//(pvq)
//-(p^q)
//(p>q)


public class TheoremProver{
  
  PropositionalParser p = new PropositionalParser();
  
  public boolean prove(String s){
    Queue<TPnode> queue = new LinkedList<TPnode>();
    TPnode head = new TPNode(s);
    queue.push(head);
    
    //while there are still nodes to be expanded
    while (!queue.isEmpty()){
      //expand 
      //determine if alpha or beta formula
      TPNode next = queue.pop();
      int ab = isAlphaOrBeta(next.phrase);
      
    }
    
    //if all nodes expanded and no open branches found (?)
    return false;
  }
  
  //ab = if alpha or beta
  public void expand(TPnode n, int ab){
    int k = p.parse(n.phrase);
    //if negation, then simplify
    //if proposition, then finish
    //if binary formula, do beta or alpha split
  }
  
  //0 = idk 1 = alpha, 2 = beta
  public int isAlphaOrBeta(Strings s){
    int x = 0;
    int operator = p.findOperator(s);
    //if there is no operator, then its some sort of proposition?
    if(operator == -1){
      return 0;
    }
    char o = s.charAt(operator);
    if (p.isNegation(s)){
      if (o == 'v'|| o == '>'){
        return 1;
      }else if(o == '^'){
        return 2;
      }
    }else{
      if(o == 'v' || o == '>'){
        return 2;
      }else if( o == '^'){
        return 1;
      }
    }
    
  }
  
  public static void main(String args[]){
    
    if(args.length > 0){
      for(int i = 0; i < args.length; i++){
        String s = args[i];
      }
    }else{
      try{}
      catch(Exception e){}
    }
  }
  
  
  
}

public class TPnode{
  boolean expanded;
  String phrase;
  TPnode parent;
  TPnode left;
  TPnode right;
  
  public TPnode(String s){
    this.expanded = false;
    this.phrase = s;
    this.left = null;
    this.right = null;
    this.parent = null;
  }
  
  
  
}