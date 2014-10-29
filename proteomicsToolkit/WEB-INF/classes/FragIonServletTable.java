import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.StringTokenizer;
import java.util.Vector;

import java.lang.String.*;

public class FragIonServlet extends HttpServlet
{

  // ************************** SERVLET CODE **************************

  /*
   *  CONSTANTS
   */
 
  private static final int MAX_PEPTIDES = 20;

 /*
  *  VARIABLES
  */

  private String sequence,
                 massType,
                 nterm,
                 cterm,
                 addModifType,
                 addModifVal,
                 outputSeq,
                 charge,
                 aCB,
	         bCB,
      	 	 cCB,
      		 xCB,
      		 yCB,
      		 zCB;

  private int numSequences,  //  the number of sequences entered by user
              invalidInput = 0;  //  0 == OK, 1 == invalid input

  private Vector allSequences = new Vector();  // vector where all sequences are stored


  private PrintWriter out;

    //  The following strings avgWeight and monoWeight contain the weights cooresponding to elements and amino acids


    //  this String relates the molecule (atom or amino acid) to the arrays of weights based on their index
    private String weightStrAA = "GASPVTCLIXNOBDQKZEMHFRYWJU";  // the amino acids plus other alpha's
    private String weightStrElem = "hocnpsabdefgijklmqrtuvwxyz";  //  the elements plus other alpha's


    /*
    The following tables of amino acid weights are for each amino acid already assuming
    that the AA is in a peptide bond therefore the C-terminal -OH group is missing and
    N-terminal -H is missing.  These are added later when calculating fragment ions.
    */

    private double [] avgWeightDblAA = {//  the amino acids (always upper case)
        57.05192000,  //  G
        71.07880000,  //  A
        87.07820000,  //  S
        97.11668000,  //  P
        99.13256000,  //  V
        101.1050800,  //  T
        103.1388000,  //  C
        113.1594400,  //  L
        113.1594400,  //  I
        113.1594400,  //  X --> L or I
        114.1038400,  //  N
        114.1472000,  //  O
        114.5962200,  //  B --> avg of N and D
        115.0886000,  //  D
        128.1307200,  //  Q
        128.1740800,  //  K
        128.6231000,  //  Z --> avg Q and E
        129.1154800,  //  E
        131.1925600,  //  M
        137.1410800,  //  H
        147.1765600,  //  F
        156.1874800,  //  R
        163.1759600,  //  Y
        186.2132000,  //  W
        0.0,  //  J --> not an amino acid
        0.0};  //  U --> not an amino acid


    private double [] avgWeightDblElem = {  //  the elements
        1.007940000,  //  hydrogen
        15.99940000,  //  oxygen
        12.01070000,  //  carbon
        14.00674000,  //  nitrogen
        30.97376100,  //  phosphorous
        32.06600000,  //  sulfur
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0};


