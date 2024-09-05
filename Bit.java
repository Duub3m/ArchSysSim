public class Bit {
    private Boolean value;

    //_____________________Bit Class Constructors__________________\\
    
     public Bit() {
        this.value = false; // Makes the default boolean value "false"
    }

    public Bit(Boolean value) {
        this.value = value;
    }

    //_____________________Bit Class Methods_______________________\\

    // Set(Boolean Value) - Sets the value of the bit
    public void set(Boolean value) {
        this.value = value;
    }

    // Toggle - Changes the value from true to false or false to true
    public void toggle() {
        this.value = !this.value;
    }

    // Set - Sets the bit to true
    public void set() {
        this.value = true;
    }

    // Clear - Sets the bit to false
    public void clear() {
        this.value = false;
    }

    // Boolean getValue - Returns the current value of the bit
    public Boolean getValue() {
        return this.value;
    }

    // AND - Perform AND on two bits and return a new bit set to the result
    // Note - Can't use the logic (&, &&, |, ||) 
    //I used "If, Else" for this method
    public Bit and(Bit other) {
        if (this.value) {
            return new Bit(other.getValue());  // true AND other = other
        } else {
            return new Bit(false);  // false AND other = false
        }
    }

    // OR - Perform OR on two bits and return a new bit set to the result
    //Note: Can't use the logic (&, &&, |, ||)
    //I used "Switch" for this method
    public Bit or(Bit other) {
        switch (this.value ? 1 : 0) {
            case 1:
                return new Bit(true);  // true OR other = true
            case 0:
                return new Bit(other.getValue());  // false OR other = other
            default:
                throw new IllegalStateException("Error: " + this.value + " is neither True nor False");
        }
    }

    // XOR - Perform XOR on two bits and return a new bit set to the result
    //Note: Can't use the logic (&, &&, |, ||)
    //I used "If, Else" for this method
    public Bit xor(Bit other) {
        boolean resultValue;
        if (this.value && !other.getValue() || !this.value && other.getValue()) {
            resultValue = true;
        } else {
            resultValue = false;
        }
        return new Bit(resultValue); // returns a new object so project doesn't break later
    }

    // NOT - Perform NOT operation on the existing bit, returning the result as a new bit
    //Note: Can't use the logic (&, &&, |, ||)
    //I used "If, Else" for this method
    public Bit not() {
        boolean resultValue;
        if (this.value) {
            resultValue = false;
        } else {
            resultValue = true;
        }
        return new Bit(resultValue); // returns a new object so project doesn't break later
    }

    // toString - Returns "t" or "f"
    public String toString() {
        if (this.value) {
            return "t";
        } else {
            return "f";
        }
    }
    
}
