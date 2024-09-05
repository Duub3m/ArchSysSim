public class L2Cache {
    
    private static final int cacheSize = 32;
    private static final int groupSize = 8;
    private static final int blockSize = 8;

    private Word[] cache;
    private boolean[] valid;
    private Word[] baseAddresses;

    public L2Cache() {
        cache = new Word[cacheSize];
        valid = new boolean[cacheSize];
        baseAddresses = new Word[cacheSize / groupSize];
        for (int i = 0; i < cacheSize; i++) {
            cache[i] = new Word();
            valid[i] = false;
        }
        for (int i = 0; i < cacheSize / groupSize; i++) {
            baseAddresses[i] = new Word();
        }
    }

    public Word read(Word address) {
        // This checks if the address is located inside the cache
        int groupIndex = (int) address.rightShift(6).getUnsigned();
        int index = getIndex(address);
        if (baseAddresses[groupIndex].equals(address.rightShift(6))) {
            if (valid[index]) {
                return cache[index];
            }
        }
        baseAddresses[groupIndex].copy(address.rightShift(6));
        for (int i = 0; i < groupSize; i++) {
            int blockIndex = groupIndex * groupSize + i;
            Word blockAddress = baseAddresses[groupIndex].leftShift(6);
            cache[blockIndex].copy(MainMemory.read(blockAddress));
            valid[blockIndex] = true;
        }
        return cache[index];
    }

    private int getIndex(Word address) {
        int addressValue = (int) address.getUnsigned();
        return (addressValue / blockSize) % groupSize;
    }
}
