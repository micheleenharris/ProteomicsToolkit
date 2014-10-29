import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.math.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class IsotopeServlet extends HttpServlet
{

  // ************************** SERVLET CODE **************************

 /*
  *  CONSTANTS
  */
 
  private static final int MAX_PEPTIDES = 20;

  //  String containing all 1-letter codes for the 2O amino acids
  private String aminoAcidStr = "ARNDCEQGHILKMFPSTWYV";

  //  Array of strings which contain the number of C's, H's, N's, O's, and S's
  //  This array cooresponds to the aminoAcidStr by index (i.e. "C3H5N1O1S0" is Alanine or A)
  private String [] elements = {"CCCHHHHHNO",  		//  ALANINE "C3H5N1O1S0"
                        "CCCCCCHHHHHHHHHHHHNNNNO", 	//  ARGININE "C6H12N4O1S0"
                        "CCCCHHHHHHNNOO",  		//  ASPARAGINE "C4H6N2O2S0"
                        "CCCCHHHHHNOOO",  		//  ASPARTIC ACID "C4H5N1O3S0"
                        "CCCHHHHHNOS",  		//  CYSTEINE "C3H5N1O1S1"
                        "CCCCCHHHHHHHNOOO",  		//  GLUTAMIC ACID "C5H7N1O3S0"
                        "CCCCCHHHHHHHHNNOO",  		//  GLUTAMINE "C5H8N2O2S0"
                        "CCHHHNO",  			//  GLYCINE "C2H3N1O1S0"
                        "CCCCCCHHHHHHHNNNO",		//  HISTIDINE "C6H7N3O1S0"
                        "CCCCCCHHHHHHHHHHHNO",		//  ISOLEUCINE "C6H11N1O1S0"
                        "CCCCCCHHHHHHHHHHHNO",		//  LEUCINE "C6H11N1O1S0"
                        "CCCCCCHHHHHHHHHHHHNNO", 	//  LYSINE "C6H12N2O1S0"
                        "CCCCCHHHHHHHHHNOS",  		//  METHIONINE "C5H9N1O1S1"
                        "CCCCCCCCCHHHHHHHHHNO",  	//  PHENYLALANINE "C9H9N1O1S0"
                        "CCCCCHHHHHHHNO",  		//  PROLINE "C5H7N1O1S0"
                        "CCCHHHHHNOO",  		//  SERINE "C3H5N1O2S0"
                        "CCCCHHHHHHHNOO",  		//  THREONINE "C4H7N1O2S0"
                        "CCCCCCCCCCCHHHHHHHHHHNNO",	//  TRYPTOPHAN "C11H10N2O1S0"
                        "CCCCCCCCCHHHHHHHHHNOO",  	//  TYROSINE "C9H9N1O2S0"
                        "CCCCCHHHHHHHHHNO"};  		//  VALINE "C5H9N1O1S0"

    //  String containing the atoms and amino acid 1-letter names plus the rest of the alpha code
    //  This cooresponds to the avgWeightDbl and monoWeightDbl array by index (except for extra alpha's)
    private String weightStr = "hocnpsGASPVTCLIXNOBDQKZEMHFRYWabdefgijklmqrtuvwxyzJU";


    //  An array of the average weights of the atoms (c,h,n,o,s,p) and amino acids
    private double [] avgWeightDbl = {
        1.007940000,
        15.99940000,
        12.01070000,
        14.00674000,
        30.97376100,
        32.06600000,
        57.05192000,
        71.07880000,
        87.07820000,
        97.11668000,
        99.13256000,
        101.1050800,
        103.1388000,
        113.1594400,
        113.1594400,
        113.1594400,
        114.1038400,
        114.1472000,
        114.5962200,
        115.0886000,
        128.1307200,
        128.1740800,
        128.6231000,
        129.1154800,
        131.1925600,
        137.1410800,
        147.1765600,
        156.1874800,
        163.1759600,
        186.2132000};

    //  An array of the monoisotopic weights of the atoms (c,h,n,o,s,p) and amino acids
    private double [] monoWeightDbl = {
        1.007825000,
        15.99491460,
        12.00000000,
        14.00307400,
        30.97376330,
        31.97207180,
        57.02146360,
        71.03711360,
        87.03202820,
        97.05276360,
        99.06841360,
        101.0476782,
        103.0091854,
        113.0840636,
        113.0840636,
        113.0840636,
        114.0429272,
        114.0793126,
        114.5349350,
        115.0269428,
        128.0585772,
        128.0949626,
        128.5505850,
        129.0425928,
        131.0404854,
        137.0589116,
        147.0684136,
        156.1011106,
        163.0633282,
        186.0793126};





 /*
  *  VARIABLES
  */

  private String sequence;
  private Vector allSequences = new Vector();


  private PrintWriter out;

  private int invalidInput = 0,
              numSequences;

  //  This function is the main servlet function which communicates with the server
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {

  res.setContentType ("text/html");
  out = res.getWriter();

  /*
   *  GET USER ENTERED SEQUENCE
   */

  //  retrieves the user entered protein sequence
  sequence = req.getParameter("sequence");

  parseInput(sequence);

  /*
   *  CREATION OF HTML DOC FOR OUTPUT
   */


  out.println("<HTML>");
  out.println("<HEAD><TITLE>Output Page</TITLE></HEAD><BODY>");

  out.println("<CENTER><H3>Mass/Elemental Composition/Amino Acid Composition/Isotope Ratio Results</H3></CENTER>");

  if (allSequences.size() > 0)
  {
    //  format the output
    out.println("<FONT SIZE=+1>");

    for(int i = 0; i < allSequences.size(); i++)
    {
      out.println("Sequence: " + outputBy((String)allSequences.elementAt(i),50) + "<BR><BR>");

      Output((String)allSequences.elementAt(i));
     
      out.println("<HR>");
    }
  }
  else
  {
    if (invalidInput == 0)
      out.println("<B>Please enter a sequence.</B>");
    else // invalid input
      out.println("<B> Invalid amino acid sequence.</B>");
  }

  out.println("</BODY></HTML>");

  }

  /********************* HELPER METHODS *********************/ 

  /*
   *  OUTPUT FUNCTION -- calls all other helper methods, calculates, and outputs results in HTML form
   */
  
   public void Output(String seq)
   {
  
       StringBuffer output = new StringBuffer("");

       //  create table for tables and print it
       output.append("<TABLE CELLSPACING = 10><TR VALIGN = TOP>");

       //  print the mass table
       output.append("<TD>" + massCalc(seq) + "</TD>");

       //  print the elemental composition table
       output.append("<TD>" + elemCompCalc(seq) + "</TD></TR>");

       //  print the amino acid composition of peptide table
       output.append("<TR VALIGN = TOP><TD>" + aminoAcidCompCalc(seq) + "</TD>");
    
       //  print the isotope abundance data
       output.append("<TD>" + isotopeAbunCalc(seq) + "</TD></TR>");

       //  end the output table
       output.append("</TR></TABLE>");

       //  print all the output
       out.println(output);


   }


  /*
   *  OUTPUT BY ...
   */

  //  output sequence -- charPerLine indicates characters per line
  public String outputBy (String s, int charPerLine)
  {

    int count = 0;
    StringBuffer tempBuff = new StringBuffer("");
    for (int i = 0; i < s.length(); i++)
    {
      count++;
      if (count == charPerLine && i != (s.length()-1))
      {
        tempBuff.append("<BR>");
        count = 0;  // restart counter
      }
      tempBuff.append(s.charAt(i));
    }

    return tempBuff.toString();
  }



  /*
   *  MASS CALC --  calculates mass (mono and avg) and returns an HTML table as a string
   */

   private double massDbl, origMassDblMono, origMassDblAvg;
   private String completeSeq, massStr1, massStr2;

   public String massCalc(String seq)
   {

    massStr1 = "";
    massStr2 = "";
    massDbl = 0.0;
    origMassDblMono = 0.0;
    origMassDblAvg = 0.0;

    completeSeq = seq + "hoh";  //  add the terminal H and OH groups to the mass of peptides

    StringBuffer tableBuff = new StringBuffer("");


    /*  CREATE HTML TABLE */

    //  the start of the table and caption
    tableBuff.append("<TABLE BORDER WIDTH = 25%><CAPTION ALIGN=TOP><NOBR>Mass / Charge</NOBR></CAPTION>");

    //  mass heading
    tableBuff.append("<TR><TH></TH><TH COLSPAN=2>Mass</TH></TR>");

    //  mono and avg headings
    tableBuff.append("<TR><TH></TH><TH>Mono</TH><TH>Average</TH></TR>");


    //  monoisotopic weight

    massDbl = lookupWeight(completeSeq,0);
    origMassDblMono = massDbl;  //  saves the non-charged mono mass
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = lookupWeight(completeSeq,1);
    origMassDblAvg = massDbl;  //  saves the non-charged avg mass
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH>M</TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    //  +1 charge

    //  monoisotopic weight

    massDbl = origMassDblMono + lookupWeight("h",0);
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = origMassDblAvg + lookupWeight("h",1);
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH>M+H</TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");
 

    //  +2 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hh",0))/2.0;
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("h",1))/2.0;
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH>M+2H</TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    //  +3 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hhh",0))/3.0;
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("hhh",1))/3.0;
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH>M+3H</TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    tableBuff.append("</TABLE><BR><BR>");

     return tableBuff.toString();
   }


   /*
    *  ELEMENTAL COMPOSITION CALC --  calculates the elemental composition and returns an HTML table
    */

    int countC,
        countH,
        countN,
        countO,
        countS;

    public String elemCompCalc (String seq)
    {

      StringBuffer elemBuff = new StringBuffer("");
      int indexAA = 0;

      for (int i = 0; i < seq.length(); i++)
      {
        if (aminoAcidStr.indexOf( seq.charAt(i) ) != (-1) )  //  is valid amino acid
        {
          indexAA = aminoAcidStr.indexOf( seq.charAt(i) );
          elemBuff.append(elements[indexAA]);
        }
      }

      String elemStr = elemBuff.toString();

      countC = 0;
      countH = 0;
      countN = 0;
      countO = 0;
      countS = 0;

      for (int i = 0; i < elemStr.length(); i++)
      {
        if (elemStr.charAt(i) == 'C')
          countC++;
        if (elemStr.charAt(i) == 'H')
          countH++;
        if (elemStr.charAt(i) == 'O')
          countO++;
        if (elemStr.charAt(i) == 'N')
          countN++;
        if (elemStr.charAt(i) == 'S')
          countS++;
      }

      StringBuffer tableBuff = new StringBuffer("");

      /*  CREATE HTML TABLE */

      //  the start of the table and caption
      tableBuff.append("<TABLE BORDER WIDTH = 25%><CAPTION ALIGN=TOP><NOBR>Elemental Composition</NOBR></CAPTION>");

      //  the headings
      tableBuff.append("<TR><TH>Element</TH><TH>Occurences</TH></TR>");

      if (countC != 0)
      {
        tableBuff.append("<TR><TH>Carbon</TH>");
        tableBuff.append("<TD>" + String.valueOf(countC) + "</TD>");
      }

      if (countH != 0)
      {
        tableBuff.append("<TR><TH>Hydrogen</TH>");
        tableBuff.append("<TD>" + String.valueOf(countH) + "</TD>");
      }

      if (countN != 0)
      {
        tableBuff.append("<TR><TH>Nitrogen</TH>");
        tableBuff.append("<TD>" + String.valueOf(countH) + "</TD>");
      }

      if (countO != 0)
      {
        tableBuff.append("<TR><TH>Oxygen</TH>");
        tableBuff.append("<TD>" + String.valueOf(countO) + "</TD>");
      }


      if (countS != 0)
      {
        tableBuff.append("<TR><TH>Sulfur</TH>");
        tableBuff.append("<TD>" + String.valueOf(countS) + "</TD>");
      }

      tableBuff.append("</TABLE><BR><BR>");

      return tableBuff.toString();
    }



   /*
    *  AMINO ACID COMPOSITION CALC -- calculates the amino acid composition and returns an HTML table
    */


    private String compStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";  //  the amino acids in alphabetical order

    public String aminoAcidCompCalc (String seq)
    {

      int [] comp = new int [26];  //  holds the count of the twenty amino acids in aphabetical order

      //  determines the composition of the peptide
      for (int i = 0; i < seq.length(); i++)
      {
        if ( Character.isLetter( seq.charAt(i) ) )
          comp[seq.charAt(i) - 'A']++;
      }

      StringBuffer tableBuff = new StringBuffer("");
      
      /*  CREATE HTML TABLE */
 
      //  the start of the table and caption
      tableBuff.append("<TABLE BORDER WIDTH = 25%><CAPTION ALIGN=TOP><NOBR>Amino Acid Composition</NOBR></CAPTION>");

      //  the headings
      tableBuff.append("<TR><TH>Amino Acid</TH><TH>Occurences</TH><TH>Abundance(%)</TH></TR>");

      int occurences;
      double abundance;
      char AA;
      double len = seq.length();

      //  create data row for each amino acid in peptide
      for (int i = 0; i < 26; i++)
      {
        occurences = comp[i];
        abundance = (occurences / len) * 100;

        AA = compStr.charAt(i);
 
        if (occurences != 0)
        {
          tableBuff.append("<TR><TD>" + String.valueOf(AA) + "</TD>");
          tableBuff.append("<TD>" + String.valueOf(occurences) + "</TD>");
          tableBuff.append("<TD>" + roundFxn(abundance) + "</TD></TR>");
        }

      }

      tableBuff.append("</TABLE><BR><BR>");
 
      return tableBuff.toString();     

    }



  /*
   *  ISOTOPE ABUNDANCE CALC
   */

  double massPlusOne, massPlusTwo;
  String plusOneStr, plusTwoStr;

  //  Calculates the Isotope Abundance Ratio in the polypeptide
  public String isotopeAbunCalc (String s)
  {

    massPlusOne = (1.1 *countC) + (0.016 * countH) + (0.38 * countN);
    massPlusTwo = (Math.pow((1.1 * countC),2) / 200) + (Math.pow((0.016 * countH),2) / 200) + (Math.pow((0.38 * countN),2) / 200);

    plusOneStr = roundFxn(massPlusOne);
    plusTwoStr = roundFxn(massPlusTwo);

    StringBuffer tableBuff = new StringBuffer("");


    /*  CREATE HTML TABLE */

    //  the start of the table and caption
    tableBuff.append("<TABLE BORDER WIDTH = 25%><CAPTION ALIGN=TOP><NOBR>Isotope Ratio</NOBR></CAPTION>");

    //  the headings
    tableBuff.append("<TR><TH>Peak</TH><TH><NOBR>Relative Percent</NOBR><BR><NOBR>to M Peak</NOBR></TH></TR>");

    //  the isotope ratio data
    tableBuff.append("<TR><TH>M+1</TH><TD>" + plusOneStr + "</TD></TR>");
    tableBuff.append("<TR><TH>M+2</TH><TD>" + plusTwoStr + "</TD></TR>");

    //  the end of the table
    tableBuff.append("</TABLE><BR><BR>");

    return tableBuff.toString();
  }


  /*
   *  PARSE INPUT SEQUENCES
   */

  public void parseInput (String input)
  {
    invalidInput = 0;  //  reinitialize to OK

    //  the number of sequences
    numSequences = 0;

    //  clear allSequences
    allSequences.clear();

    //  the delimiter is the newline or carriage return
    StringTokenizer tok = new StringTokenizer (input, "\n");

    String tempStr = "";


    //  all sequences holds a maximum number of 20 sequences
    while( tok.hasMoreTokens() && numSequences <= MAX_PEPTIDES )
    {
      tempStr = ( ( tok.nextToken() ).toUpperCase() ).trim();
      StringBuffer tempBuff = new StringBuffer("");

      for (int i = 0; i < tempStr.length(); i++)
      {
        if (Character.isLetter( tempStr.charAt(i) ) )
        {
          if (isAminoAcid( tempStr.charAt(i) ) )
            tempBuff.append(tempStr.charAt(i) );
          else
            invalidInput = 1;  //  invalid input
        }
      }

      if (tempBuff.length() > 0 && invalidInput == 0)
      {
        allSequences.addElement(tempBuff.toString());
        numSequences++;
      }
    }


  }  //  end parseInput

  /*
   *  IS AMINO ACID
   */

  public boolean isAminoAcid (char ch)
  {

    if ( Character.isLetter (ch) )
    {
      if ( (ch != 'B') && (ch != 'J') && (ch != 'O') && (ch != 'U') && (ch != 'Z') && (ch != 'X' ) )  //  not amino acids
        return true;
    }

    return false;
  }


 /*
  *  LOOKUP WEIGHT
  */


