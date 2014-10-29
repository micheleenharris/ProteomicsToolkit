import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.math.*;


public class DNAServlet extends HttpServlet
{


  //  ************************** TRANSLATION TABLES **************************

  /*  GENERAL:
   *  This list contains 14 translation tables for nucleotides.
   *  Each table has an array of strings representing the codons
   *  and a string containing the cooresponding single letter
   *  amino acids.

   *  RULES FOR AMBIGUOUS CODONS:
   *  Letter   Nucleotides
   *    M        A/C
   *    R        A/G
   *    W        A/T
   *    S        C/G
   *    Y        C/T
   *    K        G/T
   *    V        A/C/G
   *    H        A/C/T
   *    D        A/G/T
   *    B        C/G/T
   *    X/N      G/A/T/C

   */


  /*
   *  1.  STANDARD TRANSLATION CODE
   */

  public String [] stdTranCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX", 
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TGA", "TAR", "TRA"};


  public String stdTranCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLLLMNNNPPPPPQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWXYYYZ.*****";


  //  Start codons
  public String [] stdTranCodeStart = { "ATG" };


  /*
   *  2.  DROSOPHILA MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] drosMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATY",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG", "ATA", "ATR",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "CGX",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "AGA", "AGG", "TCX", "AGX",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR"};


  public String drosMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIKKKLLLLLLLLLMMMNNNPPPPPQQQRRRRRSSSSSSSSSSTTTTTVVVVVWWXYYYZ.***";


  //  Start codons
  public String [] drosMitoCodeStart = {"ATT", "ATG" };

  /*
   *  3.  VERTEBRATE MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] vertMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATY",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG", "ATA", "ATR",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "CGX",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA", "TGR",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "AGA", "AGG", "TAA", "TAG", "TAR", "AGR"};


  public String vertMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIKKKLLLLLLLLLMMMNNNPPPPPQQQRRRRRSSSSSSSSTTTTTVVVVVWWWXYYYZ.******";


  //  Start codons
  public String [] vertMitoCodeStart = {"ATT", "ATG", "ATC", "ATA" };


  /*
   *  4.  YEAST MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] yeastMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATY",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "TTR",
 /* M        Met */         "ATG", "ATA",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX", "CTT", "CTA", "CTC", "CTG", "CTX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA", "TGR",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR"};


  public String yeastMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIKKKLLLMMNNNPPPPPQQQRRRRRRRRRSSSSSSSSTTTTTTTTTTVVVVVWWWXYYYZ.***";

  //  Start codons
  public String [] yeastMitoCodeStart = { "ATG" };


  /*
   *  5.  MOLD, PROTOZOAN, AND COELENTERATE MITOCHONDRIAL AND MYCOPLASMA/SPIROPLASMA TRANSLATION CODE
   */

  public String [] moldProtoMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA", "TGR",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR"};


  public String moldProtoMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLLLMNNNPPPPPQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWWWXYYYZ.***";

  //  Start codons
  public String [] moldProtoMitoCodeStart = {"ATT", "ATC", "ATA", "ATG", "TTA", "TTG", "CTG", "GTG" };


  /*
   *  6.  INVERTEBRATE MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] invertMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG", "ATA",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "CGX",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "AGA", "AGG", "TCX", "AGX",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA", "TGR",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR",};


  public String invertMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIKKKLLLLLLLLLMMNNNPPPPPQQQRRRRRSSSSSSSSSSTTTTTVVVVVWWWXYYYZ.***";

  //  Start codons
  public String [] invertMitoCodeStart = {"ATT", "ATC", "TTG", "ATG", "ATA", "GTG" };

  /*
   *  7.  CILIATE, DASYCLADACEAN AND HEXAMITA NUCLEAR TRANSLATION CODE
   */

  public String [] cilDasHexNucCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR", "TAG", "TAA", "TAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TGA"};


  public String cilDasHexNucCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLLLMNNNPPPPPQQQQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWXYYYZ.*";

  //  Start codons
  public String [] cilDasHexNucCodeStart = { "ATG" };

  /*
   *  8.  ECHINODERM MITOCHONDRIAL TRANSLATION CODE
   */


  public String [] echinoMitoCode = {
/* A        Ala  */       "GCT", "GCC", "GCA", "GCG", "GCX",
/* B        Asx  */       "RAY",
/* C        Cys  */       "TGT", "TGC", "TGY",
/* D        Asp  */       "GAT", "GAC", "GAY",
/* E        Glu  */       "GAA", "GAG", "GAR",
/* F        Phe  */       "TTT", "TTC", "TTY",
/* G        Gly  */       "GGT", "GGC", "GGA", "GGG", "GGX",
/* H        His  */       "CAT", "CAC", "CAY",
/* I        Ile  */       "ATT", "ATC", "ATA", "ATH",
/* K        Lys  */       "AAG", "AAG",
/* L        Leu  */       "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
/* M        Met  */       "ATG",
/* N        Asn  */       "AAT", "AAC", "AAA", "AAH",
/* P        Pro  */       "CCT", "CCC", "CCA", "CCG", "CCX",
/* Q        Gln  */       "CAA", "CAG", "CAR",
/* R        Arg  */       "CGT", "CGC", "CGA", "CGG", "CGX",
/* S        Ser  */       "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "AGA", "AGG", "TCX", "AGH",
/* T        Thr  */       "ACT", "ACC", "ACA", "ACG", "ACX",
/* V        Val  */       "GTT", "GTC", "GTA", "GTG", "GTX",
/* W        Trp  */       "TGG", "TGA", "TGR",
/* X        Xxx  */       "XXX",
/* Y        Tyr  */       "TAT", "TAC", "TAY",
/* Z        Glx  */       "SAR",
/* .        ...  */       "...",
/* *        End  */       "TAA", "TAG", "TAR"};


  public String echinoMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKLLLLLLLLLMNNNNPPPPPQQQRRRRRSSSSSSSSSSTTTTTVVVVVWWWXYYYZ.***";

  //  Start codons
  public String [] echinoMitoCodeStart = { "ATG" };

  /*
   *  9.  EUPLOTID NUCLEAR TRANSLATION CODE
   */



  public String [] euplotidNucCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGA", "TGH",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR"};


  public String euplotidNucCodeAA = "AAAAABCCCCDDDEEEFFFGGGGGHHHIIIIKKLLLLLLLLLMNNNPPPPPQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWXYYYZ.***";

  //  Start codons
  public String [] euplotidNucCodeStart = { "ATG" };


  /*
   *  10.  BACTERIAL TRANSLATION CODE
   */

  public String [] bacterialTranCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR", "YTX",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TGA", "TAR", "TRA"};


  public String bacterialTranCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLLLLMNNNPPPPPQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWXYYYZ.*****";

  //  Start codons
  public String [] bacterialTranCodeStart = {"ATT", "TTG", "CTG", "ATG", "GTG" };


  /*
   *  11.  ALTERNATIVE YEAST TRANSLATION CODE
   */

  public String [] altYeastCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "TTR", "CTH",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "CTG", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TGA", "TAR", "TRA"};


  public String altYeastCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLMNNNPPPPPQQQRRRRRRRRRSSSSSSSSSTTTTTVVVVVWXYYYZ.*****";

  //  Start codons
  public String [] altYeastCodeStart = { "ATG", "CTG" };


  /*
   *  12.  ASCIDIAN MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] ascidianMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "AGA", "AGG", "GGX", "AGR",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATY",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG", "ATA", "ATR",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "CGX",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA", "TGR",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR"};


  public String ascidianMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGGGGHHHIIIKKKLLLLLLLLLMMMNNNPPPPPQQQRRRRRSSSSSSSSTTTTTVVVVVWWWXYYYZ.***";

  //  Start codons
  public String [] ascidianMitoCodeStart = { "ATG" };


  /*
   *  13.  FLATWORM MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] flatMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAA", "AAH",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "CAR",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "CGX",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "AGA", "AGG", "TCX", "AGX",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG", "TGA", "TGR",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAA", "TAH",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAG"};


  public String flatMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLLLMNNNNPPPPPQQQRRRRRSSSSSSSSSSTTTTTVVVVVWWWXYYYYZ.*";

  //  Start codons
  public String [] flatMitoCodeStart = { "ATG" };

  /*
   *  14.  BLEPHARISMA MITOCHONDRIAL TRANSLATION CODE
   */

  public String [] blephMitoCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
 /* D        Asp */         "GAT", "GAC", "GAY",
 /* E        Glu */         "GAA", "GAG", "GAR",
 /* F        Phe */         "TTT", "TTC", "TTY",
 /* G        Gly */         "GGT", "GGC", "GGA", "GGG", "GGX",
 /* H        His */         "CAT", "CAC", "CAY",
 /* I        Ile */         "ATT", "ATC", "ATA", "ATH",
 /* K        Lys */         "AAA", "AAG", "AAR",
 /* L        Leu */         "TTG", "TTA", "CTT", "CTC", "CTA", "CTG", "TTR", "CTX", "YTR",
 /* M        Met */         "ATG",
 /* N        Asn */         "AAT", "AAC", "AAY",
 /* P        Pro */         "CCT", "CCC", "CCA", "CCG", "CCX",
 /* Q        Gln */         "CAA", "CAG", "TAG", "CAR", "YAG",
 /* R        Arg */         "CGT", "CGC", "CGA", "CGG", "AGA", "AGG", "CGX", "AGR", "MGR",
 /* S        Ser */         "TCT", "TCC", "TCA", "TCG", "AGT", "AGC", "TCX", "AGY",
 /* T        Thr */         "ACT", "ACC", "ACA", "ACG", "ACX",
 /* V        Val */         "GTT", "GTC", "GTA", "GTG", "GTX",
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TGA", "TRA"};


  public String blephMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKKLLLLLLLLLMNNNPPPPPQQQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWXYYYZ.***";

  //  Start codons
  public String [] blephMitoCodeStart = { "ATG" };



  // ************************** SERVLET CODE ************************** 


  private String sequence, code, stopFormat, outputSeq, key, processedSequence;
  private PrintWriter out;

  //  indicates whether we've reached a stop codon or not
  private int reachStop = 0; // false

  //  indicates whether encountered an unknown codon
  private int unknownStatus;

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

  //  retrieves the user choosen character for the stop codon
  stopFormat = req.getParameter("stopFormat");

  //  retrieves the user choosen genetic code
  code = req.getParameter("code");

  //  retrieves the user entered sequence
  sequence = req.getParameter("sequence");

  //  retrieves whether or not user wishes to include sequence in output (from a checkbox)
  outputSeq = req.getParameter("outputSeq");


  if ( (sequence != null) && (sequence.length() > 0) )
  {

  sequence = sequence.toUpperCase();

  processedSequence = processSeq(sequence);

  /*
   *  CREATION OF HTML DOC FOR OUTPUT
   */

  out.println("<HTML>");
  out.println("<HEAD><TITLE>Output Page</TITLE></HEAD><BODY>");

  out.println("<CENTER><H2>Translation Results</H2></CENTER>");

  //  format the output
  out.println("<FONT SIZE=+1>");

  //  prints out a legend specific to user options
  out.println(makeKey());

  //  if user requests to see the sequence it will be displayed without whitespace/numbers
  String displaySeq = "";
  if (outputSeq != null) // (outputSeq.equals("yes"))
  {
    out.println("Results for sequence <BR><TT>");
    StringBuffer tempSeq = new StringBuffer("");
    int countNuc = 0;
    for (int i = 0; i < sequence.length(); i++)
    {
      if (Character.isLetter(sequence.charAt(i)))
      {
        tempSeq.append(sequence.charAt(i));
        countNuc++;
      }
      if (countNuc == 50 && i != (sequence.length()-1))  //  inserts a break so sequence wraps after 50 characters
      {
        tempSeq.append("<BR>");  
        countNuc = 0;  // reset the counter
      }
    }
    displaySeq = tempSeq.toString();
    out.println(displaySeq + "</TT><BR>");
  }

  out.println("<BR>Using <B>" + code + "</B> translation code " );
  out.println("<BR><BR>" + processedSequence);


  out.println("</FONT></BODY></HTML>");

  }

}  // end doGet


  /*
   *  GET SERVLET INFO
   */


  public String getServletInfo()
  {
    return "This application tranlates a DNA or RNA sequence into 6 peptides, representing the 6 possible reading frames.  Created by ISB, 11 July 2001.";
  }

  //  ************************** HELPER METHODS **************************

  /*
      PROCESS SEQUENCE
   

      This subroutine takes the whole sequence as entered by the user.
      It removes anything that is not a letter.  The six reading frames
      are formed, and these sequences sent to the match() subroutine
      which returns a string containing the amino acid equivalents of
      each codon.
   */

  public String processSeq (String seq)
  {


    unknownStatus = 0;  // reinitialize this to 0

    //  take out any spaces and numbers
    StringBuffer tempSeq = new StringBuffer("");;
    for (int i = 0; i < seq.length(); i++)
    {
      if (Character.isLetter(seq.charAt(i)))
        tempSeq.append(seq.charAt(i));
      else
        continue;
    }
    seq = tempSeq.toString();

    if (seq.length() > 0)
    {

    //  the three 5'3' reading frame translations

    String fiveThreeFrame1 = match (seq);
    String fiveThreeFrame2 = match (seq.substring(1, seq.length()));
    String fiveThreeFrame3 = match (seq.substring(2, seq.length()));

 

    //  reverse and complement the string seq and rename rev

    String rev = "";
    for (int i = 0; i < seq.length(); i++)
    {
      //  the unambiguous nucleotides
      if (seq.charAt(i) == 'A')
        rev = "U" + rev;
      else if ((seq.charAt(i) == 'U') || (seq.charAt(i) == 'T'))
        rev = "A" + rev;
      else if (seq.charAt(i) == 'C')
        rev = "G" + rev;
      else if (seq.charAt(i) == 'G')
        rev = "C" + rev;
      else // any other non-nucleotides
        rev = String.valueOf (seq.charAt(i)) + rev;
    }


    //  the three 3'5' reading frame translations

    String threeFiveFrame1 = match (rev); 
    String threeFiveFrame2 = match (rev.substring(1, rev.length()));
    String threeFiveFrame3 = match (rev.substring(2, rev.length()));

    return ("5'3' Frame1: " + "<BR>" + fiveThreeFrame1 + 
            "<BR><BR>5'3' Frame2: " + "<BR>" + fiveThreeFrame2 +
            "<BR><BR>5'3' Frame3: " + "<BR>" + fiveThreeFrame3 +
            "<BR><BR>3'5' Frame1: " + "<BR>" + threeFiveFrame1 +
            "<BR><BR>3'5' Frame2: " + "<BR>" + threeFiveFrame2 +
            "<BR><BR>3'5' Frame3: " + "<BR>" + threeFiveFrame3 + "<BR>");
  }

  return "";
  } //  end processSeq() 

  /* 
      MATCH


      This subroutine takes the sequence and breaks it up into codons starting with the first
      nucleotide.  It sends the codon to the lookupCodon subroutine which matches it to
      an amino acid.  The amino acid sequence is then returned as a String.
  */

  public String match (String frameSeq)
  {
    //  keeps track of how many times match is called (i.e. how many codons/AA's there are)    
 

    StringBuffer newSeq = new StringBuffer ("");
    int begin = 0;
    int end = 3;
    int countAA = 0;

    for (int i = 0;  i < frameSeq.length(); i += 3)
    {
      //  keeps track of how many codons/AA's there are so that breaks can be inserted
      countAA++;

      if (frameSeq.length() < 3)
        break;
      String codon = frameSeq.substring(begin,end);  //  takes one codon at a time

      String letter = lookupCodon(codon);
        
      newSeq.append(letter);

      begin +=3;
      end +=3;

      if ((end > frameSeq.length())||(begin > frameSeq.length()-1))  //  reached end of translational sequence
        break;
      else if (countAA == 50)  //  format the output 50 chars per line
      {
        newSeq.append("<BR>");
        countAA = 0;  //  reset counter
      }
    }
  
    //  returns the sequence in typewriter font (for standard spacing)
   return ("<TT>" + newSeq.toString() + "</TT>");
  }  //  end match()



  /*
      LOOKUP CODON
    

      This subroutine takes three characters (the codon) sent from the match()
      subroutine and compares it to the translation table specific to the
      genetic code selected by the user.  It returns a letter cooresponding to
      the amino acid match for the codon.  The code for each lookup is the same
      for each table, but must be written specific to each since each table is
      different.  Place for improvement:  a modular function could be written
      to handle each lookup where only the name of the array would change.
  */


  public String lookupCodon( String codon )
  {
    // capitalize and replace U's with T's because the code tables are in terms of T's
    codon = codon.toUpperCase();
    codon = codon.replace('U','T');



    String letter = "";
    String outputStr = "";

    if (code.equals("Standard"))
    {
        for (int i = 0; i < stdTranCodeAA.length(); i++)
        {

          letter = String.valueOf(stdTranCodeAA.charAt(i));  //  takes one char as a string

          if ( stdTranCode[i].equals( codon ) )
          {
            for (int j = 0; j < stdTranCodeStart.length; j++)
            {
              if ( stdTranCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Drosophila Mitochondrial"))
    {
        for (int i = 0; i < drosMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(drosMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( drosMitoCode[i].equals( codon ) )
          {
            if ( ( letter.equals("I") ) || ( letter.equals("M") ) )
            {
              return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }
    
    else if (code.equals("Vertebrate Mitochondrial"))
    {
        for (int i = 0; i < vertMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(vertMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( vertMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < vertMitoCodeStart.length; j++)
            {
              if ( vertMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Yeast Mitochondrial"))
    {
        for (int i = 0; i < yeastMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(yeastMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( yeastMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < yeastMitoCodeStart.length; j++)
            {
              if ( yeastMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Mold Protozoan Coelenterate Mycoplasma Mitochondrial"))
    {
        for (int i = 0; i < moldProtoMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(moldProtoMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( moldProtoMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < moldProtoMitoCodeStart.length; j++)
            {
              if ( moldProtoMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Invertebrate Mitochondrial"))
    {
        for (int i = 0; i < invertMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(invertMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( invertMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < invertMitoCodeStart.length; j++)
            {
              if ( invertMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Ciliate Dasycladacean Hexamita Nuclear"))
    {
        for (int i = 0; i < cilDasHexNucCodeAA.length(); i++)
        {

          letter = String.valueOf(cilDasHexNucCodeAA.charAt(i));  //  takes one char as a string

          if ( cilDasHexNucCode[i].equals( codon ) )
          {
            for (int j = 0; j < cilDasHexNucCodeStart.length; j++)
            {
              if ( cilDasHexNucCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Echinoderm Mitochondrial"))
    {
        for (int i = 0; i < echinoMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(echinoMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( echinoMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < echinoMitoCodeStart.length; j++)
            {
              if ( echinoMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Euplotid Nuclear"))
    {
        for (int i = 0; i < euplotidNucCodeAA.length(); i++)
        {

          letter = String.valueOf(euplotidNucCodeAA.charAt(i));  //  takes one char as a string

          if ( euplotidNucCode[i].equals( codon ) )
          {
            if ( letter.equals("M") )
            {
              return ("<B><FONT COLOR=red>M</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Bacterial"))
    {
        for (int i = 0; i < bacterialTranCodeAA.length(); i++)
        {

          letter = String.valueOf(bacterialTranCodeAA.charAt(i));  //  takes one char as a string

          if ( bacterialTranCode[i].equals( codon ) )
          {
            for (int j = 0; j < bacterialTranCodeStart.length; j++)
            {
              if ( bacterialTranCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Alternative Yeast Nuclear"))
    {
        for (int i = 0; i < altYeastCodeAA.length(); i++)
        {

          letter = String.valueOf(altYeastCodeAA.charAt(i));  //  takes one char as a string

          if ( altYeastCode[i].equals( codon ) )
          {
            for (int j = 0; j < altYeastCodeStart.length; j++)
            {
              if ( altYeastCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Ascidian Mitochondrial"))
    {
        for (int i = 0; i < ascidianMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(ascidianMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( ascidianMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < ascidianMitoCodeStart.length; j++)
            {
              if ( ascidianMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }

            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else if (code.equals("Flatworm Mitochondrial"))
    {
        for (int i = 0; i < flatMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(flatMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( flatMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < flatMitoCodeStart.length; j++)
            {
              if ( flatMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }
            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    else // (code.equals("Blepharisma Mitochondrial"))
    {
        for (int i = 0; i < blephMitoCodeAA.length(); i++)
        {

          letter = String.valueOf(blephMitoCodeAA.charAt(i));  //  takes one char as a string

          if ( blephMitoCode[i].equals( codon ) )
          {
            for (int j = 0; j < blephMitoCodeStart.length; j++)
            {
              if ( blephMitoCodeStart[j].equals( codon ) )
                return ("<B><FONT COLOR=red>" + letter + "</FONT></B>");  //  colors the start AA red
            }
            if (letter.equals("*"))
            {
              if (stopFormat.length() > 0)
                return ("<B><FONT COLOR=blue>" + String.valueOf(stopFormat.charAt(0)) + "</FONT></B>");
              else
                return ("<B><FONT COLOR=blue>*</B></FONT>");
            }
            return letter;  //  found letter and it is not from a stop or start codon
          }
          else
            continue;  //  not found so continue searching
        }

    }

    unknownStatus = 1;  //  encountered an unknown codon so print this in legend

    return "<FONT COLOR=#ff6633><B>u</B></FONT>";  // unknown codon


  }

  /* 
      MAKE KEY
   
   
      This subroutine creates the key based upon what serves as a start and stop
      codon.  The start amino acid(s) is/are colored red and the stop is blue.
  */

  public String makeKey ()
  {
    String stop = "*";  // default

    if (stopFormat.length() > 0)
      stop = String.valueOf(stopFormat.charAt(0));

    key = "<BR>";

    if (code.equals("Standard"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><B><FONT COLOR=red>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Drosophila Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>I M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Vertebrate Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>I M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Yeast Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Mold Protozoan Coelenterate Mycoplasma Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>I L M V</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Invertebrate Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>I L M V</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Ciliate Dasycladacean Hexamita Nuclear"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Echinoderm  Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Euplotid Nuclear"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");
    }

    else if (code.equals("Bacterial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>I L M V</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");

    }

    else if (code.equals("Alternative Yeast Nuclear"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M S</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");

    }

    else if (code.equals("Ascidian Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");

    }

    else if (code.equals("Flatworm Mitochondrial"))
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");

    }

    else
    {
      key += ("<HR><B>Key:</B><BR>Start: <TT><FONT COLOR=red><B>M</B></FONT></TT>" +
            "<BR>Stop: <TT><FONT COLOR=blue><B>" + stop + "</B></FONT></TT>");

    }

    if (unknownStatus == 1)
      key += ("<BR>Unknown codon: <TT><FONT COLOR=#ff6633><B>u</B></FONT><BR></TT>");


    return (key + "<HR>");

}

}//  end class DNATranslatorServlet
