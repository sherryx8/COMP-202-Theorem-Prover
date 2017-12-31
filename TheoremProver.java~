import java.io.*;
import java.util.*;


public class TheoremProver{
  
  PropositionalParser p = new PropositionalParser();
  
  
  // This function uses the prove function to find the string result
  public String getResult(String s){
    String x = p.getresult(s);
    // if string is not a formula
    if(p.parse(s) == 0){
      x += "I told you, " + s + " is not a formula.";
    }else{ // find if string is satisfiable or not
      x += s + " is ";
      if(prove(s)){
        x += "satisifiable.";
      }else{
        x += "not satisfiable.";
      }  
    }
    return x;
  }
  
  
  
  // Given a formula, determines if the formula satisfiable or not
  public boolean prove(String s){
    
    if (p.parse(s) == 0){
      // System.out.println("is not a formula");
      return false;
    }
    
    Queue<TPnode> queue = new LinkedList<TPnode>();
    TPnode head = new TPnode(s);
    queue.add(head);
    boolean satisfiable = false;
    
    // while there are still nodes to be expanded
    while (!queue.isEmpty()){
      TPnode next = queue.remove();
      // System.out.println("expanded " + next.phrase);
      expand(next, queue); 
    }
    
    // check all branches for an open branch
    return checkOpen(head);
  }
  
  
  
