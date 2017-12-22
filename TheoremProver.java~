import java.io.*;
import java.util.*;
//import PropositionalParser.*;
//import COMP-202-Theorem-Prover.*;

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
    if (p.parse(s) == 0){
      System.out.println("is not a formula");
      return false;
    }
    Queue<TPnode> queue = new LinkedList<TPnode>();
    TPnode head = new TPnode(s);
    queue.add(head);
    boolean satisfiable = false;
    
    //while there are still nodes to be expanded
    while (!queue.isEmpty()){
      //expand 
      //determine if alpha or beta formula
      TPnode next = queue.remove();
      
      System.out.println("expanded " + next.phrase);
      expand(next, queue);
//      if (expand(next, queue)){
//        System.out.println(next.phrase + " is open");
//        
//        satisfiable = true; 
//      }
      next.expanded = true;
      //int ab = isAlphaOrBeta(next.phrase);
      
    }
    //check all branches for an open branch
    return checkOpen(head);
  }
  
  //ab = if alpha or beta
  public boolean expand(TPnode n, Queue<TPnode> q){
    int k = findFormulaType(n.phrase);
    System.out.println("formula type for " + n.phrase + " is " + k);
    int o = p.findOperator(n.phrase);
    //split after removing any negations
    
    String x = simplifyNegation(n.phrase);
    if(p.isNegation(x)){
      x = x.substring(1);
    }
    
    //System.out.println("operator at " + o);
    String a = "";
    String b = "";
    if(o > 0){
      a = p.splitBinaryFormulaA(x);
      b = p.splitBinaryFormulaB(x);
    }
    switch(k){
      case (0): break; 
      case (1): addAlpha(n, a, b, q); break;
      case (2): addAlpha(n, negate(a), negate(b), q); break;
      case (3): addAlpha(n, a, negate(b), q); break;
      case (4): addBeta(n, a, b, q); break;
      case (5): addBeta(n, negate(a), negate(b), q); break;
      case (6): addBeta(n, negate(a), b, q); break;
    }
    return false;
    
    //if negation, then simplify
    //if proposition, then finish
    //if binary formula, do beta or alpha split
  }
  
  public String negate(String s){
   String ss = "-" + s;
   return simplifyNegation(ss);
  }
  //given node check if there are any open branches
  public boolean checkOpen(TPnode node){
    if (node == null){
      return false;
    }
    //if leaf node, go up tree
    if(node.left == null && node.right == null){
      ArrayList<String> props = new ArrayList<String>();
      
      String x = findNegation(node.phrase);
      System.out.println("checking for " + x);
      props.add(x);
      
      TPnode curr = node;
      while(curr.parent != null){
        
        String phrase = curr.phrase;
        
        for(int i = 0; i < props.size(); i++){
          if(phrase.equals(props.get(i))){ //if negation found
            System.out.println("found " + x + ", branch is closed");
            return false;
          }
        }
        
        if (p.isProposition(phrase) || p.isProposition(negate(phrase))){ //add to the list if new prop found
          System.out.println("adding -" + phrase + " to list");
          props.add(findNegation(phrase));
        }
        
        curr = curr.parent;
      }
      
      //if no contraditions found
      return true;
    }else{
      
      return checkOpen(node.left) || checkOpen(node.right);
    }
  }
  
  public String simplifyNegation(String s){
    while(s.length() > 2){
      if(s.charAt(0) == '-' && s.charAt(1) == '-'){
        s = s.substring(2);
      }else{ //if doesn't start with "--"
        break;
      }
    }
    return s;
  }
  
  public String findNegation(String s){
    
    String ss = "-" + s;
    
    return simplifyNegation(ss);
  }
  
  //expands an alpha formula
  //WRONG -> NEEDS TO ADD TO ALL LEAVES
  public void addAlpha(TPnode n, String a, String b, Queue<TPnode> q){
    TPnode aNode = new TPnode(a);
    TPnode bNode = new TPnode(b);
     q.add(aNode);
      q.add(bNode);
    TPnode curr = n;
    if (n == null){
      return;
    }
    if(curr.left == null && curr.right == null){ //if leaf node
      aNode.parent = curr;
      curr.left = aNode;
      bNode.parent = aNode;
      aNode.left = bNode;
      System.out.println("addalpha to " + curr.phrase + " with " + a + " and " + b);
      //return;
    }else{
      addAlpha(curr.left, a, b, q);
      addAlpha(curr.right, a, b, q);
     
    }
    
    return;
  }
  
  //expands a beta formula
  public void addBeta(TPnode n, String a, String b, Queue<TPnode> q){
    TPnode aNode = new TPnode(a);
    TPnode bNode = new TPnode(b);
     q.add(aNode);
    q.add(bNode);
    
    TPnode curr = n;
    
    if(n == null){
      return;
    }
    if(curr.left == null && curr.right == null){ //if leaf node
      aNode.parent = curr;
      curr.left = aNode;
      bNode.parent = curr;
      curr.right = bNode;
      System.out.println("addbeta to " + curr.phrase + " with " + a + " and " + b);
      //return;
    }else{
      addBeta(curr.left, a, b, q);
      addBeta(curr.right, a, b, q);
      
    }
   
  }
  
  
  
  //0 = proposition 1 = AND 2 = -OR 3 = -IMP
  //4 = OR 5 = -AND 6 = IMP
  public int findFormulaType(String s){
    
    int operator = p.findOperator(s);
    
    //if there is no operator, then its some sort of proposition?
    if(operator == -1){
      return 0;
    }
    char o = s.charAt(operator);
    s = simplifyNegation(s);
    
    if (p.isNegation(s)){
      if (o == 'v'){
        return 2;
      }
      else if(o == '>'){
        return 3;
      }else if(o == '^'){
        return 5;
      }
    }else{
      if (o == 'v'){
        return 4;
      }
      else if(o == '>'){
        return 6;
      }else if(o == '^'){
        return 1;
      }
    }
    return 0;
    
  }
  
  public static void main(String args[]){
    
    TheoremProver tp = new TheoremProver();
    
    if(args.length > 0){
      for(int i = 0; i < args.length; i++){
        String s = args[i];
        System.out.println(tp.prove(s));
      }
    }else{
      try{}
      catch(Exception e){}
    }
  }
  
  
  
}


