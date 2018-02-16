//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short OR=257;
public final static short AND=258;
public final static short IGUALMENORQUE=259;
public final static short IGUALMAYORQUE=260;
public final static short EQUAL=261;
public final static short DISTINT=262;
public final static short MENORQUETIPO=263;
public final static short MAYORQUETIPO=264;
public final static short VAR=265;
public final static short IDENT=266;
public final static short CHAR=267;
public final static short REAL=268;
public final static short INT=269;
public final static short LITINT=270;
public final static short STRUCT=271;
public final static short PRINT=272;
public final static short READ=273;
public final static short IF=274;
public final static short ELSE=275;
public final static short WHILE=276;
public final static short RETURN=277;
public final static short CAST=278;
public final static short LITREAL=279;
public final static short LITCHAR=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    4,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    3,    8,    8,   10,   10,
   11,   11,   12,    9,   13,   13,   15,   14,   14,   16,
   16,   16,   16,   16,   16,   16,   16,   20,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   22,
   23,   23,   18,   24,   24,   25,   19,   21,   21,   26,
   26,   27,
};
final static short yylen[] = {                            2,
    0,    2,    1,    1,    1,    5,    1,    1,    1,    1,
    4,    6,    0,    2,    4,    4,    4,    6,    0,    1,
    1,    3,    3,    2,    0,    2,    5,    0,    2,    3,
    3,    1,    2,    1,    7,   11,    7,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    7,    3,    1,    1,    1,    1,    1,    1,    5,
    0,    4,    4,    2,    3,    1,    4,    0,    1,    1,
    3,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,    0,    2,    3,    4,    5,    0,    0,
    0,    0,   25,    0,    0,    0,    0,   21,   13,    0,
    0,   10,    7,    8,    9,    0,    0,    0,    0,    0,
    0,   16,    0,    0,   26,    0,    6,   23,    0,   22,
    0,    0,   14,    0,    0,    0,    0,    0,    0,    0,
   29,   32,    0,   34,    0,    0,   18,    0,   12,    0,
    0,    0,   64,    0,   54,    0,    0,   55,   56,    0,
   59,   58,    0,    0,    0,    0,    0,    0,   70,   33,
    0,    0,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   30,   31,    0,    0,   38,    0,    0,
   65,    0,    0,   15,   27,    0,   67,    0,   51,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   53,    0,    0,   71,   63,   62,    0,    0,
   28,   28,   60,    0,    0,    0,    0,    0,   37,   52,
    0,   28,    0,   36,
};
final static short yydgoto[] = {                          1,
    5,    6,    7,    8,   27,   31,   43,    9,   20,   16,
   17,   18,   21,   34,   35,   51,   76,   52,   71,   54,
   77,   72,   63,   55,  113,   78,   79,
};
final static short yysindex[] = {                         0,
 -254, -265,  -27, -252,    0,    0,    0,    0, -108,  -18,
 -247,  -82,    0,  -12,  -13,    6,    4,    0,    0,  -55,
 -190,    0,    0,    0,    0, -192,   29,  -12,   41, -247,
 -115,    0, -164,  -61,    0,   12,    0,    0,  -12,    0,
   50,   53,    0,   72,  -31,  -40,  -40,   69,  106,  -40,
    0,    0,   98,    0,   49,  -12,    0,  -12,    0,  -12,
  -40,  -40,    0,  -22,    0,  -40,   99,    0,    0,  365,
    0,    0,  371,  -40,  -40,  404,  105,  122,    0,    0,
  -97,  -40,    0,  112,  119,  392,  142,  -40,   44,  -12,
  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,
  -40,  -40,  -40,    0,    0,   51,   74,    0,  -40,   93,
    0,  404,  127,    0,    0,   93,    0,  398,    0,  130,
  426,  432,  216,  216,  216,  216,  216,  216,  186,  186,
  143,  143,    0,   73,   76,    0,    0,    0,   93,  155,
    0,    0,    0,  -40,   19,   52,   81,  -73,    0,    0,
   86,    0,  232,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  169,    0,    0,    0,    0,    0,  176,    0,    0,    0,
  252,    0,    0,    0,    0,    0,    0,    0,  102,    0,
    0,    0,    0,  104,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   79,    0,    0,    0,    0,  168,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  193,    0,  -16,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   59,    0,  -17,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   79,
    0,  177,    0,    0,    0,  -39,    0,    0,    0,    0,
  121,    8,  101,  108,  114,  131,  138,  144,   88,   94,
   14,   21,    0,    0,    0,    0,    0,    0,   -9,    0,
    0,    0,    0,    0,    0,    0,    0,  261,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,  491,    0,    0,    0,    0,    0,
    0,  205,    0,  -70,    0,    0,  470,    0,  174,    0,
  185,    0, -100,  179,    0,    0,  158,
};
final static int YYTABLESIZE=694;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         66,
   10,   61,   61,   61,   61,   61,   61,   61,   62,   42,
    2,    3,   11,   12,   13,  138,    4,   62,   15,   61,
   61,   61,   61,   69,   57,   57,   57,   57,   57,   57,
   57,   61,   61,   61,   61,   61,   61,   61,  143,   14,
   19,   69,   57,   57,   28,   57,   29,   30,   45,   61,
   61,   45,   61,   61,   49,   49,   49,   49,   49,   61,
   49,   50,   50,   50,   50,   50,   45,   50,   88,   32,
  145,  146,   49,   49,   33,   49,   57,   36,   26,   50,
   50,  153,   50,   61,  119,  101,   99,   37,  100,  103,
  102,  134,  101,   99,   81,  100,  103,  102,   39,   72,
   45,   44,   72,   94,   56,   93,   49,   58,   74,   82,
   94,   59,   93,   50,  135,  101,   99,   72,  100,  103,
  102,  150,  101,   99,   61,  100,  103,  102,   47,   60,
   47,   47,   47,   94,   48,   93,   48,   48,   48,   61,
   94,   43,   93,  148,   43,   75,   47,   47,   41,   47,
   41,   41,   48,   48,   42,   48,   80,   42,   90,   43,
   43,   46,   43,  108,   46,  109,   41,   41,  110,   41,
  114,   44,   42,   42,   44,   42,  149,  115,   39,   46,
   47,   39,  117,   61,   40,  137,   48,   40,  103,   44,
   44,  140,   44,   43,  144,  141,   39,   39,  142,   39,
   41,  151,   40,   40,   45,   40,   42,   53,  152,   19,
   46,   47,   48,   46,   49,   50,   20,   61,   61,   61,
   61,   61,   61,   44,   17,   64,   68,  101,   24,   65,
   39,  103,  102,   68,   40,   66,   40,   67,   68,   69,
   57,   57,   57,   57,   57,   57,   87,   61,   61,   61,
   61,   61,   61,   22,   23,   24,   25,  101,   99,  111,
  100,  103,  102,    0,   45,   45,  136,    0,    0,    0,
   49,   49,   49,   49,   49,   49,    0,   50,   50,   50,
   50,   50,   50,    0,   45,    0,    0,    0,    0,    0,
   46,   47,   48,    0,   49,   50,    0,    0,    0,    0,
   91,   92,   95,   96,   97,   98,    0,   91,   92,   95,
   96,   97,   98,    0,    0,    0,    0,   45,   53,   53,
    0,    0,    0,   46,   47,   48,   53,   49,   50,    0,
   91,   92,   95,   96,   97,   98,    0,   91,   92,   95,
   96,   97,   98,    0,   47,   47,   47,   47,   47,   47,
   48,   48,   48,   48,   48,   48,  154,   43,   43,   43,
   43,   43,   43,    0,   41,   41,   41,   41,   41,   41,
   42,   42,   42,   42,   42,   42,   28,   46,    0,    0,
    0,    0,    0,    0,    0,   35,    0,   44,   44,   44,
   44,   44,   44,    0,   39,   39,   39,   39,   39,   39,
   40,   40,   40,   40,   40,   40,  101,   99,    0,  100,
  103,  102,  101,   99,    0,  100,  103,  102,    0,    0,
    0,    0,    0,  104,   94,    0,   93,    0,    0,  105,
   94,    0,   93,  101,   99,    0,  100,  103,  102,  101,
   99,    0,  100,  103,  102,  101,   99,    0,  100,  103,
  102,   94,    0,   93,    0,    0,    0,   94,    0,   93,
    0,    0,    0,   94,    0,   93,    0,  101,   99,    0,
  100,  103,  102,  101,   99,    0,  100,  103,  102,    0,
    0,    0,    0,    0,  116,   94,    0,   93,    0,    0,
  139,   94,    0,   93,    0,    0,    0,   45,    0,    0,
    0,    0,    0,   46,   47,   48,    0,   49,   50,    0,
    0,    0,    0,    0,    0,   70,   73,   28,   38,    0,
    0,    0,    0,   28,   28,   28,   35,   28,   28,   57,
   86,    0,   35,   35,   35,   89,   35,   35,    0,    0,
    0,    0,    0,  106,  107,    0,   83,    0,   84,    0,
   85,  112,    0,    0,    0,    0,    0,  118,    0,    0,
  121,  122,  123,  124,  125,  126,  127,  128,  129,  130,
  131,  132,  133,    0,    0,    0,    0,    0,    0,    0,
  120,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  147,    0,    0,    0,    0,    0,    0,
    0,   91,   92,   95,   96,   97,   98,   91,   92,   95,
   96,   97,   98,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   91,   92,
   95,   96,   97,   98,   91,   92,   95,   96,   97,   98,
   91,   92,   95,   96,   97,   98,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,   95,   96,   97,   98,    0,    0,
   95,   96,   97,   98,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  266,   41,   42,   43,   44,   45,   46,   47,   40,  125,
  265,  266,   40,  266,  123,  116,  271,   40,  266,   59,
   60,   61,   62,   41,   41,   42,   43,   44,   45,   46,
   47,   41,   42,   43,   44,   45,   46,   47,  139,   58,
  123,   59,   59,   60,   58,   62,   41,   44,   41,   59,
   60,   44,   62,   93,   41,   42,   43,   44,   45,   91,
   47,   41,   42,   43,   44,   45,   59,   47,   91,  125,
  141,  142,   59,   60,  265,   62,   93,  270,   91,   59,
   60,  152,   62,   93,   41,   42,   43,   59,   45,   46,
   47,   41,   42,   43,   46,   45,   46,   47,   58,   41,
   93,  266,   44,   60,   93,   62,   93,   58,   40,   61,
   60,   59,   62,   93,   41,   42,   43,   59,   45,   46,
   47,   41,   42,   43,   46,   45,   46,   47,   41,   58,
   43,   44,   45,   60,   41,   62,   43,   44,   45,   61,
   60,   41,   62,  125,   44,   40,   59,   60,   41,   62,
  266,   44,   59,   60,   41,   62,   59,   44,   60,   59,
   60,   41,   62,   59,   44,   44,   59,   60,  266,   62,
   59,   41,   59,   60,   44,   62,  125,   59,   41,   59,
   93,   44,   41,   91,   41,   59,   93,   44,   46,   59,
   60,   62,   62,   93,   40,  123,   59,   60,  123,   62,
   93,  275,   59,   60,  266,   62,   93,   34,  123,   41,
  272,  273,  274,   93,  276,  277,   41,  257,  258,  259,
  260,  261,  262,   93,  123,  266,   59,   42,  125,  270,
   93,   46,   47,   41,   30,   59,   93,  278,  279,  280,
  257,  258,  259,  260,  261,  262,   62,  257,  258,  259,
  260,  261,  262,  266,  267,  268,  269,   42,   43,   81,
   45,   46,   47,   -1,  257,  258,  109,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,   -1,  266,   -1,   -1,   -1,   -1,   -1,
  272,  273,  274,   -1,  276,  277,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,  266,  145,  146,
   -1,   -1,   -1,  272,  273,  274,  153,  276,  277,   -1,
  257,  258,  259,  260,  261,  262,   -1,  257,  258,  259,
  260,  261,  262,   -1,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,  125,  257,  258,  259,
  260,  261,  262,   -1,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,  125,  257,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  125,   -1,  257,  258,  259,
  260,  261,  262,   -1,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,   42,   43,   -1,   45,
   46,   47,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   59,
   60,   -1,   62,   42,   43,   -1,   45,   46,   47,   42,
   43,   -1,   45,   46,   47,   42,   43,   -1,   45,   46,
   47,   60,   -1,   62,   -1,   -1,   -1,   60,   -1,   62,
   -1,   -1,   -1,   60,   -1,   62,   -1,   42,   43,   -1,
   45,   46,   47,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   93,   60,   -1,   62,   -1,   -1,
   93,   60,   -1,   62,   -1,   -1,   -1,  266,   -1,   -1,
   -1,   -1,   -1,  272,  273,  274,   -1,  276,  277,   -1,
   -1,   -1,   -1,   -1,   -1,   46,   47,  266,   28,   -1,
   -1,   -1,   -1,  272,  273,  274,  266,  276,  277,   39,
   61,   -1,  272,  273,  274,   66,  276,  277,   -1,   -1,
   -1,   -1,   -1,   74,   75,   -1,   56,   -1,   58,   -1,
   60,   82,   -1,   -1,   -1,   -1,   -1,   88,   -1,   -1,
   91,   92,   93,   94,   95,   96,   97,   98,   99,  100,
  101,  102,  103,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   90,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  144,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  257,  258,  259,  260,  261,  262,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  258,  259,  260,  261,  262,   -1,   -1,
  259,  260,  261,  262,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"\"OR\"","\"AND\"","\"IGUALMENORQUE\"",
