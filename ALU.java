public class ALU {
    public Word op1;
    public Word op2;
    public Word result;

    public ALU() {
        op1 = new Word();
        op2 = new Word();
        result = new Word();
    }

    public void doOperation(Bit[] operation) {
        int operationCode = convertBitsToInteger(operation);
        switch (operationCode) {
            case 8: // 1000 - and (1000 in Binary is 8)
                result = op1.and(op2);
                break;
            case 9: // 1001 - or (1001 in Binary is 9)
                result = op1.or(op2);
                break;
            case 10: // 1010 - xor (1010 in Binary is 10)
                result = op1.xor(op2);
                break;
            case 11: // 1011 - not (1011 in BInary is 11)
                result = op1.not();
                break;
            case 12: // 1100 - left shift (1100 in Binary is 12)
                result.copy(op1.leftShift((int) op2.getUnsigned() & 0x1F)); // left shift
                break;
            case 13: // 1101 - right shift (1101 in Binary is 13)
                result.copy(op1.rightShift((int) op2.getUnsigned() & 0x1F)); // right shift
                break;
            case 14: // 1110 - add (1110 in Binary is 14)
                add(op1, op2);
                break;
            case 15: // 1111 - subtract (1111 in Binary is 15)
                subtract(op1, op2);
                break;
            case 7: // 0111 - multiply (0111 in Binary is 7)
                multiply(op1, op2);
                break;
            default:
                System.out.println("This is not an operation");
        }
    }
    //Converts the bits to Integers by iterating through each bit in the array
    //It then shifts the result to the left then adds the value of the bit
    private int convertBitsToInteger(Bit[] bits) {
        int result = 0;
        for (int i = 0; i < bits.length; i++) {
            result = (result << 1) | (bits[i].getValue() ? 1 : 0);
        }
        return result;
    }

    //Does the addition operation for two operands while using carry 
    public void add(Word op1, Word op2) {
        Bit carry = new Bit(false);
        Word tempResult = new Word();
        for (int i = 31; i >= 0; i--) {
            Bit[] resultAndCarry = add2(op1.getBit(i), op2.getBit(i), carry);
            tempResult.setBit(i, resultAndCarry[0]);
            carry = resultAndCarry[1];
        }
        result.copy(tempResult);
    }

    public void subtract(Word op1, Word op2) {
        // Inverts the bits of op2 to start making the two's complement
        Word invertedOp2 = new Word();
        for (int i = 0; i < 32; i++) {
            invertedOp2.setBit(i, op2.getBit(i).not());
        }
        
        // Add one to the inverted op2
        Word one = new Word();
        one.setBit(31, new Bit(true)); 
        
        Word originalResult = result;
        result = invertedOp2;
        
        // Add one to invertedOp2
        add(result, one);
    
        Word twosComplementOp2 = result;//two's complement of op2 in a new Word
        
        result = originalResult; // Reset 
        
        // Adds op1 and the two's complement of op2
        add(op1, twosComplementOp2);
    }
    

    public void multiply(Word op1, Word op2) {
        result = new Word();
    
        Word multiplyOp1 = new Word();
        // Copy op1 to multiply0p1 so we dont change op1
        multiplyOp1.copy(op1);
    
        // Iterate through each bit of the multiplier 
        for (int i = 0; i < 32; i++) {
            // If the bit of op2 is set then we add op1 to the result
            if (op2.getBit(31 - i).getValue()) { // fixes the order
                result = add4(result, multiplyOp1, new Word(), new Word());
            }
    
            // Shifts left by 1 bit
            multiplyOp1 = multiplyOp1.leftShift(1);
        }
    }

    //Add 2: use and(), or() and xor() on the inputs to add the bits. 
    private Bit[] add2(Bit bit1, Bit bit2, Bit carryIn) {
        // Calculate sum bit (bitwise XOR for bit1, bit2, and carryIn)
        Bit sum = bit1.xor(bit2).xor(carryIn);
    
        // Calculate carry-out bit
        Bit carryOut = bit1.and(bit2).or(bit1.xor(bit2).and(carryIn));
    
        return new Bit[]{sum, carryOut};
    }
    
    //Add 4: add4 will also use and(), or() and xor() but for 4 operands instead of 2. 
    private Word add4(Word a, Word b, Word c, Word d) {
        Word result = new Word();
        Bit carry = new Bit(false);
    
        for (int i = 31; i >= 0; i--) { // Iterate through the 32 bits
            Bit[] sumAndCarry = add2(a.getBit(i), add2(b.getBit(i), add2(c.getBit(i), d.getBit(i), carry)[0], carry)[0], carry);
            result.setBit(i, sumAndCarry[0]);
            carry = sumAndCarry[1]; // Updates the carry
        }
    
        return result;
    }
    //Checks if the result is negative
    public boolean resultIsNegative() {
        return result.getBit(0).getValue();
    }
    

    
}