    private double [] monoWeightDblAA = {  //  the monoistopic weight of amino acids
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


    private double [] monoWeightDblElem = {  //  the monoistopic weight of the elements H, O, C, N, P, and S
        1.007825000,  //  hydrogen
        15.99491460,  //  oxygen
        12.00000000,  //  carbon
        14.00307400,  //  nitrogen
        30.97376330,  //  phosphorous
        31.97207180,  //  sulfur
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0};


  /*
   *  DO GET
   */


  //  This function is the main servlet function which communicates with the server
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {

  res.setContentType ("text/html");
  out = res.getWriter();

  /*
   *  GET USER OPTIONS
   */

  //  retrieves the user entered protein sequence
  sequence = req.getParameter("sequence");

  //  retrieves the user selected charge state
  charge = req.getParameter("charge");
  
  //  retrieves the user selected mass type
  massType = req.getParameter("massType");

  //  retrieves the user selected ion-fragmentation patterns
  aCB = req.getParameter("aCB");
  bCB = req.getParameter("bCB");
  cCB = req.getParameter("cCB");
  xCB = req.getParameter("xCB");
  yCB = req.getParameter("yCB");
  zCB = req.getParameter("zCB");

  //  retrieves the user selected output order
  nterm = req.getParameter("nterm");

  //  retrieves the user selected output order
  cterm = req.getParameter("cterm");

  //  retrieves the user selected output order
  addModifType = req.getParameter("addModifType");

  //  retrieves the user selected output order
  addModifVal = req.getParameter("addModifVal");

  //  retrieves the user selected output order
  outputSeq = req.getParameter("outputSeq");


  /*
   *  CREATION OF HTML DOC FOR OUTPUT
   */

  if (sequence.length() > 0)
  {

    out.println("<HTML>");
    out.println("<HEAD><TITLE>Output Page</TITLE></HEAD><BODY>");

    out.println("<CENTER><H2>Fragment Ion Calculator Results</H2></CENTER>");

    //  format the output
    out.println("<FONT SIZE=+1>");

    //  Outputs the rest of the HTML doc
    Output(sequence);
   
    if (invalidInput == 0) // input was valid
      out.println("</BODY></HTML>");

   }  

  }

 // ************************** HELPER METHODS **************************

  /*
   *  OUTPUT RESULTS FUNCTION
   */

  public void Output(String seq)
  {
    allSequences.clear();

    //  parses user input and places individual sequences
    //  into the vector allSequences
    parseInput(seq);

    if (invalidInput == 1)  //  invalid peptide was entered
      out.println( "Invalid Input:  an invalid amino acid was entered.  <BR>Please correct input and resubmit.<BR></BODY></HTML>");
  
   if (invalidInput == 0)
   {

    //  for each sequence do the following
    //  STEP1 -->  calculate mass
    //  STEP2 -->  calculate fragment ions
    //  STEP3 -->  output results

    String fragIonTable = "";
    String massTable = "";
    String tempSeq = "";


    for (int i = 0; i < allSequences.size(); i++)
    {
      tempSeq = (String)allSequences.elementAt(i);
      
      out.println("<HR WIDTH = 900><CENTER>");

      /* STEP1 */

      massTable = massTblOut(tempSeq);

      /* STEP2 */

      if (aCB != null ||
          bCB != null ||
          cCB != null ||
          xCB != null ||
          yCB != null ||
          zCB != null )
      {
        fragIonTable = ionTblOut(tempSeq);
      }

      /* STEP3 */

      // output sequence
      out.println("Sequence: " + outputBy50(tempSeq) + "<BR>");

      //  output pI
      out.println("pI: " + roundFxn(calc_pI(tempSeq)) + "<BR>");

      //  output the mass table
      out.println(massTable + "<BR>" );

      //  output the table of fragment ions
      out.println("Mass type: " + massType + "<BR>");

      out.println(fragIonTable + "<BR>");
    }
   }

  }


  /*
   *  OUTPUT BY 50
   */

  public String outputBy50 (String s)
  {
    //  output sequence -- 50 characters per line

    int count = 0;
    StringBuffer tempBuff = new StringBuffer("");
    for (int i = 0; i < s.length(); i++)
    {
      count++;
      if (count == 50)
      {
        tempBuff.append("<BR>");
        count = 0;  // restart counter
      }
      tempBuff.append(s.charAt(i));
    }
  
    return tempBuff.toString();
  }

  /*
   *  PARSE INPUT SEQUENCES
   */

  public void parseInput (String input)
  {
    invalidInput = 0;  //  reinitialize to OK
   
    //  the number of sequences
    numSequences = 0;
    
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
      if ( (ch != 'J') && (ch != 'U') ) //  not amino acids
        return true;
    }

    return false;
  }

  /*
   *   MASS TABLE OUT
   */


   private double massDbl, origMassDblMono, origMassDblAvg;
   private String completeSeq, massStr1, massStr2;

   public String massTblOut(String seq)
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
    tableBuff.append("<TABLE BORDER WIDTH = 25%><CAPTION ALIGN=TOP>Mass / Charge Table</CAPTION>");

    //  mass heading
    tableBuff.append("<TR><TH></TH><TH COLSPAN=2>Mass</TH></TR>");

    //  mono and avg headings
    tableBuff.append("<TR><TH></TH><TH>Mono</TH><TH>Average</TH></TR>");


    //  monoisotopic weight

    massDbl = lookupWeight(completeSeq,0);
    origMassDblMono = massDbl;  //  saves the non-charged mono mass
    massStr1 = roundFxn(massDbl).toString();

    //  average weight

    massDbl = lookupWeight(completeSeq,1);
    origMassDblAvg = massDbl;  //  saves the non-charged avg mass
    massStr2 = roundFxn(massDbl).toString();

