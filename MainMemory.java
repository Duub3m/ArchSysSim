public class MainMemory {
    private static final int memorySize = 1024; // Static array of 1024 words
    private static Word[] memory = new Word[memorySize]; // Static memory array

    // Static for memory locations 
    static {
        for (int i = 0; i < memorySize; i++) {
            memory[i] = new Word();
        }
    }

    //Read: Reads a Word from the memory address
    public static Word read(Word address) {
        int index = (int) address.getUnsigned();
        if (index >= 0 && index < memorySize) {
            Word wordCopy = new Word();
            wordCopy.copy(memory[index]); // Copies the contents of the memory into the new Word 
            return wordCopy; // Returns the new Word 
        } else {
            throw new IndexOutOfBoundsException("Error Trying to 'Read' from the memory");
        }
    }

    //Write: Writes a Word to the memory address
    public static void write(Word address, Word value) {
        int index = (int) address.getUnsigned();
        if (index >= 0 && index < memorySize) {
            memory[index].copy(value); // Copies the value to the memory 
        } else {
            throw new IndexOutOfBoundsException("Error trying to 'Write' to the memory");
        }
    }

    // Load: Loads the binary value into memory
    //The Strings should be: 32 0’s or 1’s (0 is false, 1 is true)
    public static void load(String[] data) {
        for (int i = 0; i < data.length && i < memorySize; i++) {
            if (data[i].length() == 32) {
                for (int bit = 0; bit < 32; bit++) {
                    memory[i].setBit(bit, new Bit(data[i].charAt(bit) == '1'));
                }
            } else {
                throw new IllegalArgumentException("String should be 32 bits long.");
            }
        }
    }
    
}
