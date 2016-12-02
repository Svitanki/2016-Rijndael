import java.util.Scanner;

// I will go back and make some of those expanded loops into actual loops 
// sometime when i do not have a headache
// I apologize ahead of time

// thanks to assistance from http://www.moserware.com/2009/09/stick-figure-guide-to-advanced.html


public class RijndaelCipher {
	
		static byte roundKey[] = new byte[176];	// so, 11 keys of 128 bits
	
	// I'm lazy and not going to do the calculation for the sbox data in here
	// I'm just going to grab it and make an array
	private static int[][] subs = 
		{{0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76}, 
			{0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0}, 
			{0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15}, 
			{0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75}, 
			{0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84}, 
			{0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf}, 
			{0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8}, 
			{0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2}, 
			{0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73}, 
			{0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb}, 
			{0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79}, 
			{0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08}, 
			{0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a}, 
			{0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e}, 
			{0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf}, 
			{0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}};
	
	private static byte[] subBytes(byte[] cipherMe) {
		
		for (int i = 0; i<16; i++){
			byte temp = cipherMe[i];
			cipherMe[i] = (byte) subs[(temp>>4) & 0x0f][temp & 0x0f];
		}
		
		return cipherMe;
	}
	
	private static byte[] shiftRows(byte[] cipherMe) {
		
		// map into 4x4 bytes
		/*
		1	2	3	4
		5	6	7	8
		9	10	11	12
		13	14	15	16
		 */
		
		// and move as such
		/*
		1	2	3	4
		6	7	8	5	
		11	12	9	10		
		16 	13	14	15	
		 */
		
		byte temp1, temp2;
		
		// 1st row untouched
		
		// 2nd row
		temp1 = cipherMe[4];
		cipherMe[4] = cipherMe[5];
		cipherMe[5] = cipherMe[6];
		cipherMe[6] = cipherMe[7];
		cipherMe[7] = temp1;
		
		// 3rd row
		temp1 = cipherMe[8];
		temp2 = cipherMe[9];
		cipherMe[8] = cipherMe[10];
		cipherMe[9] = cipherMe[11];
		cipherMe[10] = temp1;
		cipherMe[11] = temp2;
		
		//4th row
		temp1 = cipherMe[15];
		cipherMe[15] = cipherMe[14];
		cipherMe[14] = cipherMe[13];
		cipherMe[13] = cipherMe[12];
		cipherMe[12] = temp1;
		
		return cipherMe;
	}
	
	private static byte[] mixColumns(byte[] cipherMe) {
		
		byte[] temp = new byte[4];
		
		// map into 4x4 bytes
		/*
		0x0	0x1	0x2	0x3
		1x0	1x1	1x2	1x3
		2x0	2x1	2x2	2x3
		3x0	3x1	3x2	3x3
		
		0	1	2	3
		4	5	6	7
		8	9	10	11
		12	13	14	15
		*/
		
		// mix bits by column
		// aka, for each column, matrix multiply with following field
		/*
		 2 3 1 1 
		 1 2 3 1
		 1 1 2 3
		 3 1 1 2
		 */
		
		// bitwise xor rather than +
		
		// 1st col
				temp[0] = cipherMe[0];
				temp[1] = cipherMe[4];
				temp[2] = cipherMe[8];
				temp[3] = cipherMe[12];
				cipherMe[0] = (byte)( (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) );
				cipherMe[4] = (byte)( (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) );
				cipherMe[8] = (byte)( (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) );
				cipherMe[12] = (byte)( (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) );
				
		// 2nd col
				temp[0] = cipherMe[1];
				temp[1] = cipherMe[5];
				temp[2] = cipherMe[9];
				temp[3] = cipherMe[13];
				cipherMe[1] = (byte)( (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) );
				cipherMe[5] = (byte)( (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) );
				cipherMe[9] = (byte)( (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) );
				cipherMe[13] = (byte)( (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) );
		
		// 3rd col
				temp[0] = cipherMe[2];
				temp[1] = cipherMe[6];
				temp[2] = cipherMe[10];
				temp[3] = cipherMe[14];
				cipherMe[2] = (byte)( (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) );
				cipherMe[6] = (byte)( (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) );
				cipherMe[10] = (byte)( (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) );
				cipherMe[14] = (byte)( (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) );	
				
		// 4th col
				temp[0] = cipherMe[3];
				temp[1] = cipherMe[7];
				temp[2] = cipherMe[11];
				temp[3] = cipherMe[15];
				cipherMe[3] = (byte)( (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) );
				cipherMe[7] = (byte)( (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) ^ (temp[2]*1) );
				cipherMe[11] = (byte)( (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) ^ (temp[3]*1) );
				cipherMe[15] = (byte)( (temp[3]*1) ^ (temp[2]*1) ^ (temp[1]*3) ^ (temp[0]*2) );
		
		
		// I could probably put all that in a 4-loop but I'm not gonna worry about that rn tbh
		
		return cipherMe;
	}
	