    tableBuff.append("<TR><TH ALIGN = LEFT>(M)</TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    //  +1 charge

    //  monoisotopic weight

    massDbl = origMassDblMono + lookupWeight("h",0);
    massStr1 = roundFxn(massDbl).toString();

    //  average weight

    massDbl = origMassDblAvg + lookupWeight("h",1);
    massStr2 = roundFxn(massDbl).toString();

    tableBuff.append("<TR><TH ALIGN = LEFT>(M+H)<SUP>+</SUP></TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");


    //  +2 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hh",0))/2.0;
    massStr1 = roundFxn(massDbl).toString();

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("h",1))/2.0;
    massStr2 = roundFxn(massDbl).toString();

    tableBuff.append("<TR><TH ALIGN = LEFT>(M+2H)<SUP>2+</SUP></TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    //  +3 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hhh",0))/3.0;
    massStr1 = roundFxn(massDbl).toString();

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("hhh",1))/3.0;
    massStr2 = roundFxn(massDbl).toString();

    tableBuff.append("<TR><TH ALIGN = LEFT>(M+3H)<SUP>3+</SUP></TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    //  +4 charge

    //  monoisotopic weight

    massDbl = (origMassDblMono + lookupWeight("hhhh",0))/4.0;
    massStr1 = roundFxn(massDbl).toString();

    //  average weight

    massDbl = (origMassDblAvg + lookupWeight("hhhh",1))/4.0;
    massStr2 = roundFxn(massDbl).toString();

    tableBuff.append("<TR><TH ALIGN = LEFT>(M+4H)<SUP>4+</SUP></TH><TD>" + massStr1 + "</TD><TD>" + massStr2 + "</TD></TR>");

    tableBuff.append("</TABLE><BR><BR>");

     return tableBuff.toString();
   }





  /*
   *  CREATE MASS OUTPUT TABLE
   */
