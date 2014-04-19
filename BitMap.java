/*  
 * This file provides the implementation of the class BitMap. 
 * BitMap: a form of hash,  using one bit to present an integer nubmer; 
 *
 *  FileName:    BitMap.java 
 *  description: use BitMap to implement hash.
 *  Author:      wzb (zhongbo.wzb@alibaba-inc.com)
 *  Date:        2014-04-19 16:13
 *  Version:     0.1
 *
 * */

public class BitMap {
  private long bits;
  private int[] map;

  public static final long SHIFT = 5; 
  public static final long MASK  = ((1 << SHIFT) -1); 

  public BitMap(long nbits) {
    bits = nbits + 1;
    int size  = (int) (((bits >>> SHIFT) + 1) & 0x0ffffffffL); 
    map = new int[size];

    for (int i = 0; i < size; i++) {
      map[i] = 0;
    }

  } 

  public long size() {
    return bits;
  }

  public void set(long index) {
    int offset = (int) ((index >>> SHIFT) & 0x0ffffffffL);
    map[offset] |= (1 << (index & MASK));  
  }

  public void clear(long index) {
    int offset = (int) ((index >>> SHIFT) & 0x0ffffffffL);
    map[offset] &=  ~(1 << (index & MASK));  
  }

  public boolean isSet(long index) {
    int offset = (int) ((index >>> SHIFT) & 0x0ffffffffL);
    int off = (int) (index & MASK);

    return ((map[offset] >>> off) & 0x01) == 0x1; 

  }

/*
  public static void main(String[] args) {
    System.out.println(MASK);
    long size = 1L << 32;
    System.out.println(size);
    BitMap bitMap = new BitMap(size);

    System.out.println("bitMap size: " + bitMap.size());
    for (long i = 0; i <= bitMap.size(); ++i) {
      if (bitMap.isSet(i)) {
        System.out.println("Error:@ " + i);
      }
    }
    System.err.println("xxxx");

    for (long i = 0; i <= bitMap.size(); i++) {
      bitMap.set(i);
      if (!bitMap.isSet(i)) {
        System.out.println("not set:@ " + i);
      }
      bitMap.clear(i);
    }

    System.err.println("xxxx");
    for (long i = 0; i <= bitMap.size(); ++i) {
      if (bitMap.isSet(i)) {
        System.out.println("Error:@ " + i);
      }
    }
  }
  */
}
