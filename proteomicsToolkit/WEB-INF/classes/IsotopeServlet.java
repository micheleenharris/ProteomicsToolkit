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

  out.println("<CENTER><H3>Mass, Elemental Composition, Amino Acid Composition, Isotope Ratio Results</H3></CENTER>");

  if (allSequences.size() > 0)
  {
    //  format the output

    for(int i = 0; i < allSequences.size(); i++)
    {
      out.println("Sequence: <FONT COLOR=RED><B>" + outputBy((String)allSequences.elementAt(i),50) + "</B></FONT>, ");
      out.println("&nbsp; pI: <B>" + roundFxn(calc_pI((String)allSequences.elementAt(i))) + "</B><BR>");  //  output pI

      PrintOutput((String)allSequences.elementAt(i));
     
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
  
   public void PrintOutput(String seq)
   {
  
       StringBuffer output = new StringBuffer("");

       //  create table for tables and print it
       output.append("<TABLE CELLSPACING=5><TR VALIGN = TOP>\n");

       //  print the mass table
       output.append("<TD>" + massCalc(seq) + "</TD>\n");

       //  print the elemental composition table
       output.append("<TD>" + elemCompCalc(seq) + "</TD>\n");

       //  print the amino acid composition of peptide table
 //      output.append("<TR VALIGN = TOP><TD>" + aminoAcidCompCalc(seq) + "</TD>");
       output.append("<TD>" + aminoAcidCompCalc(seq) + "</TD>\n");
    
       //  print the isotope abundance data
       output.append("<TD>" + isotopeAbunCalc(seq) + "</TD>\n");

       //  end the output table
       output.append("</TR></TABLE>\n");

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
    tableBuff.append("<TABLE BORDER><CAPTION ALIGN=TOP><NOBR><B>Mass</B></NOBR></CAPTION>");

    //  mass heading
    tableBuff.append("<TR><TH></TH><TH COLSPAN=2 BGCOLOR=\"FFFFCC\">Mass</TH></TR>");

    //  mono and avg headings
    tableBuff.append("<TR><TH></TH><TH BGCOLOR=\"FFFFCC\">Mono</TH><TH BGCOLOR=\"FFFFCC\">Avg</TH></TR>");


    //  monoisotopic weight

    massDbl = lookupWeight(completeSeq,0);
    origMassDblMono = massDbl;  //  saves the non-charged mono mass
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = lookupWeight(completeSeq,1);
    origMassDblAvg = massDbl;  //  saves the non-charged avg mass
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH>M</TH><TD ALIGN=RIGHT>"
       + massStr1 + "</TD><TD ALIGN=RIGHT>" + massStr2 + "</TD></TR>");

    //  +1 charge

    //  monoisotopic weight

    massDbl = origMassDblMono + lookupWeight("h",0);
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = origMassDblAvg + lookupWeight("h",1);
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH NOWRAP>(M+H)<SUP>+</SUP></TH><TD ALIGN=RIGHT>"
       + massStr1 + "</TD><TD ALIGN=RIGHT>" + massStr2 + "</TD></TR>");
 

    //  +2 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hh",0))/2.0;
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("hh",1))/2.0;
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH NOWRAP>(M+2H)<SUP>2+</SUP></TH><TD ALIGN=RIGHT>"
       + massStr1 + "</TD><TD ALIGN=RIGHT>" + massStr2 + "</TD></TR>");

    //  +3 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hhh",0))/3.0;
    massStr1 = roundFxn(massDbl);

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("hhh",1))/3.0;
    massStr2 = roundFxn(massDbl);

    tableBuff.append("<TR><TH NOWRAP>(M+3H)<SUP>3+</SUP></TH><TD ALIGN=RIGHT>"
       + massStr1 + "</TD><TD ALIGN=RIGHT>" + massStr2 + "</TD></TR>");

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
      countH = 2;   /* terminal elements */
      countN = 0;
      countO = 1;   /* terminal elements */
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
      tableBuff.append("<TABLE BORDER><CAPTION ALIGN=TOP><NOBR><B>Elemental Composition</B></NOBR></CAPTION>");

      //  the headings
      tableBuff.append("<TR><TH BGCOLOR=\"FFFFCC\">Element</TH><TH BGCOLOR=\"FFFFCC\">Count</TH></TR>");

      if (countC != 0)
      {
        tableBuff.append("<TR><TH>Carbon</TH>");
        tableBuff.append("<TD ALIGN=CENTER>" + String.valueOf(countC) + "</TD></TR>");
      }

      if (countH != 0)
      {
        tableBuff.append("<TR><TH>Hydrogen</TH>");
        tableBuff.append("<TD ALIGN=CENTER>" + String.valueOf(countH) + "</TD></TR>");
      }

      if (countN != 0)
      {
        tableBuff.append("<TR><TH>Nitrogen</TH>");
        tableBuff.append("<TD ALIGN=CENTER>" + String.valueOf(countN) + "</TD></TR>");
      }

      if (countO != 0)
      {
        tableBuff.append("<TR><TH>Oxygen</TH>");
        tableBuff.append("<TD ALIGN=CENTER>" + String.valueOf(countO) + "</TD></TR>");
      }


      if (countS != 0)
      {
        tableBuff.append("<TR><TH>Sulfur</TH>");
        tableBuff.append("<TD ALIGN=CENTER>" + String.valueOf(countS) + "</TD></TR>");
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
      tableBuff.append("<TABLE BORDER><CAPTION ALIGN=TOP><NOBR><B>Amino Acid Composition</B></NOBR></CAPTION>");

      //  the headings
      tableBuff.append("<TR><TH BGCOLOR=\"FFFFCC\">AA</TH><TH BGCOLOR=\"FFFFCC\">Count</TH><TH BGCOLOR=\"FFFFCC\">Comp %</TH></TR>");

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
          tableBuff.append("<TR><TH>" + String.valueOf(AA) + "</TH>");
          tableBuff.append("<TD ALIGN=CENTER>" + String.valueOf(occurences) + "</TD>");
          tableBuff.append("<TD ALIGN=RIGHT>" + roundFxn(abundance) + "</TD></TR>");
        }

      }

      tableBuff.append("</TABLE><BR><BR>");
 
      return tableBuff.toString();     

    }



  /*
   *  ISOTOPE ABUNDANCE CALC
   */

  double massPlusZero, massPlusOne, massPlusTwo;
  String plusZeroStr, plusOneStr, plusTwoStr;

  //  Calculates the Isotope Abundance Ratio in the polypeptide
  public String isotopeAbunCalc (String s)
  {

    massPlusZero = 100.0;
    massPlusOne = (1.1 *countC) + (0.016 * countH) + (0.38 * countN);
    massPlusTwo = (Math.pow((1.1 * countC),2) / 200)
                  + (Math.pow((0.016 * countH),2) / 200)
                  + (Math.pow((0.38 * countO),2) / 200);

    if (massPlusZero >= massPlusOne && massPlusZero >= massPlusTwo)
    {
       massPlusOne  /= massPlusZero;
       massPlusTwo  /= massPlusZero;
       massPlusZero /= massPlusZero;
    }
    else if (massPlusOne >= massPlusZero && massPlusOne >= massPlusTwo)
    {
       massPlusZero /= massPlusOne;
       massPlusTwo  /= massPlusOne;
       massPlusOne  /= massPlusOne;
    }
    else if (massPlusTwo >= massPlusZero && massPlusTwo >= massPlusOne)
    {
       massPlusZero /= massPlusTwo;
       massPlusOne  /= massPlusTwo;
       massPlusTwo  /= massPlusTwo;
    }

    plusZeroStr = roundFxn(massPlusZero*100.0);
    plusOneStr = roundFxn(massPlusOne*100.0);
    plusTwoStr = roundFxn(massPlusTwo*100.0);

    StringBuffer tableBuff = new StringBuffer("");


    /*  CREATE HTML TABLE */

    //  the start of the table and caption
    tableBuff.append("<TABLE BORDER><CAPTION ALIGN=TOP><NOBR><B>Isotope Ratio</B></NOBR></CAPTION>");

    //  the headings
    tableBuff.append("<TR><TH BGCOLOR=\"FFFFCC\">Peak</TH><TH BGCOLOR=\"FFFFCC\"><NOBR>Peak %</NOBR></TH></TR>");

    //  the isotope ratio data
    tableBuff.append("<TR><TH>M</TH><TD ALIGN=RIGHT>" + plusZeroStr + "</TD></TR>");
    tableBuff.append("<TR><TH>M+1</TH><TD ALIGN=RIGHT>" + plusOneStr + "</TD></TR>");
    tableBuff.append("<TR><TH>M+2</TH><TD ALIGN=RIGHT>" + plusTwoStr + "</TD></TR>");

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
    weightDbl = 0.0;  // initialization

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

  public String roundFxn (double d)
  {
     double d2;
     int i,
         p;
     String s1, s2;

     p = 4;  // # of decimal points

     d2 = Math.pow(10.0 , (double)p);
     d = d*d2;
     i = (int)d;
     s1 = String.valueOf(i);
     s2 = s1.substring(0, s1.length()-p);
     s2 = s2 + "." + s1.substring(s1.length()-p);
     return s2;
  }


  /*
   *  CALCULATE pI
   */
 
/* the 7 amino acid which matter */
static int R = 'R' - 'A',
           H = 'H' - 'A',
           K = 'K' - 'A',
           D = 'D' - 'A',
           E = 'E' - 'A',
           C = 'C' - 'A',
           Y = 'Y' - 'A';
/*
 *  table of pk values :
 *  Note: the current algorithm does not use the last two columns. Each
 *  row corresponds to an amino acid starting with Ala. J, O and U are
 *  inexistant, but here only in order to have the complete alphabet.
 *
 */
 
static double [][] pk = {
 /*          Ct    Nt   Sm     Sc     Sn */
/* A */    {3.55, 7.59, 0.0  , 0.0  , 0.0   },
/* B */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* C */    {3.55, 7.50, 9.00 , 9.00 , 9.00  },
/* D */    {4.55, 7.50, 4.05 , 4.05 , 4.05  },
/* E */    {4.75, 7.70, 4.45 , 4.45 , 4.45  },
/* F */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* G */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* H */    {3.55, 7.50, 5.98 , 5.98 , 5.98  },
/* I */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* J */    {0.00, 0.00, 0.0  , 0.0  , 0.0   },
/* K */    {3.55, 7.50, 10.00, 10.00, 10.00 },
/* L */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* M */    {3.55, 7.00, 0.0  , 0.0  , 0.0   },
/* N */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* O */    {0.00, 0.00, 0.0  , 0.0  , 0.0   },
/* P */    {3.55, 8.36, 0.0  , 0.0  , 0.0   },
/* Q */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* R */    {3.55, 7.50, 12.0 , 12.0 , 12.0  },
/* S */    {3.55, 6.93, 0.0  , 0.0  , 0.0   },
/* T */    {3.55, 6.82, 0.0  , 0.0  , 0.0   },
/* U */    {0.00, 0.00, 0.0  , 0.0  , 0.0   },
/* V */    {3.55, 7.44, 0.0  , 0.0  , 0.0   },
/* W */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* X */    {3.55, 7.50, 0.0  , 0.0  , 0.0   },
/* Y */    {3.55, 7.50, 10.00, 10.00, 10.00 },
/* Z */    {3.55, 7.50, 0.0  , 0.0  , 0.0   }};
 
 
   static final double PH_MIN = 0.0;  //  minimum pH value
   static final double PH_MAX = 14.0;  //  maximum pH value
   static final double MAX_LOOP = 2000;  //  maximum iterations
   static final double DEC = 0.0001;  //  desired precision
 
   public double exp_10(double val)
   {
      return (Math.pow(10,val));
   }

   public double calc_pI(String seq)
   {
      int [] comp = new int [26];  //  number of each amino acid in sequence
      int ntermres,     //  integer value representing n-terminus amino acid
          ctermres;     //  integer value representing c-terminus amino acid
 
      double charge,
             ph_mid,
              ph_min,
              ph_max,
              cter,
              nter,
              carg,   //  charge for arginines (R)
              clys,   //  charge for lysines (Y)
              chis,   //  charge for histidines (H)
              casp,   //  charge for asparagines (
              cglu,   //  charge for glutamines (
              ctyr,   //  charge for tyrosines
              ccys;   //  charge for cystines (C)
 
 
     //  compute the number of each amino acid in sequence
     for (int i = 0;  i < seq.length(); i++)
     {
        comp[seq.charAt(i) - 'A']++;
     }
    
     ntermres = seq.charAt(0) - 'A';  //  index of n-terminus amino acid
     ctermres = seq.charAt(seq.length()-1) - 'A';  //  index of c-terminus amino acid
    
     ph_min = PH_MIN;
     ph_max = PH_MAX;
     ph_mid = ph_min + (ph_max - ph_min) / 2.0;
    
     for (int i = 0; ( i < MAX_LOOP ) && ( (ph_max - ph_min) > DEC );i++ )
     {
    
        ph_mid = ph_min + (ph_max - ph_min) / 2.0;
    
        cter = exp_10(-pk[ctermres][0]) / ( exp_10(-pk[ctermres][0]) + exp_10(-ph_mid));
        nter = exp_10(-ph_mid) / ( exp_10(-pk[ntermres][1]) + exp_10(-ph_mid));
    
        carg = comp[R] * exp_10(-ph_mid) / ( exp_10 (-pk[R][2]) + exp_10(-ph_mid) );
        chis = comp[H] * exp_10(-ph_mid) / ( exp_10 (-pk[H][2]) + exp_10(-ph_mid) );
        clys = comp[K] * exp_10(-ph_mid) / ( exp_10 (-pk[K][2]) + exp_10(-ph_mid) );
        casp = comp[D] * exp_10(-pk[D][2]) / ( exp_10 (-pk[D][2]) + exp_10(-ph_mid) );
        cglu = comp[E] * exp_10(-pk[E][2]) / ( exp_10 (-pk[E][2]) + exp_10(-ph_mid) );
        ccys = comp[C] * exp_10(-pk[C][2]) / ( exp_10 (-pk[C][2]) + exp_10(-ph_mid) );
        ctyr = comp[Y] * exp_10(-pk[Y][2]) / ( exp_10 (-pk[Y][2]) + exp_10(-ph_mid) );
    
        charge = carg + clys + chis + nter - (casp + cglu + ctyr + ccys + cter);
    
        if (charge > 0.0)
           ph_min = ph_mid;
        else
           ph_max = ph_mid;
      }
    
      return ph_mid;
   } // calc_pI


}