/*
  public String massTblOut (String s)
  {
        
    String completePep = s + "hoh";

    //  Monoisotopic weight

    double mass = lookupWeight(completePep, 0);
    String massStr = "";
    String massLabel = "";

    double originalMass = mass;  // save value of original mass

    massStr = roundFxn(mass) + "<BR>";
    massLabel = "(M):<BR>";

    mass = originalMass + lookupWeight("h",0);
    massStr += roundFxn(mass) + "<BR>";
    massLabel += "(M+H)<SUP>+</SUP>:<BR>";

    mass = ( originalMass + lookupWeight("hh") )/2.0;
    massStr += roundFxn(mass) + "<BR>";
    massLabel += "(M+2H)<SUP>2+</SUP>:<BR>";

    mass = ( originalMass + lookupWeight("hhh") )/3.0;
    massStr += roundFxn(mass) + "<BR>";
    massLabel += "(M+3H)<SUP>3+</SUP>:<BR>";

    mass = ( originalMass + lookupWeight("hhhh") )/4.0;
    massStr += roundFxn(mass) + "<BR>";
    massLabel += "(M+4H)<SUP>4+</SUP>:<BR>";


    //  CREATE HTML TABLE WITH FRAGMENT MASSES FOR IONS SELECTED 

    StringBuffer tableStrBuff = new StringBuffer("");

    tableStrBuff.append("<TABLE BORDER>");
    tableStrBuff.append("<CAPTION ALIGN=TOP>Mass/Charge Table</CAPTION>");
    tableStrBuff.append("<TR>");

    if (massType.equals("monoRB"))
      tableStrBuff.append("<TH COLSPAN = 2>Monoisotopic Mass</TH>");
    else
      tableStrBuff.append("<TH COLSPAN = 2>Average Mass</TH>");

    tableStrBuff.append("<TR><TD><PRE>" + massLabel + "</PRE></TD><TD><PRE>" + massStr + "</PRE></TD></TR></TABLE>");

    return tableStrBuff.toString();

  }
*/
  private String AAStr = "",
                 numberStr = "",
                 numberStr2 = "",
                 aIonMassStr = "",
                 bIonMassStr = "",
                 cIonMassStr = "",
                 xIonMassStr = "",
                 yIonMassStr = "",
                 zIonMassStr = "",
                 tableStr = "";

  private StringBuffer aIonMassBuff = new StringBuffer("");
  private StringBuffer bIonMassBuff = new StringBuffer("");
  private StringBuffer cIonMassBuff = new StringBuffer("");
  private StringBuffer xIonMassBuff = new StringBuffer("");
  private StringBuffer yIonMassBuff = new StringBuffer("");
  private StringBuffer zIonMassBuff = new StringBuffer("");

  /*
   *  CREATE ION FRAGMENT OUTPUT TABLE
   */

  public String ionTblOut(String s)
  {

    //  Output the sequence one character per line
    char AA;
    AAStr = "";
    for (int i = 0; i < s.length(); i++)
    {
      AA = s.charAt(i);
      AAStr = AAStr + "!" +  String.valueOf(AA);
    }

    //  create string of increasing numbers
    int numLine = 0;
    String numLineStr = "";
    for (int i = 0; i < s.length(); i++)
    {
      ++numLine;
      numLineStr = numLineStr + "!" + String.valueOf(numLine);
    }
    numberStr = "";
    numberStr = numLineStr;


    //  create string of decreasing numbers
    numLineStr = "";
    for (int i = s.length(); i > 0; i--)
    {
      numLineStr = numLineStr + "!" + String.valueOf(i);
    }
    numberStr2 = "";
    numberStr2 = numLineStr;


    Vector outputVec = new Vector();

   calcIon(s);

   aIonMassStr = aIonMassBuff.toString();
   bIonMassStr = bIonMassBuff.toString();
   cIonMassStr = cIonMassBuff.toString();
   xIonMassStr = xIonMassBuff.toString();
   yIonMassStr = yIonMassBuff.toString();
   zIonMassStr = zIonMassBuff.toString();

   StringTokenizer tokA = new StringTokenizer(aIonMassStr,"!");
   StringTokenizer tokB = new StringTokenizer(bIonMassStr,"!");
   StringTokenizer tokC = new StringTokenizer(cIonMassStr,"!");
   StringTokenizer tokX = new StringTokenizer(xIonMassStr,"!");
   StringTokenizer tokY = new StringTokenizer(yIonMassStr,"!");
   StringTokenizer tokZ = new StringTokenizer(zIonMassStr,"!");
   StringTokenizer tokAmino = new StringTokenizer(AAStr,"!");
   StringTokenizer tokNumIncr = new StringTokenizer(numberStr,"!");
   StringTokenizer tokNumDecr = new StringTokenizer(numberStr2,"!");


  for (int i = 0; i < s.length(); i++)
  {

   StringBuffer outputBuff = new StringBuffer("");

   if (tokAmino.hasMoreTokens() && tokNumIncr.hasMoreTokens())
   {
     //  the letter in sequence and number in sequence
     outputBuff.append("<TR><TD><PRE>" + tokAmino.nextToken() + "</PRE></TD><TD><PRE>" + padLeft(tokNumIncr.nextToken(),4) + "</PRE></TD>");
   }

   if (aCB != null && tokA.hasMoreTokens())
   {
     outputBuff.append("<TD><FONT COLOR = #990033><PRE>" + padLeft(tokA.nextToken(),9) + "</FONT></TD>");
   }
   if (bCB != null && tokB.hasMoreTokens())
   {
     outputBuff.append("<TD ALIGN = RIGHT><FONT COLOR = #ff6633><PRE>" + padLeft(tokB.nextToken(),9) + "</PRE></FONT></TD>");
   }
   if (cCB != null && tokC.hasMoreTokens() )
   {
     if (i == s.length()-1)
     {
       String skip = tokC.nextToken();
       outputBuff.append("<TD><FONT COLOR = #006633><PRE>" + padCenter("-",9) + "</PRE></FONT></TD>");
     }
     else
       outputBuff.append("<TD><FONT COLOR = #006633><PRE>" + padLeft(tokC.nextToken(),9) + "</PRE></FONT></TD>");
   }
   if (xCB != null && tokX.hasMoreTokens())
   {
     if (i == 0)
     {
       String skip = tokX.nextToken();
       outputBuff.append("<TD><FONT COLOR = #006666><PRE>" + padCenter("-",9) + "</PRE></FONT></TD>");
     }
     else
       outputBuff.append("<TD><FONT COLOR = #006666><PRE>" + padLeft(tokX.nextToken(),9) + "</PRE></FONT></TD>");
   }
   if (yCB != null && tokY.hasMoreTokens() )
   {
     outputBuff.append("<TD><FONT COLOR = #000066><PRE>" + padLeft(tokY.nextToken(),9) + "</PRE></FONT></TD>");
   }
   if (zCB != null && tokZ.hasMoreTokens())
   {
     outputBuff.append("<TD><FONT COLOR = #663366><PRE>" + padLeft(tokZ.nextToken(),9) + "</PRE></FONT></TD>");
   }

   if ( (xCB != null || yCB != null || zCB != null) && tokNumDecr.hasMoreTokens() )
   {
     outputBuff.append("<TD><PRE>" + padLeft(tokNumDecr.nextToken(),4) + "</PRE></TD>");
   }
   
   outputBuff.append("</TR>");

  outputVec.addElement(outputBuff.toString());

  }


  /*  CREATE HTML TABLE WITH FRAGMENT MASSES FOR IONS SELECTED */

  StringBuffer tableStrBuff = new StringBuffer("");

  tableStrBuff.append("<TABLE BORDER>");
  tableStrBuff.append("<CAPTION ALIGN=TOP>Fragment Ions</CAPTION>");
  tableStrBuff.append("<TR><TH>Seq</TH><TH>#</TH>");  //  the constant part of the heading row

  //  create heading for each ion selected
  if (aCB != null)
    tableStrBuff.append("<TH><FONT COLOR = #990033>a</FONT></TH>");
  if (bCB != null)
    tableStrBuff.append("<TH><FONT COLOR = #ff6633>b</FONT></TH>");
  if (cCB != null)
    tableStrBuff.append("<TH><FONT COLOR = #006633>c</FONT></TH>");
  if (xCB != null)
    tableStrBuff.append("<TH><FONT COLOR = #006666>x</FONT></TH>");
  if (yCB != null)
    tableStrBuff.append("<TH><FONT COLOR = #000066>y</FONT></TH>");
  if (zCB != null)
    tableStrBuff.append("<TH><FONT COLOR = #663366>z</FONT></TH>");

  if (xCB != null || yCB != null || zCB != null )
  {
    if (charge.equals("1"))
      tableStrBuff.append("<TH>(+1)</TH>");
    if (charge.equals("2"))
      tableStrBuff.append("<TH>(+2)</TH>");
    if (charge.equals("3"))
      tableStrBuff.append("<TH>(+3)</TH>");
  }
  for (int i = 0; i < outputVec.size(); i++)
  {
    tableStrBuff.append((String)outputVec.elementAt(i));
  }

  tableStrBuff.append("</TABLE>");


  tableStr = tableStrBuff.toString();

  return tableStr;

  }


  /*
   *  CALCULATE FRAGMENT ION MASS
   */

  //  vector to contain the types (AA's or numbers) of the additional modifications
  private Vector addModifTypeVec = new Vector ();
  
  //  vector to contain the values of the additional modifications
  private Vector addModifValVec = new Vector ();

  private String ntermMod,
                 ctermMod,
                 completeABC,
                 completeXYZ,
                 IonMassStr;

  private StringBuffer str = new StringBuffer("");

  private double termDblABC,
                 termDblXYZ,
                 addModDblABC,
                 addModDblXYZ,
                 IonMass;

  private int indexMod;


  public void calcIon (String s)
  {
    //  reinitialization of variables
    addModifTypeVec.clear();
    addModifValVec.clear();
    completeABC = "";
    completeXYZ = "";
    addModDblXYZ = 0.0;
    addModDblABC = 0.0;
    IonMass = 0.0;
    IonMassStr = "";

    if (addModifVal.length() > 0)  // there are additional modifications present
    {
      //  the delimiter is the newline or carriage return
      StringTokenizer tokType = new StringTokenizer (addModifType, "\n");
      StringTokenizer tokVal = new StringTokenizer (addModifVal, "\n");

      //  get additional modification values and place in vector addModifValVec
      while( tokType.hasMoreTokens() && tokVal.hasMoreTokens() )
      {
        addModifTypeVec.addElement(tokType.nextToken());
        addModifValVec.addElement(tokVal.nextToken());
      }
    }

        aIonMassBuff = new StringBuffer("");
        bIonMassBuff = new StringBuffer("");
        cIonMassBuff = new StringBuffer("");
        xIonMassBuff = new StringBuffer("");
        yIonMassBuff = new StringBuffer("");
        zIonMassBuff = new StringBuffer("");

      for (int i = 0; i < s.length(); i++)
      {

        //  Retrieves the n-terminus modifications:
        ntermMod = nterm;

        //  Retrieves the c-terminus modifications:
        ctermMod = cterm;

        termDblABC = 0.0;  //  reinitialize
        termDblXYZ = 0.0;  //  reinitialize

        IonMass = 0.0;  //  reinitialize

        addModDblABC = 0.0;  //  reinitialize
        addModDblXYZ = 0.0;  //  reinitialize
  
        //  leave out last fragment of c-ion series
        if (i == (s.length()-1) && cCB != null)
        {
          cIonMassBuff.append("!<CENTER>-</CENTER>!");
        }

        //  leave out first fragment of x-ion series
        if (i == 0 && xCB != null)
        {
          xIonMassBuff.append( "!<CENTER>-</CENTER>!");
        }


      try {

          //  if there is n-terminus modification it is retrieved and added to ALL fragments
          if (ntermMod.length() > 0)
            termDblABC = new Double(ntermMod).doubleValue();

          //  if there is c-terminus modification it is retrieved and added to LAST fragment
          if (ctermMod.length() > 0 && i == (s.length()-1) )
            termDblABC += new Double(ctermMod).doubleValue();


          for (int j = 0; j < completeABC.length(); j++)
          {
            for(int k = 0; k < addModifTypeVec.size(); k++)
            {
              if (completeABC.charAt(j) == ( ( ( (String)addModifTypeVec.elementAt(k) ).toUpperCase() ).charAt(0)  ) )
                addModDblABC += new Double( (String)addModifValVec.elementAt(indexMod) ).doubleValue();

              String jStr = String.valueOf(j);
              if (jStr.equalsIgnoreCase((String)addModifTypeVec.elementAt(k))  )
                addModDblABC += new Double( (String)addModifValVec.elementAt(indexMod) ).doubleValue();
            }
          }


          //  if there is n-terminus modification it is retrieved and added to FIRST fragment
          if (ntermMod.length() > 0 && i == 0)
            termDblXYZ = new Double(ntermMod).doubleValue();

          //  if there is c-terminus modification it is retrieved and added to ALL fragments
          if (ctermMod.length() > 0)
            termDblXYZ += new Double(ctermMod).doubleValue();

          int diff = 0;

          for (int j = 0; j < completeXYZ.length() ; j++)
          {
            for(int k = 0; k < addModifTypeVec.size(); k++)
            {
              if (completeXYZ.charAt(j) == ( ( ( (String)addModifTypeVec.elementAt(k) ).toUpperCase() ).charAt(0) ) )
                addModDblXYZ += new Double( (String)addModifValVec.elementAt(indexMod) ).doubleValue();

              diff = 0;

              diff = s.length() - completeXYZ.length();
              String jStr = String.valueOf( diff + j + 3 );

              //  if number indicated by user is not over the lenght of peptide add value
              if (s.length() > (diff + j + 3))
              {
                if (jStr.equalsIgnoreCase((String)addModifTypeVec.elementAt(k)) )
                  addModDblXYZ += new Double( (String)addModifValVec.elementAt(k) ).doubleValue();
              }
            }
          }
      }
      catch ( NumberFormatException e )
      {
        out.println("Error: " + e + "<BR>");
      }
        //  fragment ion string for a, b, or c ion -- must readd N-terminal -H
        completeABC = "h" + s.substring(0,(i+1));

        //  fragment ion string for x, y, or z ion -- must readd C-terminal -OH
        completeXYZ = "oh" + s.substring(i,s.length());


        //  this part is specific for each ion

        if (aCB != null)
        {
          IonMass = divCharge((lookupWeight(completeABC) - lookupWeight("co")) + termDblABC + addModDblABC);
          str = (roundFxn(IonMass)); 
          aIonMassBuff.append("!");
          aIonMassBuff.append(str);
        }
        if ( bCB != null )
        {
          IonMass = divCharge(lookupWeight(completeABC) + termDblABC + addModDblABC);
          str = roundFxn(IonMass);
          bIonMassBuff.append("!");
          bIonMassBuff.append(str);
        }
        if ( cCB != null )
        {
          IonMass = divCharge((lookupWeight(completeABC) + lookupWeight("nhhh")) + termDblABC + addModDblABC);
          str = roundFxn(IonMass);
          cIonMassBuff.append("!");
          cIonMassBuff.append(str);
        }
        if (  xCB != null && i != 0 )  //  skip the first ion calculation
        {
          IonMass = divCharge(lookupWeight(completeXYZ) + lookupWeight("co") + termDblXYZ + addModDblXYZ);
          str = roundFxn(IonMass);
          xIonMassBuff.append("!");
          xIonMassBuff.append(str);
        }
        if ( yCB != null )
        {
          IonMass = divCharge((lookupWeight(completeXYZ)  + lookupWeight("hh")) + termDblXYZ + addModDblXYZ);
          str = roundFxn(IonMass);
          yIonMassBuff.append("!");
          yIonMassBuff.append(str);
        }
        if (  zCB != null )
        {
          IonMass = divCharge((lookupWeight(completeXYZ)  - lookupWeight("nh")) + termDblXYZ + addModDblXYZ);
          str = roundFxn(IonMass);
          zIonMassBuff.append("!");
          zIonMassBuff.append(str);
        }

      }


  }

  /*
   *  DIV CHARGE
   */

  public double divCharge(double mass)
  {
    if (mass > 0)
    {
      if (charge.equals("1"))  //  do nothing already calculated +1 fragments
        return mass;
      else if (charge.equals("2"))  //  add an additional H and divide by charge(+2)
        return ( ( mass + lookupWeight("h") ) / 2.0);
      else //  charge == 3  -->  add an additional 2 H's and divide by charge (+3)
        return ( ( mass + lookupWeight("hh") ) /3.0 );
    }
    else
     return 0.0;
  }


  /*
   *  LOOKUP WEIGHT (two overloaded functions)
   */

