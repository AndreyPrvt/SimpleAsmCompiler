package com.sp.Utils;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String RESOURCES_PATH = "resources/";

    public static final BigInteger BYTES_16 = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);
    public static final BigInteger BYTES_8 = new BigInteger("FFFFFFFFFFFFFFFF", 16);
    public static final BigInteger BYTES_4 = new BigInteger("FFFFFFFF", 16);
    public static final BigInteger BYTES_2 = new BigInteger("FFFF", 16);
    public static final BigInteger BYTES_1 = new BigInteger("FF", 16);
    public static final BigInteger ONE = BigInteger.ONE;
    public static final BigInteger ZERO = BigInteger.ZERO;

    //hide here to evaluate exp catch exc and write that exp is invalid
//    ScriptEngineManager mgr = new ScriptEngineManager();
//        ScriptEngine engine = mgr.getEngineByName("JavaScript");
//        String foo = "(20 * 5) * 2 + 1";

    public static final String REG = "reg";
    public static final String MEM = "mem";
    public static final String IMM = "imm";
    public static final String LABEL = "label";

    //constants for mrm
    public static boolean OFFSET = false;

    //constants for sib
    public static boolean SCALE = true;
    public static boolean BASE = true;
    public static boolean ALLOWED_SIB = SCALE | BASE;

    //constants for address mode
    public static boolean USE_32 = false;
    public static boolean USE_16 = !USE_32;


    //constants for special structures like procedure, if-then-else
    //procedure if true - can use
    public static boolean PROCEDURE = false;
    public static boolean MACROS = true;

    //Prefix segment change obliviously
    //if true => fs:[eax]   Prefix = 64
    //if false => in code segment can define variable then use it and we take  Prefix 2E(CS)
    public static boolean PREFIX_SEGMENT_OBVIOUSLY = true;

    //constant for index addressing
    //like val1[eax]
    public static boolean INDEX_ADDRESSING = true;

    public static Integer ADDRESS_SIZE_PREFIX = 0x67;
    public static Integer OPERAND_SIZE_PREFIX = 0x66;


    //constant for math registers
    public static boolean USE_MATH_PROCESSORS = false;

    //constants for type of constants
    //101010b
    public static boolean BINARY_CONSTANTS = true;
    //99231
    public static boolean DECIMAL_CONSTANTS = true;
    //0fffh
    public static boolean HEX_CONSTANTS = true;
    //"TEXT"
    public static boolean TEXT_CONSTANTS = true;
    //float constants like 3.14
    public static boolean FLOAT = false;

    public static boolean IN_MACROS = false;


    //constants for using DB,DW,DD,DQ,EQU
    //if false - you can use it
    public static boolean DB = true;
    public static boolean DW = true;
    public static boolean DD = true;
    public static boolean DQ = false;
    public static boolean EQU = false;


    //collection for commands
    private static Map<String, String[]> commands = new HashMap<>();
    private static Map<String, Integer> operationCodes = new HashMap<>();

    private static Map<String, Integer> addOperationCodes = new HashMap<>();


    //register array
    private static String[] registers = {
            //0                                                 7
            "EAX", "ECX", "EDX", "EBX", "ESP", "EBP", "ESI", "EDI",
            //8                11          13    14    15
            "AX", "CX", "DX", "BX", "SP", "BP", "SI", "DI",
            //16                                        23
            "AL", "CL", "DL", "BL", "AH", "CH", "DH", "BH",
    };

    private static String[] mathRegisters = {
            "ST(0)", "ST(1)", "ST(2)", "ST(3)", "ST(4)", "ST(5)", "ST(6)", "ST(7)"
    };

    private static Map<String, Integer> segmentRegisters = new HashMap<>();
    //key words array
    private static String[] keyWords = {
            //0          1      2      3
            "SEGMENT", "ENDS", "END", ".386",
            //4    5     6     7
            "DB", "DW", "DD", "DQ",
            //8       9       10       11
            "BYTE", "WORD", "DWORD", "PTR",
            //12        13       14     15       16
            "LABEL", "ASSUME", "PROC", "ENDP", "EQU",
            //17    18      19       20        21
            "IF", "ELSE", "ENDIF", "QWORD", "MACRO",
            //22
            "ENDM"    };

    //+
    public static final String[] REG_REG = new String[]{REG, REG};
    //+
    public static final String[] ONLY_MEM = new String[]{MEM};
    //+
    public static final String[] ONLY_REG = new String[]{REG};
    //+
    public static final String[] REG_MEM = new String[]{REG, MEM};
    //+
    public static final String[] MEM_REG = new String[]{MEM, REG};
    //+
    public static final String[] REG_IMM = new String[]{REG, IMM};
    //+
    public static final String[] MEM_IMM = new String[]{MEM, IMM};
    //+
    public static final String[] ONLY_LABEL = new String[]{LABEL};
    //+
    public static final String[] EMPTY = new String[]{};

    //fill the commands
    static {
        commands.put("CMPSW", EMPTY);

        commands.put("STOSW", EMPTY);
        operationCodes.put("STOSW   ", Integer.parseInt("FC", 16)); //ispr
        
        commands.put("OR", MEM_IMM);
        operationCodes.put("OR", Integer.parseInt("08", 16));

        commands.put("ADD", REG_REG);
        operationCodes.put("ADD", Integer.parseInt("2", 16));

        commands.put("DEC", ONLY_MEM);
        operationCodes.put("DEC", Integer.parseInt("0", 16)); //ispr

        commands.put("MOV", REG_IMM);
        operationCodes.put("MOV", Integer.parseInt("BA", 16));

        commands.put("LEA", REG_MEM);
        operationCodes.put("LEA", Integer.parseInt("0", 16));

        commands.put("AND", MEM_REG);
        operationCodes.put("AND", Integer.parseInt("0", 16));

        commands.put("INC", ONLY_REG);
        operationCodes.put("INC", Integer.parseInt("FE", 16));

        commands.put("JG", ONLY_LABEL);
        operationCodes.put("JG", Integer.parseInt("7E", 16));



        //some add op codes for eax,ax,al and mem
        addOperationCodes.put("DEC", Integer.parseInt("14", 16));
        addOperationCodes.put("ADD", Integer.parseInt("4", 16));
        addOperationCodes.put("AND", Integer.parseInt("24", 16));
        addOperationCodes.put("MOV", Integer.parseInt("BE", 16));
        addOperationCodes.put("OR", Integer.parseInt("C", 16));
        addOperationCodes.put("STOSW", Integer.parseInt("A8", 16));
        addOperationCodes.put("LEA", Integer.parseInt("34", 16));
        addOperationCodes.put("INC", Integer.parseInt("FE", 16));

        addOperationCodes.put("JG", Integer.parseInt("EB", 16));


        //fill segment registers
        segmentRegisters.put("CS", 0x2E);
        segmentRegisters.put("SS", 0x36);
        segmentRegisters.put("DS", 0x3E);
        segmentRegisters.put("ES", 0x26);
        segmentRegisters.put("FS", 0x64);
        segmentRegisters.put("GS", 0x65);
    }

    private Constants() {
    }

    public static Map<String, String[]> getCommands() {
        return commands;
    }

    public static Map<String, Integer> getOperationCodes() {
return operationCodes;
        }

public static Map<String, Integer> getAddOperationCodes() {
        return addOperationCodes;
        }

public static String[] getRegisters() {
        return registers;
        }

public static String[] getKeyWords() {
        return keyWords;
        }

public static Map<String, Integer> getSegmentRegisters() {
        return segmentRegisters;
        }

public static String[] getMathRegisters() {
        return mathRegisters;
        }
        }
