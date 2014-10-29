public class TranslationTable
{

  /*  GENERAL:
   *  This class contains 14 translation tables for nucleotides.
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
 /* W        Trp */         "TGG",
 /* X        Xxx */         "XXX",
 /* Y        Tyr */         "TAT", "TAC", "TAY",
 /* Z        Glx */         "SAR",
 /* .        ... */         "...",
 /* *        End */         "TAA", "TAG", "TAR"};


  public String drosMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIKKKLLLLLLLLLMMMNNNPPPPPQQQRRRRRSSSSSSSSSSTTTTTVVVVVWXYYYZ.***";





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


  public String echinoMitoCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIILLNNNNPPPPPQQQRRRRRSSSSSSSSSSTTTTTWWWXYYYZ.***";       





 
  /*
   *  9.  EUPLOTID NUCLEAR TRANSLATION CODE
   */ 
 


  public String [] euplotidNucCode = {

 /* A        Ala */         "GCT", "GCC", "GCA", "GCG", "GCX",
 /* B        Asx */         "RAY",
 /* C        Cys */         "TGT", "TGC", "TGY",
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


  public String euplotidNucCodeAA = "AAAAABCCCDDDEEEFFFGGGGGHHHIIIIKKLLLLLLLLLMNNNPPPPPQQQRRRRRRRRRSSSSSSSSTTTTTVVVVVWXYYYZ.***";



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




}