"\"IGUALMAYORQUE\"","\"EQUAL\"","\"DISTINT\"","MENORQUETIPO","MAYORQUETIPO",
"\"VAR\"","\"IDENT\"","\"CHAR\"","\"REAL\"","\"INT\"","\"LITINT\"","\"STRUCT\"",
"\"PRINT\"","\"READ\"","\"IF\"","\"ELSE\"","\"WHILE\"","\"RETURN\"","\"CAST\"",
"\"LITREAL\"","\"LITCHAR\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa :",
"programa : programa definiciones",
"definiciones : definicionStruct",
"definiciones : definicionFuncion",
"definiciones : definicionVariable",
"definicionVariable : \"VAR\" \"IDENT\" ':' tipo ';'",
"tipo : \"CHAR\"",
"tipo : \"REAL\"",
"tipo : \"INT\"",
"tipo : \"IDENT\"",
"tipo : '[' \"LITINT\" ']' tipo",
"definicionStruct : \"STRUCT\" \"IDENT\" '{' atributos '}' ';'",
"atributos :",
"atributos : atributos atributo",
"atributo : \"IDENT\" ':' tipo ';'",
"definicionFuncion : cabecera '{' cuerpo '}'",
"cabecera : \"IDENT\" '(' parametrosDeclaracion ')'",
"cabecera : \"IDENT\" '(' parametrosDeclaracion ')' ':' tipo",
"parametrosDeclaracion :",
"parametrosDeclaracion : listaDeclaracion",
"listaDeclaracion : parametroDeclaracion",
"listaDeclaracion : listaDeclaracion ',' parametroDeclaracion",
"parametroDeclaracion : \"IDENT\" ':' tipo",
"cuerpo : declaracionVariable sentencias",
"declaracionVariable :",
"declaracionVariable : declaracionVariable dec",
"dec : \"VAR\" \"IDENT\" ':' tipo ';'",
"sentencias :",
"sentencias : sentencias sentencia",
"sentencia : \"PRINT\" expr ';'",
"sentencia : \"READ\" expr ';'",
"sentencia : asignacion",
"sentencia : llamadaFuncion ';'",
"sentencia : return",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}'",
"sentencia : \"IF\" '(' expr ')' '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"WHILE\" '(' expr ')' '{' sentencias '}'",
"return : \"RETURN\" parametrosLlamada ';'",
"expr : expr \"EQUAL\" expr",
"expr : expr \"DISTINT\" expr",
"expr : expr '<' expr",
"expr : expr \"IGUALMENORQUE\" expr",
"expr : expr '>' expr",
"expr : expr \"IGUALMAYORQUE\" expr",
"expr : expr \"AND\" expr",
"expr : expr \"OR\" expr",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : '(' expr ')'",
"expr : \"CAST\" '<' tipo '>' '(' expr ')'",
"expr : expr '.' expr",
"expr : \"LITINT\"",
"expr : \"LITREAL\"",
"expr : \"LITCHAR\"",
"expr : \"IDENT\"",
"expr : accesoArray",
"expr : llamadaFuncion",
"accesoArray : \"IDENT\" '[' expr ']' recursividadArray",
"recursividadArray :",
"recursividadArray : '[' expr ']' recursividadArray",
"asignacion : parteIzquierda '=' parteDerecha ';'",
"parteIzquierda : \"IDENT\" recursividadArray",
"parteIzquierda : parteIzquierda '.' parteIzquierda",
"parteDerecha : expr",
"llamadaFuncion : \"IDENT\" '(' parametrosLlamada ')'",
"parametrosLlamada :",
"parametrosLlamada : listaLlamada",
"listaLlamada : parametroLlamada",
"listaLlamada : listaLlamada ',' parametroLlamada",
"parametroLlamada : expr",
};

//#line 162 "sintac.y"
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
//#line 488 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