private double weightDbl;
private int charIndex;
private char c;

  //  The lookupWeight(String s) method takes a string of AA's
  //  and integer indicating which weight table to use (mono == 0 or avg == 1),
  //  then looks up the appropriate weight associated with it.
  //  This function also checks for bad input

  public double lookupWeight(String s, int weight)
  {
    weightDbl = 0;  // initialization

    if (weight == 0)  //  mono weight requested
    {
      for (int i = 0; i < s.length(); i++)
      {
        c = s.charAt(i);

        charIndex = weightStr.indexOf(c);
        if (charIndex < 30 && charIndex >= 0) //  it is a valid element or AA
          weightDbl += monoWeightDbl[charIndex];
      }
    }
    else  //  avg weight requested
    {
      for (int i = 0; i < s.length(); i++)
      {
        c = s.charAt(i);

        charIndex = weightStr.indexOf(c);
        if (charIndex < 30 && charIndex >= 0) //  it is a valid element or AA
          weightDbl += avgWeightDbl[charIndex];
      }
    }

    return weightDbl;
  }



  /*
   *  ROUND FUNCTION
   */

private double dbl, rounderDbl;
private String dStr, rounder;

  public String roundFxn (double d)
  {
    dStr = String.valueOf(d);

    int places = 0;
    double div10 = d;

    //  places gives us how many digits - 1 are before the decimal 
    while (div10 > 10)
    {
      div10 = div10/10;
      places++;
    }

    //  one less that the number of significant figures desired
    int numZeroes = 6;

    //  figures out how many places after the decimal the number should go
    //  by subtracting the number of sig figs from the number of digits
    //  before the decimal (places)
    numZeroes = numZeroes - places;

    if (numZeroes >= 0)
    {
      //  create the rounder number
      rounder = "0.";
      for (int i = 0; i < (numZeroes); i++)
      {
        rounder += "0";
      }
      rounder += "5";

      dStr = dStr + "00000000000";

      dStr = dStr.substring(0,9);  // truncates the string rep of the double after seventh character
      dbl = new Double(dStr).doubleValue();  // converts the truncated string to double

      rounderDbl = new Double(rounder).doubleValue();

      dbl = dbl + rounderDbl;  //  if the last digit is >5 the number is rounded up, else stays the same
      dStr = String.valueOf(dbl);  //  convert back to string
      dStr = dStr.substring(0,8);  // chops off the last digit of double
    }

    else
    {
      dbl = Math.rint(d);
      dStr = String.valueOf(dbl);
    }
    
    return dStr;

  }




}  //  end class IsotopeServlet
















