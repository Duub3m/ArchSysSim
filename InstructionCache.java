public class InstructionCache {
    private static final int cacheSize = 32; // 32 Words in size
    private static final int groupSize = 8; // 4 groups of 8 Words
    private static final int blockSize = 4; // 4 Words per group
    private Word[] cache;
    private Word[] baseAddresses;
    private boolean[] valid;

    public InstructionCache() {
        cache = new Word[cacheSize];
        for (int i = 0; i < cacheSize; i++) {
            cache[i] = new Word();
        }
        baseAddresses = new Word[cacheSize];
        for (int i = 0; i < cacheSize; i++) {
            baseAddresses[i] = new Word();
        }
        valid = new boolean[cacheSize];
    }

    public Word read(Word address) {
        // Checks to see if the address is inside of the cache
        int index = getIndex(address);
        if (baseAddresses[index].equals(address.rightShift(3))) {
            if (valid[index]) {
                // 10 cycles
                return cache[index];
            }
        }
        // L2
        L2Cache l2Cache = new L2Cache();
        Word data = l2Cache.read(address);
        // Copies the block into Instruction Cache 
        //50 cycles
        baseAddresses[index].copy(address.rightShift(3));
        cache[index].copy(data);
        valid[index] = true;
        return cache[index];
    }

    public void write(Word address, Word data) {
        int index = getIndex(address);
        // Writes the data to cache
        cache[index].copy(data);
        valid[index] = true;
        // Update block in main memory
        for (int i = 0; i < blockSize; i++) {
            Word blockAddress = baseAddresses[index].leftShift(3);
            MainMemory.write(blockAddress, cache[index * blockSize + i]);
        }
    }
    
    

   

    private int getIndex(Word address) {
        int addressValue = (int) address.getUnsigned();
        return (addressValue / blockSize) % groupSize;
    }
}