	private static byte[] addRoundKey(byte[] cipherMe, byte[] roundKey) {
		
		// bitwise XOR each element
		for (int i = 0; i < cipherMe.length; i++) {
			cipherMe[i] = (byte)((int)cipherMe[i]^(int)roundKey[i]);
		}
		
		return cipherMe;
	}
	
	
	private static void keySchedule(byte[] originalKey) {
		
		int rconIter = 1;
		int schedulerLoc = 16-4;
		
		// start by copying originalKey into roundKey - the first 16 bytes
		for (int i=0; i<16; i++) {
			roundKey[i] = originalKey[i];
		}
		
		// next, for each additional group of 16 bytes
		for (int j=1; j<11; j++){
			// first, generate the first 4 bytes
				byte temp[] = new byte[4];
				// assign value of previous 4 bytes to temp, then rotate 1 byte to left
				// i'm doing this all in one while assigning
				temp[3] = roundKey[j*16 - 4];
				temp[0] = roundKey[j*16 - 3];
				temp[1] = roundKey[j*16 - 2];
				temp[2] = roundKey[j*16 - 1];
				// next, subByte each value
				for (int k = 0; k<4; k++){
					byte subMe = temp[k];
					temp[k] = (byte) subs[(subMe>>4) & 0x0f][subMe & 0x0f];
				}
				// xor 2^rconIter to just the first byte, bitwise
				temp[0] ^= rcon(rconIter);
				// increment rconIter
				rconIter++;
				// xor temp with the 4 bytes 16 bytes before it
				temp[0] ^= roundKey[j*16 - 16];
				temp[1] ^= roundKey[j*16 - 16];
				temp[2] ^= roundKey[j*16 - 16];
				temp[3] ^= roundKey[j*16 - 16];
				// this becomes the first 4 bytes of the group/round key 
				roundKey[j*16] = temp[0];
				roundKey[j*16 + 1] = temp[1];
				roundKey[j*16 + 2] = temp[2];
				roundKey[j*16 + 3] = temp[3];
			// NEXT
				// 3 times, copy 4 bytes, and cor with 16 bytes ago
				roundKey[j*16 + 4] = roundKey[j*16];
				roundKey[j*16 + 5] = roundKey[j*16 + 1];
				roundKey[j*16 + 6] = roundKey[j*16 + 2];
				roundKey[j*16 + 7] = roundKey[j*16 + 3];
				roundKey[j*16 + 4] ^= roundKey[j*16 + 4 - 16];
				roundKey[j*16 + 5] ^= roundKey[j*16 + 5 - 16];
				roundKey[j*16 + 6] ^= roundKey[j*16 + 6 - 16];
				roundKey[j*16 + 7] ^= roundKey[j*16 + 7 - 16];
					roundKey[j*16 + 8] = roundKey[j*16 + 4];
					roundKey[j*16 + 9] = roundKey[j*16 + 5];
					roundKey[j*16 + 10] = roundKey[j*16 + 6];
					roundKey[j*16 + 11] = roundKey[j*16 + 7];
					roundKey[j*16 + 8] ^= roundKey[j*16 + 8 - 16];
					roundKey[j*16 + 9] ^= roundKey[j*16 + 9 - 16];
					roundKey[j*16 + 10] ^= roundKey[j*16 + 10 - 16];
					roundKey[j*16 + 11] ^= roundKey[j*16 + 11 - 16];
				roundKey[j*16 + 12] = roundKey[j*16 + 8];
				roundKey[j*16 + 13] = roundKey[j*16 + 9];
				roundKey[j*16 + 14] = roundKey[j*16 + 10];
				roundKey[j*16 + 15] = roundKey[j*16 + 11];
				roundKey[j*16 + 12] ^= roundKey[j*16 + 12 - 16];
				roundKey[j*16 + 13] ^= roundKey[j*16 + 13 - 16];
				roundKey[j*16 + 14] ^= roundKey[j*16 + 14 - 16];
				roundKey[j*16 + 15] ^= roundKey[j*16 + 15 - 16];
		}
		
		// AND WE'RE DONE
		
		
	}
	
	private static int rcon(int i) {
		// return 2^i
		int temp = 1;
		for (int j = 0; j<i; j++)
			temp *= 2;
		return temp;
	}
	
	
	private static byte[] encipher(byte[] ciphertext, byte[] key) {
		
		keySchedule(key);
		// creates full set of roundkeys in roundKey[]
		
		
		// encipher plain text
		// ---------------------
		
		// addRoundKey (cipher key)
		addRoundKey(ciphertext, key);
		
		// for i=0 -> 9
		for (int i = 1; i<11; i++){
			// subBytes
			subBytes(ciphertext);
			// shiftRows
			shiftRows(ciphertext);
			// mixColumns
			if (i != 10)
				mixColumns(ciphertext);
			// addRoundKey (roundKey[i])
			byte thisRoundsKey[] = new byte[16];
			for (int j = 0; j<16; j++){
				thisRoundsKey[j] = roundKey[i*16 + j];
			}
			addRoundKey(ciphertext, thisRoundsKey);
		}
			
		return ciphertext;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in); 
		
		// FOR THE PURPOSES OF THIS CODE, I AM ASSUMING THAT KEY IS ALWAYS 128 BITS, AND SO IS PLAINTEXT
		
		//if a plaintext message is 128 bits, in chars, it can have 16 chars
		System.out.println("Only first 16 characters will be accepted. Please enter.");
		
		System.out.print("Plaintext: ");
		String inputPlaintext = scanner.nextLine();
		
		System.out.print("Key: ");
		String inputKey = scanner.nextLine();
		
		byte plainarr[]= new byte[16];
		byte key[] = new byte[16];
		for (int i = 0; i<16; i++){
			plainarr[i] = (byte)inputPlaintext.charAt(i);
			key[i] = (byte)inputKey.charAt(i);
		}
		
		System.out.print("Plaintext in Hex: ");
		for (int i = 0; i<plainarr.length; i++)
			System.out.print(Integer.toHexString(plainarr[i] & 0xFF) + " ");
		System.out.println("");
		
		// input plainarr and key into ciphering
		encipher(plainarr, key);
		
		System.out.print("Ciphertext in Hex: ");
		for (int i = 0; i<plainarr.length; i++)
			System.out.print(Integer.toHexString(plainarr[i] & 0xFF) + " ");
		System.out.println("");

	}

}
