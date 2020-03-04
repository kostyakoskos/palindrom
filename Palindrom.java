import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Palindrom {
	static ArrayList<Integer> subRes = new ArrayList<Integer>();
	static int separate = 0;

	public int findPalindrome(String A) {
		char[] chars = A.toCharArray();
		int[][] LP = new int[chars.length][chars.length];
		for (int i = 0; i < chars.length; i++) {
			LP[i][i] = 1;
		}
		for (int sublen = 2; sublen <= chars.length; sublen++) {
			for (int i = 0; i <= LP.length - sublen; i++) {
				int j = i + sublen - 1;
				if (chars[i] == chars[j] && sublen == 2) {
					LP[i][j] = 2;
				} else if (chars[i] == chars[j]) {
					LP[i][j] = LP[i + 1][j - 1] + 2;
				} else {
					LP[i][j] = Math.max(LP[i + 1][j], LP[i][j - 1]);
				}
			}
		}
		//printMatrix(LP);
		way(LP);
		return LP[0][LP.length - 1];

	}

	public void printMatrix(int[][] LP) {
		for (int i = 0; i < LP.length; i++) {
			for (int j = 0; j < LP.length; j++) {
				System.out.print("  " + LP[i][j]);
			}
			System.out.println("");
		}
	}

	public void way(int[][] LP) {
		int counter = 0;
		for (int i = 0; i < LP.length; i++) {
			for (int j = LP.length - 1 - counter; j > i; j--) {
				if (LP[i][j] == LP[i][j - 1]) {
					counter++;
//					separate = 0;
//					separate = j;
				} else if (LP[i][j] == LP[i + 1][j]) {
					j = i;
				} else if(LP[i][j] == LP[i + 1][j - 1] + 2){
					separate = 0;
					separate = j - 1;
					subRes.add(i);
					subRes.add(j);
					j = i;
					counter++;
				}
			}
		}
	}

	public static void main(String arg[]) {
		try {
			FileInputStream fstream = new FileInputStream("input.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			OutputStream f = new FileOutputStream("output.txt", true);
			OutputStreamWriter writer = new OutputStreamWriter(f);
			BufferedWriter out = new BufferedWriter(writer);

			String strA = br.readLine();
			char arr[] = strA.toCharArray();

			Boolean flag = true;
			if (arr.length == 1) {
				out.write(1 + "\r\n");
				out.write(arr[0]);
				//System.out.print(arr[0] + " 1");
				flag = false;
			}
			if (flag) {
				Palindrom i = new Palindrom();
				int x = i.findPalindrome(strA);
				out.write(Integer.toString(x) + "\r\n");
				if(separate != 0) {
					subRes.add(separate);
				}
				Collections.sort(subRes);
				HashSet<Integer> set = new HashSet<>(subRes);
				subRes.clear();
				subRes.addAll(set);
				for (Integer retval : subRes) {
					System.out.print(arr[retval] + " ");
					out.write(arr[retval]);
				}
				System.out.print(arr[separate]);
			}
			br.close();
			out.close();
		} catch (IOException e) {
		}
	}
}
