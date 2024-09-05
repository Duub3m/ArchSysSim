public class Processor {
    private Word PC; // Program Counter
    private Word SP; // Stack Pointer
    private Word currentInstruction; // Current instruction
    private Bit halted; // Checks if the processor is halted
    private ALU alu = new ALU(); // ALU 
    public Word[] registers = new Word[32]; // Processor's registers
    
    // Decode values
    private Word registerDest; // Destination register
    private Word registerSrc;  // Source register - 2R 
    private Word registerSrc1; // First source register- 3R 
    private Word registerSrc2; // Second source register -3R 
    private Word immediateValue; // Immediate value
    private Word function; // Alu : Function bits 

     // Instruction Cache
     private InstructionCache instructionCache = new InstructionCache();
    
     // L2 Cache
     private L2Cache l2Cache = new L2Cache();
 
     // Clock cycle 
     private int currentClockCycle = 0;

     public int getClockCycleCount() {
        return currentClockCycle;
    }

    public Processor() {
        PC = new Word();
        SP = new Word();
        currentInstruction = new Word();
        halted = new Bit(false);

        // PC & SP
        PC.set(0); //The PC starts at address 0 in this processor
        SP.set(1024); //The stack will start at address 1024
    }

    public void run() {
        while (halted.getValue() == false) {
            fetch(); 
            decode();
            execute();
            store();
            currentClockCycle++;
        }
    }
    //Fethch: Fetch the instruction from memory (based on the PC)
    private void fetch() {
        currentInstruction.copy(MainMemory.read(PC));
        PC.increment(); // Increments the PC  
    }

    //Decode: get the data for the instruction 
    private void decode() {
        // Checks for halt 
        if (currentInstruction.and(new Word(new boolean[32])).getUnsigned() == 0) {
            halted.set(true);
            return;
        }
        Word opcode = currentInstruction.and(new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true}));
    
        // Checks the format by looking at the last 2 bits of the opcode
        int formatType = (int)opcode.getUnsigned() & 0x3;  // Masking to get the last 2 bits
    
        switch (formatType) {
            //
            case 0: // No R
                immediateValue = extractImmediate(currentInstruction, 0, 26);
                break;
            case 1: // Dest Only
                immediateValue = extractImmediate(currentInstruction, 0, 17);
                function = extractFunction(currentInstruction, 18, 21);
                registerDest = extractRegister(currentInstruction, 22, 26);
                break;
            case 2: // 2R
                immediateValue = extractImmediate(currentInstruction, 0, 12);
                registerSrc = extractRegister(currentInstruction, 13, 17);
                function = extractFunction(currentInstruction, 18, 21);
                registerDest = extractRegister(currentInstruction, 22, 26);
                break;
            case 3: // 3R
                immediateValue = extractImmediate(currentInstruction, 0, 7);
                registerSrc1 = extractRegister(currentInstruction, 8, 12);
                registerSrc2 = extractRegister(currentInstruction, 13, 17);
                function = extractFunction(currentInstruction, 18, 21);
                registerDest = extractRegister(currentInstruction, 22, 26);
                break;
        }
    }
    
    
    private Word extractImmediate(Word instruction, int start, int end) {
        // Create a mask to get the bits from the start to the end
        Word mask = new Word();
        for (int i = start; i <= end; i++) {
            mask.setBit(i, new Bit(true));// sets the bit at position 'i' to the value of the bit
        }
    
        // Uses the mask and right shift the bits 
        Word immediate = instruction.and(mask).rightShift(start);
    
        return immediate;
    }

    // extract register value: 
    private Word extractRegister(Word instruction, int start, int end) {
         
        Word mask = createMask(start, end);
        return instruction.and(mask).rightShift(start);
    }
    
    //createMask
    private Word createMask(int start, int end) {
        boolean[] bits = new boolean[32];
        for (int i = start; i <= end; i++) {
            bits[i] = true;
        }
        return new Word(bits);
    }

    //ExtractFunction
    private Word extractFunction(Word instruction, int start, int end) {
        Word mask = new Word();
        //Sets the bit at the index.
        for (int i = 0; i < 32; i++) {
            mask.setBit(i, new Bit(i >= start && i <= end));
        }
    
        // Uses the mask on the instruction, then right shift 
        Word functionBits = instruction.and(mask).rightShift(start);
    
        return functionBits;
    }

    private Bit[] convertWordToBits(Word word) {
        Bit[] bits = new Bit[32];  
        for (int i = 0; i < bits.length; i++) {
            bits[i] = word.getBit(i);  // Use the getBit from Word .
        }
        return bits;
    }
    
