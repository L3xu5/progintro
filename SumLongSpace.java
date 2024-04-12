public class SumLongSpace {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println(0);
			return;
		}
		long result = 0;
		String inputString = String.join(" ", args) + " ";
		boolean wasDigit = false;
		int lastWhitespace = -1;
		for (int i = 0; i < inputString.length(); i++) {
			if (Character.isSpaceChar(inputString.charAt(i))) {
				if (wasDigit) {
					result += Long.parseLong(inputString.substring(lastWhitespace + 1, i));
					wasDigit = false;
				}
				lastWhitespace = i;
			}
			else {
				wasDigit = true;
			}
		}
		System.out.println(result);
	}
}