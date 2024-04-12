public class Sum{
	public static void main(String[] args){
		if (args.length != 0){
			int result = 0;
			for (int  i = 0; i < args.length; i++){
				boolean wasDigit = false;
				int lastWhitespace = -1;
				for (int j = 0; j < args[i].length(); j++){
					if (Character.isWhitespace(args[i].charAt(j))){
						if (wasDigit){
							result += Integer.parseInt(args[i].substring(lastWhitespace + 1, j));
							wasDigit = false;
						}
						lastWhitespace = j;
					}
					else {
						wasDigit = true;
					}
				}
				if (wasDigit){
					result += Integer.parseInt(args[i].substring(lastWhitespace + 1, args[i].length()));
				}
			}
			System.out.println(result);
		} else {
			System.out.println(0);
		}
	}
}