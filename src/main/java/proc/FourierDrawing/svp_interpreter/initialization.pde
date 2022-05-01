String extractSvg(String url) {
  String data = "";
  try {
    File svg = new File(url);
    Scanner myReader = new Scanner(svg);
    while (myReader.hasNextLine()) {
      data += myReader.nextLine();
    }
    myReader.close();
  } 
  catch (FileNotFoundException e) {
    print("cannot open the file\n");
    e.printStackTrace();
  }
  String path = data.substring(data.indexOf("<path"));
  path = path.substring(path.indexOf(" d=\"") + 4);
  path = path.substring(0, path.indexOf("\""));
  return path;
}

ArrayList<String> extractCommands(String path) {
  ArrayList<String> res = new ArrayList();
  String prev = "";
  for (int i = 0; i < path.length(); i++) {
    char curr = path.charAt(i);
    if (('a' <= curr && curr <= 'z') || ('A' <= curr && curr <= 'Z')) {
      if (!prev.equals(""))
        res.add(prev);
      prev = "";
      res.add(Character.toString(curr));
    } else if ('0' <= curr && curr <= '9') {
      prev += curr;
    } else if (curr == ' ' || curr == ',') {
      if (!prev.equals(""))
        res.add(prev);
      prev = "";
    } else if (curr == '-') {
      if (prev.equals(""))
        prev += curr;
      else {
        res.add(prev);
        prev = Character.toString(curr);
      }
    } else if (curr == '.') {
      if (prev.equals("") || prev.equals("."))
        prev += curr;
      else if (prev.contains(".")) {
        res.add(prev);
        prev = Character.toString(curr);
      } else {
        prev += curr;
      }
    }
  }
  if (!prev.equals(""))
    res.add(prev);
  return res;
}

void commandsAutoFill() {
  ArrayList<String> temp = new ArrayList();
  HashMap<String, Integer> pool = new HashMap<String, Integer>() {{
      put("m", 3); put("M", 3); put("z", 1);
      put("v", 2); put("V", 2); 
      put("h", 2); put("H", 2);
      put("l", 3); put("L", 3);
      put("q", 5); put("Q", 5);
      put("t", 3); put("T", 3);
      put("c", 7); put("C", 7);
      put("s", 5); put("S", 5);
    }};
  String lastCommand = null;
  for (int i = 0; i < commands.size();) {
    String curr = commands.get(i);
    int val = pool.getOrDefault(curr, -1);
    if (val == -1) {
      if (lastCommand == null) {
        print("Badly Formatted Command\n");
        exit();
      } else {
        temp.add(lastCommand);
        val = pool.get(lastCommand);
        for (int j = 0; j < val - 1; j++) 
          temp.add(commands.get(i + j));
        i += val - 1;
      }
    } else {
      lastCommand = curr;
      for (int j = 0; j < val; j++)
        temp.add(commands.get(i +j));
      i += val;
    }
  }
  commands = temp;
  
  for (int i = 0; i < commands.size(); i++) {
    print(commands.get(i) + "\n");
  }
}