double weightDbl;
int charIndex;
char ch;
int badInput = 0;

  //  The lookupWeight(String s) method takes a string of AA's and looks up the appropriate weight associated with it
  //  This function also checks for bad input (invalid amino acid or seqence greater than 30 amino acids)

  public double lookupWeight(String s)
  {
    badInput = 0;  //  reinitialize to false

    weightDbl = 0;  // initialization

    double [] AAWeightArray = new double [26];
    double [] ElemWeightArray = new double [26];

    if (massType.equals("monoRB")) // monoisotopic weight
    {
      AAWeightArray = monoWeightDblAA;
      ElemWeightArray = monoWeightDblElem;
    }
    else                              //  average weight radio button selected
    {
      AAWeightArray = avgWeightDblAA;
      ElemWeightArray = avgWeightDblElem;
    }


      for (int i = 0; i < s.length(); i++)
      {
        ch = s.charAt(i);

        if (Character.isUpperCase(ch)) //  ch is an uppercase letter (ie an amino acid)
        {
          charIndex = weightStrAA.indexOf(ch);
          weightDbl += AAWeightArray[charIndex];

        }
        else if (Character.isLowerCase(ch))  // ch is a lowercase letter (ie an element)
        {
          charIndex = weightStrElem.indexOf(ch);
          weightDbl += ElemWeightArray[charIndex];
        }
        else
        {
          badInput = 1;
          return weightDbl;  // gets out of lookupWeight immediately
        }
      }

    return weightDbl;
  }



  public double lookupWeight(String s, int type)
  {
    badInput = 0;  //  reinitialize to false

    weightDbl = 0;  // initialization

    double [] AAWeightArray = new double [26];
    double [] ElemWeightArray = new double [26];

    if (type == 0) // monoisotopic weight
    {
      AAWeightArray = monoWeightDblAA;
      ElemWeightArray = monoWeightDblElem;
    }
    else                              //  average weight radio button selected
    {
      AAWeightArray = avgWeightDblAA;
      ElemWeightArray = avgWeightDblElem;
    }


      for (int i = 0; i < s.length(); i++)
      {
        ch = s.charAt(i);

        if (Character.isUpperCase(ch)) //  ch is an uppercase letter (ie an amino acid)
        {
          charIndex = weightStrAA.indexOf(ch);
          weightDbl += AAWeightArray[charIndex];

        }
        else if (Character.isLowerCase(ch))  // ch is a lowercase letter (ie an element)
        {
          charIndex = weightStrElem.indexOf(ch);
          weightDbl += ElemWeightArray[charIndex];
        }
        else
        {
          badInput = 1;
          return weightDbl;  // gets out of lookupWeight immediately
        }
      }

    return weightDbl;
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



  }  //  end calc_pI



  /*
   *  ROUND FUNCTION
   */

  //  rounds double values to the nearest hundredths place
  //  or if >100000 converts it to scientific notation
  public StringBuffer roundFxn(double d)
  {
    double dTemp;
    double rounder = 0.00005;
    String dStr = "";
    StringBuffer dBuff = new StringBuffer("");

    //  add the rounder to the input double
    d += rounder;

    int places = 1;
    double div10 = d;

    while (div10 >= 10)
    {
      div10 = div10/10;
      places++;
    }


    //  adds zero's to the beginning of a small number
    if (d < 100000)
    {
      dTemp = d;

      dStr = String.valueOf(dTemp) + "000000";
      dStr = dStr.substring(0,(places + 6));
/*
      int spaces = 5 - places;

      //  add spaces to the beginning
      StringBuffer blankBuff = new StringBuffer("");
      for (int i = 0; i < spaces; i++)
      {
        blankBuff.append(" ");
      }
      dBuff.append(blankBuff);
*/
      dBuff.append(dStr);

    }

    if (d >= 100000)
    {
      dTemp = d;

      dStr = String.valueOf(dTemp);

      dBuff = new StringBuffer(dStr);

      //  delete decimal point
      dBuff.deleteCharAt(places);

      //  reinsert decimal point at scietific notation point
      dBuff.insert(1,'.');

      dStr = (dBuff.toString()).substring(0,5);

      dTemp = Double.valueOf(dStr).doubleValue();
      dTemp += rounder;

      dStr = (dBuff.toString()).substring(0,4);

      dStr = dStr + "e" + String.valueOf(places - 1);
      dBuff = new StringBuffer(dStr);
     }

     return dBuff;

  }  //  end roundFxn

  /*
   *  PAD CENTER FUNCTION
   */

  //  pads the string content with spaces on either side to make it the length in characters of padNum (2nd argument)
  public String padCenter (String content, int padNum)
  {

    int len = content.length();
    int spaces = padNum-len;
    double spacesFront = 0;
    double spacesBack = 0;

    if (spaces > 0)
    {
      double div2 = spaces/2.0;
      if (div2 > Math.floor(div2)) //  odd number of spaces
      {
        spacesFront = Math.floor(div2);
        spacesBack = Math.ceil(div2);
      }
      else //  even number of spaces
      {
        spacesFront = Math.floor(div2);
        spacesBack = Math.floor(div2);
      }
    }

    StringBuffer frontSpace = new StringBuffer("");
    StringBuffer backSpace = new StringBuffer("");
    StringBuffer contentBuff = new StringBuffer(content);

    //  create space in front and back
    for (int i = 0; i < spacesFront; i++)
      frontSpace.append(" ");
    for (int i = 0; i < spacesBack; i++)
      backSpace.append(" ");

    frontSpace.append(contentBuff);
    frontSpace.append(backSpace);
    return frontSpace.toString();

  }


  /*
   *  PAD LEFT FUNCTION
   */

  //  pads the string content with spaces on left to make it right justified
  public StringBuffer padLeft (String content, int padNum)
  {

    int len = content.length();
    int spaces = padNum-len;

    StringBuffer frontSpace = new StringBuffer("");
    StringBuffer contentBuff = new StringBuffer(content);

    //  create space in front
    for (int i = 0; i < spaces; i++)
      frontSpace.append(" ");

    frontSpace.append(contentBuff);
    return frontSpace;

  }


/*  OLD ROUND FXNS


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

    return (dStr);
  }


  OLD ROUND FXN
private double dbl;
private String dStr;

  public String roundFxn (double d)
  {
    dStr = "?";


    if (d < 10)
    {
      dbl = 0.000005 + d;
      dStr = String.valueOf(dbl) + "000";  //  extra zeros added to ensure substring has at least 8 chars
      dStr = dStr.substring(0,8);
    }
    else if ((d < 100) && (d >= 10))
    {
      dbl = 0.00005 + d;
      dStr = String.valueOf(dbl) + "0000";
      dStr = dStr.substring(0,8);
    }

    else if ((d >= 100) && (d < 1000))
    {
      dbl = 0.0005 + d;
      dStr = String.valueOf(dbl) + "00000";
      dStr = dStr.substring(0,8);
    }


    else if ((d >= 1000) && (d < 10000))
    {
      dbl = 0.005 + d;
      dStr = String.valueOf(dbl) + "000000";
      dStr = dStr.substring(0,8);
    }

  return dStr;
  }
*/


}