//Execute: Checks for opcode, passes “function” onto ALU, copies appropriate values into ALU
private void execute() {
    // Check if the processor halted then return .
    if (halted.getValue()) {
        return;
    }

    Word opcode = currentInstruction.and(new Word(new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true}));
    int opcodeValue = opcode.getUnsigned(); 

    //Switch for opcode.
    switch (opcodeValue) {
        case 0: // HALT 
            halted.set(true);
            break;
        case 1: // Branching.
            handleBranch();
            break;
        case 2: // Calling a function/method.
            handleCall();
            break;
        case 3: // Return from a call.
            Return();
            break;
        default:
            
            System.out.println("not an opcode: " + opcodeValue);
            break;
    }
}

// Handle Branch
private void handleBranch() {
    boolean branchConditionMet = false;

alu.op1 = registers[getRegisterIndex(registerSrc1)];
    alu.op2 = registers[getRegisterIndex(registerSrc2)];
    alu.subtract(alu.op1, alu.op2); //subtraction with ALU

    if (alu.resultIsNegative()) {
        branchConditionMet = true;
    }
    if (branchConditionMet) {
        PC.set(PC.getUnsigned() + immediateValue.getSigned()); 
    }
}


    //getRegisterINdex
    private int getRegisterIndex(Word registerWord) {
        int index = 0;
        for (int i = 0; i < 32; i++) { 
            if (registerWord.getBit(i).getValue()) {
                index |= 1 << (31 - i);
            }
        }
        return index;
    }

    

    private void handleCall() {
        // Push the current PC value
        Word nextInstructionAddress = new Word();
        nextInstructionAddress.set(PC.getUnsigned() + 1); 
        push(nextInstructionAddress);
        
        if (!immediateValue()) { 
            // Update PC to the immediate value
            PC.copy(immediateValue);
        } else {
            PC.copy(registers[getRegisterIndex(registerDest)]);
        }
    }
    
    

    private void Return() {
        // sets PC to this address
        Word returnAddress = pop();
        PC.copy(returnAddress);
    }

    private void push(Word value) {
        SP.decrement();
        // Writes to the L2 Cache
        l2Cache.write(SP, value);
    }

    private Word peek(int distance) {
        Word peekAddress = new Word();
        peekAddress.set((int) (SP.getUnsigned() + distance));
        // Read from L2 Cache
        return l2Cache.read(peekAddress);
    }

    private Word pop() {
        Word value = l2Cache.read(SP);
        SP.increment();
        return value;
    }

    public void load(int baseRegisterIndex, int offset, int destinationRegisterIndex) {
        Word baseAddress = registers[baseRegisterIndex];
        Word offsetWord = new Word();
        offsetWord.set(offset);

        alu.op1 = baseAddress;
        alu.op2 = offsetWord;
        alu.add(alu.op1, alu.op2);

        // Loads from the L2 Cache
        Word value = l2Cache.read(alu.result);
        registers[destinationRegisterIndex].copy(value);
    }

    public void store(int baseRegisterIndex, int offset, int sourceRegisterIndex) {
        Word baseAddress = registers[baseRegisterIndex];
        Word offsetWord = new Word();
        offsetWord.set(offset);

        alu.op1 = baseAddress;
        alu.op2 = offsetWord;
        alu.add(alu.op1, alu.op2);

        // This will store it to L2 Cache
        l2Cache.write(alu.result, registers[sourceRegisterIndex]);
    }
}
