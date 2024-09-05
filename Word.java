public class Word {
    private Bit[] bits;

    //_____________________Word Class Constructor__________________\\

    //Decrement
    public void decrement() {
        boolean borrow = true; // Starting with a borrow, as we're subtracting 1
        for (int i = 31; i >= 0; i--) {
            if (borrow) {
                if (this.bits[i].getValue()) { // If bit is 1, subtraction will turn it into 0, and borrow is cleared
                    this.bits[i].set(false);
                    borrow = false; // No borrow since we had a 1
                } else { // If bit is 0, it becomes 1, and borrow continues
                    this.bits[i].set(true);
                    // borrow remains true
                }
            } else { // If there's no borrow, no need to continue as all higher bits remain unchanged
                break;
            }
        }
    }
    //Increment
    public void increment() {
        Bit carry = new Bit(true); // Start with a carry to add 1
        for (int i = 31; i >= 0; i--) { //for loopfor increment 
            Bit original = this.getBit(i);
            this.setBit(i, original.xor(carry)); // XOR the bit and then carry
            carry = original.and(carry); // Updates the carry
        }
    }

    // Default 
    public Word() {
        this.bits = new Bit[32];
        for (int i = 0; i < 32; i++) {
            this.bits[i] = new Bit();
        }
    }

    // Constructor for the initial value which also created an error if an array isn't exactly 32 instances
    // Note: It must hold an array of 32 instances of the Bit class.  
    public Word(boolean[] values) {
        if (values.length != 32) {
            throw new IllegalArgumentException("The Length Of The Array Has To Be 32");
        }
        this.bits = new Bit[32];
        for (int i = 0; i < 32; i++) {
            this.bits[i] = new Bit(values[i]);
        }
    }

    // getBit - Get a new Bit that has the same value as bit i
    public Bit getBit(int i) {
        return new Bit(this.bits[i].getValue());
    }

    // setBit - Set bit i's value
    public void setBit(int i, Bit value) {
        this.bits[i].set(value.getValue());
    }

    // AND - two words, returning a new Word
    public Word and(Word other) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].and(other.getBit(i)));
        }
        return result;
    }

    // OR - two words, returning a new Word
    public Word or(Word other) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].or(other.getBit(i)));
        }
        return result;
    }

    // XOR - two words, returning a new Word
    public Word xor(Word other) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].xor(other.getBit(i)));
        }
        return result;
    }

    // NOT - Negate this word, creating a new Word
    public Word not() {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].not());
        }
        return result;
    }

    // Right shift - this word by amount bits, creating a new Word
    public Word rightShift(int amount) {
        Word result = new Word();
        for (int i = 0; i < 32 - amount; i++) {
            result.setBit(i + amount, this.bits[i]);
        }
        return result;
    }

    // Left shift - this word by amount bits, creating a new Word
    public Word leftShift(int amount) {
        Word result = new Word();
        for (int i = amount; i < 32; i++) {
            result.setBit(i - amount, this.bits[i]);
        }
        return result;
    }

    // toString - Returns a comma-separated string of 't's and 'f's
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Bit bit : this.bits) {
            result.append(bit.toString()).append(", ");
        }
        // Takes out the space and comma
        return result.substring(0, result.length() - 2);
    }

    // Unsigned - Returns the value of this word as a long
    public long getUnsigned() {
        long result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (this.bits[i].getValue() ? 1 : 0);
        }
        return result;
    }

    // Signed - Returns the value of this word as an int
    public int getSigned() {
        int result = 0;

        for (int i = 0; i < 32; i++) {
           if (this.bits[i].getValue()) {
            result |= (1 << (31 - i));
         }
     }

    return result;
    }

    // Copy - Copies the values of the bits from another Word into this one
    public void copy(Word other) {
    for (int i = 0; i < 32; i++) {
        this.bits[i].set(other.bits[i].getValue());
    }
}


    // Set - Set the value of the bits of this Word (used for tests)
public void set(int value) {
    // If the value is negative, calculate its 2's complement
    if (value < 0) {
        value = (int) Math.pow(2, 32) + value;
    }

    for (int i = 0; i < 32; i++) {
        this.bits[i].set((value & (1 << (31 - i))) != 0);
    }
}

//Add
public void add(Word other) {
    Bit carry = new Bit(false);
    for (int i = 31; i >= 0; i--) {
        Bit originalBit = this.bits[i];
        Bit otherBit = other.bits[i];
        // XOR the bits with carry so it can do addition
        this.bits[i] = originalBit.xor(otherBit).xor(carry);
        // Calculates the carry for the next bit
        carry = (originalBit.and(otherBit)).or((originalBit.xor(otherBit)).and(carry));
    }
}
}