  // Expands a node, adding children to tree and queue
  public boolean expand(TPnode n, Queue<TPnode> q){
    int k = findFormulaType(n.phrase);
    // System.out.println("formula type for " + n.phrase + " is " + k);
    int o = p.findOperator(n.phrase);
    String x = simplifyNegation(n.phrase);
    if(p.isNegation(x)){
      x = x.substring(1);
    }
    String a = "";
    String b = "";
    // if not a proposition, split and add children to tree
    if(o > 0){
      a = p.splitBinaryFormulaA(x);
      b = p.splitBinaryFormulaB(x);
      TPnode aNode = new TPnode(a);
      TPnode bNode = new TPnode(b);
      
      // add expanded children to correct locations of tree
      switch(k){
        case (0): break; 
        case (1): addAlpha(n, aNode, bNode, q); break;
        case (2): addAlpha(n, negate(aNode), negate(bNode), q); break;
        case (3): addAlpha(n, aNode, negate(bNode), q); break;
        case (4): addBeta(n, aNode, bNode, q); break;
        case (5): addBeta(n, negate(aNode), negate(bNode), q); break;
        case (6): addBeta(n, negate(aNode), bNode, q); break;
      }
    }
    return false;
  }
  
  
  
  
  // Given node check if there are any open branches
  public boolean checkOpen(TPnode node){
    if (node == null){
      return false;
    }
    // if leaf node, go up tree
    if(node.left == null && node.right == null){
      // list of the propositions need to check for
      ArrayList<String> props = new ArrayList<String>();    
      String x = findNegation(node.phrase);
      props.add(x);
      // System.out.println("checking for " + x);
      TPnode curr = node;
      
      while(curr.parent != null){      
        String phrase = curr.phrase;
        // check for contradictions in branch
        for(int i = 0; i < props.size(); i++){
          if(phrase.equals(props.get(i))){ //if negation found
            // System.out.println("found " + x + ", branch is closed");
            return false;
          }
        }       
        // if p or the negation of p is a proposition, make sure to check for it too by adding it to list
        if (p.isProposition(phrase) || p.isProposition(findNegation(phrase))){
          // System.out.println("adding " + findNegation(phrase) + " to list");
          props.add(findNegation(phrase));
        }      
        curr = curr.parent;
      }
      // if no contraditions found, the branch is open
      return true;
    }else{  
      return checkOpen(node.left) || checkOpen(node.right);
    }
  }
  
  
  // 0 = proposition 1 = AND 2 = -OR 3 = -IMP
  // 4 = OR 5 = -AND 6 = IMP
  public int findFormulaType(String s){
    // if there is no operator, then it is a proposition (or -prop)
    int operator = p.findOperator(s);
    if(operator == -1){return 0;}
    char o = s.charAt(operator);
    s = simplifyNegation(s);
    // if there is an operator determine what kind
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
  
  
  
  // Expands an alpha formula by placing children in correct leaves
  public void addAlpha(TPnode n, TPnode aNode, TPnode bNode, Queue<TPnode> q){
    TPnode curr = n;
    if (n == null){
      return;
    }
    else if(curr.left == null && curr.right == null){ //if leaf node
      q.add(aNode);
      q.add(bNode);
      aNode.parent = curr;
      curr.left = aNode;
      bNode.parent = aNode;
      aNode.left = bNode;
      //System.out.println("addalpha to " + curr.phrase + " with " + aNode.phrase + " and " + bNode.phrase);
    }else{
      addAlpha(curr.left, new TPnode(aNode.phrase), new TPnode(bNode.phrase), q);
      addAlpha(curr.right, new TPnode(aNode.phrase), new TPnode(bNode.phrase), q); 
    }
    return;
  }
  
  // Expands an beta formula by placing children in correct leaves
  public void addBeta(TPnode n, TPnode aNode, TPnode bNode, Queue<TPnode> q){
    if(n == null){
      return;
    }else if(n.left == null && n.right == null){ //if leaf node
      q.add(aNode);
      q.add(bNode);
      aNode.parent = n;
      n.left = aNode;
      bNode.parent = n;
      n.right = bNode;
      //System.out.println("addbeta to " + n.phrase + " with " + aNode.phrase + " and " + bNode.phrase); 
      return;
    }else{
      addBeta(n.left, new TPnode(aNode.phrase), new TPnode(bNode.phrase), q);
      addBeta(n.right, new TPnode(aNode.phrase), new TPnode(bNode.phrase), q);  
    }
  }
  
  
  
  // Given a string, simplify negation by removing extra '-'
  public String simplifyNegation(String s){
    while(s.length() >= 2){
      if(s.charAt(0) == '-' && s.charAt(1) == '-'){
        return s.substring(2);
      }else{ // if doesn't start with "--"
        break;
      }
    }
    return s;
  }
  
  // Given a node, negate the phrase inside
  public TPnode negate(TPnode tp){
    String s = tp.phrase;
    tp.phrase = findNegation(s);
    return tp;
  }
  
  // Given a string, find negation of the string
  public String findNegation(String s){
    String ss = "-" + s;
    return simplifyNegation(ss);
  }
  
  public static void main(String args[]){
    
    TheoremProver tp = new TheoremProver();
    
    // if inputs given through args
    if(args.length > 0){ 
      for(int i = 0; i < args.length; i++){
        String s = args[i];
        System.out.println(tp.getResult(s));
      }
    }else{ 
      try{ // else try using input.txt file
        File input = new File("input.txt");
        Scanner inputScanner = new Scanner(input);
        PrintWriter printWriter = new PrintWriter(new FileWriter("output.txt"));  
        while(inputScanner.hasNext()){
          String g = inputScanner.nextLine();
          String result = tp.getResult(g);
          printWriter.println(result);
          System.out.println(result);
        }
        printWriter.close();
        inputScanner.close();
      }
      catch(Exception e){ // use defaults if input file cannot be found
        System.out.println(e);
        System.out.println(" Input file not found, using defaults:");
        String[] defaults = {"(pvq)", "((p>q)v-p)", "-(p^q)v-(p>q)", "((p^--q)>(-qv-p))", "---p", "((pvq)>(-p>--q))", "-(p>(q>p))", "(pv-q)", "-(pv-p)", "((p^--q)>(-qv-p))", "-pvq"};
        for (int j = 0; j < defaults.length; j++){
          System.out.println(tp.getResult(defaults[j]));
        }
      }
    }
  }
  
}


