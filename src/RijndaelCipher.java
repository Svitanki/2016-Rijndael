
public class RijndaelCipher {
	
	private static byte[] subBytes(byte[] cipherMe) {
		
		// for each byte
			// find inverse
			// then, to result
			// https://wikimedia.org/api/rest_v1/media/math/render/svg/9319172b31f8fe1e4db07d88383366286b119e88
			// "Then a bit-wise modulo-2 matrix is applied, followed by an XOR operation with 63."
		
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
		
		// map into 4x4 bytes
		/*
		0x0	0x1	0x2	0x3
		1x0	1x1	1x2	1x3
		2x0	2x1	2x2	2x3
		3x0	3x1	3x2	3x3
		*/
		
		// mix bits by column
		
		return cipherMe;
	}
	
	private static byte[] addRoundKey(byte[] cipherMe, byte[] roundKey) {
		
		// bitwise XOR each element
		for (int i = 0; i < cipherMe.length; i++) {
			cipherMe[i] = (byte)((int)cipherMe[i]^(int)roundKey[i]);
		}
		
		return cipherMe;
	}
	
	
	// !!!!!!!!!
	// !!!!!!!!!
	// !!!!!!!!!
		// MAKE METHOD FOR GENERATING ROUND KEYS HERE (KEY SCHEDULE)
	// !!!!!!!!!
	// !!!!!!!!!
	// !!!!!!!!!
	
	
	private static byte[] encipher(byte[] plaintext, byte[] cipherkey) {
		
		// cipherkey -> key schedule
			// return roundKey 0-10
		
		
		
		
		// encipher plain text
		// ---------------------
		
		// addRoundKey (cipher key)
		
		// for i=0 -> 9
			// subBytes
			// shiftRows
			// mixColumns
			// addRoundKey (roundKey[i])
		
		// subBytes
		// shiftRows
		// addRoundKey (roundKey[10])
		
		// return resulting ciphertext
		
		
		byte empty[] = {' '};
		return empty;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// FOR THE PURPOSES OF THIS CODE, I AM ASSUMING THAT KEY IS ALWAYS 128 BITS, AND SO IS PLAINTEXT
		
		//if a plaintext message is 128 bits, in chars, it can have 16 chars
		
		byte plainarr[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p'};
		
		// generate 128-bit key; or, generate 16 random (byte)s
		
		// input plainarr and key into ciphering
		
		// this is just for testing purposes
		for (int i = 0; i<plainarr.length; i++)
			System.out.println(Integer.toBinaryString((plainarr[i])));

	}

}
