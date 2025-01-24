PImage img;
String [] data;
String [][] ind;
int [][] stars;
int value;
int counter;
int[] rgbColors;
float[][] positions;
color[] groupColors;
HashMap<String, Integer> groupColorMap;
ArrayList<Trail> starTrails;
boolean flag1 = true;

int twinkleCount = 50;
float[][] twinklePositions;

void setup(){
  size(1800,800);
  frameRate(60);
  img = loadImage("star.png");
  
  groupColors = new color[] {
    color(173, 31, 50),    
    color(191, 86, 21),    
    color(191, 185, 21),   
    color(57, 191, 21),    
    color(23, 56, 152),    
    color(139, 70, 188),   
    color(229, 146, 199),  
    color(100, 149, 237)   
  };
  groupColorMap = new HashMap<String, Integer>();

  data = loadStrings("spreadsheet.csv");
  ind = new String [data.length][];
 
  for (int i=0; i< data.length; i++) {
    String [] dataItems = split(data[i],",");
    ind [i] = new String [dataItems.length];
    for (int d=0; d< dataItems.length; d++) {
      if (dataItems[d].equals("null")) {
        ind[i][d] = null;
      } else {
        ind[i][d] = dataItems[d];
      }
    }
  }
  
  stars = new int [data.length * 8][2];
  rgbColors = new int[data.length * 8];
  positions = new float[data.length * 8][2];
  starTrails = new ArrayList<>();
  
  int x = 0;
 
  for (int j=1;j<data.length;j++){
    counter = 0;
    if (ind[j].length>0 && ind[j][0] != null){
      for (int k = 1; k < 8; k++) { 
        String day = ind[0][k]; 
        day = trim(day);

        int starColor = getColorForDay(day);
        
        if (ind[j][k] != null) {
          value = int(ind[j][k]);
          
          if (x < stars.length) {
            stars[x][1] = value; 
            stars[x][0] = (j-1)*8 + counter; 
            
            rgbColors[x] = starColor; 
            
            positions[x][0] = counter * 140; 
            positions[x][1] = 100; 
            x++; 
          }
        }
        counter++;
      }
    } 
  }
  twinklePositions = new float[twinkleCount][2];
  for (int i = 0; i < twinkleCount; i++) {
    twinklePositions[i][0] = random(width);  // Random x
    twinklePositions[i][1] = random(height); // Random y
  }
}
 
int getColorForDay(String day) {
  switch (day) {
    case "Monday":
      return color(173, 31, 50); 
    case "Tuesday":
      return color(191, 86, 21); 
    case "Wednesday":
      return color(#BFB915); 
    case "Thursday":
      return color(#39BF15); 
    case "Friday":
      return color(#173898); 
    case "Saturday":
      return color(#8B46BC); 
    case "Sunday":
      return color(#E592C7); 
    default:
      return color(255); 
  } 
}

void draw(){
  //background
  background(3, 2, 55);
  int colorCounter = 0;
  for (int i = 0; i < twinkleCount; i++) {
    float d = random(2, 5); 
    fill(255);
    noStroke();
    circle(twinklePositions[i][0], twinklePositions[i][1], d);
  }
  
  fill(255,25);
  rect(1450,100, 300,600);
  
  fill(255);
  textSize(40);
  text("KEYS", 1550, 150);
  textSize(20);
  text("Line Toggle", 1480, 185);
  
  if (flag1 == true){
    fill(255,0,0);
  }
  else{
  fill(255);
  }
  rect(1590, 170,30,20);
  if (flag1 == true){
    textSize(20);
    fill(0);
    text("On", 1592, 187);
  }
  else {
    textSize(20);
    fill(0);
    text("Off", 1592, 187);
  }
    
  fill(255);
  textSize(20);
  text("Sudoku", 1550, 225);
  text("Minesweeper", 1550, 275);
  text("Valorant", 1550, 325);
  text("Stardew Valley", 1550, 375);
  text("DeadByDaylight", 1550, 425);
  text("League  (Lone Star)", 1550, 475);
  text("Fortnite  (Lone Star)", 1550, 525);
  text("Scrabble  (Lone Star)", 1550, 575);
  
  fill(173, 31, 50);
  rect(1480,200, 30,30);
  fill(191, 86, 21);    
  rect(1480,250, 30,30);
  fill(191, 185, 21);   
  rect(1480,300, 30,30);
  fill(57, 191, 21);    
  rect(1480,350, 30,30);
  fill(23, 56, 152);   
  rect(1480,400, 30,30);
  
  fill(139, 70, 188);   
  rect(1480,450, 30,30);
  fill(229, 146, 199);  
  rect(1480,500, 30,30);
  fill(100, 149, 237);
  rect(1480,550, 30,30);
  stroke(#AF1313);
  line(1480,450,1510,480);
  line(1480,500,1510,530);
  line(1480,550,1510,580);
  
  textSize(15);
  fill(173, 31, 50);
  rect(1480,600, 20,20);
  text("Monday", 1465, 635);
  fill(191, 86, 21);
  rect(1550,600, 20,20);
  text("Tuesday", 1535, 635);
  fill(#BFB915);
  rect(1620,600, 20,20);
  text("Wednesday", 1600, 635);
  fill(#39BF15);
  rect(1690,600, 20,20);
  text("Thursday", 1680, 635);
  fill(#173898);
  rect(1515,650, 20,20);
  text("Friday", 1505, 685);
  fill(#8B46BC);
  rect(1585,650, 20,20);
  text("Saturday", 1565, 685);
  fill(#E592C7);
  rect(1655,650, 20,20);
  text("Sunday", 1640, 685);
  
  HashMap<String, ArrayList<int[]>> gameStars = new HashMap<>();
  HashMap<String, ArrayList<Integer>> joinedStars = new HashMap<>();
  
    for (int j = 0; stars[j][1] > 0; j++) {
      int s = stars[j][1] - 20;
      
      float speedMultiplier = 0.1; 
      float speedX = stars[j][1] * speedMultiplier; 
      float speedY = stars[j][1] * speedMultiplier;
  
      positions[j][0] += speedX; 
      positions[j][1] += speedY;
      
      if (positions[j][0] > width+img.width/4) positions[j][0] = 0;
      if (positions[j][1] > height+img.height/4) positions[j][1] = 0;
      
      String gameId = ind[(stars[j][0] / 8) + 1][0]; 
      if (!joinedStars.containsKey(gameId)) {
        joinedStars.put(gameId, new ArrayList<Integer>());
        gameStars.put(gameId, new ArrayList<int[]>());
      }
      joinedStars.get(gameId).add(j);
      gameStars.get(gameId).add(new int[] {(int) positions[j][0], (int) positions[j][1]});
      
      if (isTrailTarget((stars[j][0] / 8) + 1, stars[j][1])) {
      if (starTrails.size() <= j || starTrails.get(j) == null) {
        while (starTrails.size() <= j) starTrails.add(null);
        starTrails.set(j, new Trail(positions[j][0], positions[j][1]));
      }
      starTrails.get(j).addTrailPosition(positions[j][0], positions[j][1]);
      starTrails.get(j).drawTrail();
    }
      int starColor = rgbColors[j]; 
      tint(starColor);
      imageMode(CENTER);
      image(img, positions[j][0], positions[j][1], s, s);
  }
      for (String gameId : gameStars.keySet()) {
        if (!groupColorMap.containsKey(gameId)) {
          groupColorMap.put(gameId, colorCounter % groupColors.length);
          colorCounter++; 
        }
        int groupIndex = groupColorMap.get(gameId);
        color groupLineColor = groupColors[groupIndex];
        stroke(groupLineColor);
    
        ArrayList<int[]> starPositions = gameStars.get(gameId);
        
        for (int i = 0; i < starPositions.size() - 1; i++) {
          int[] start = starPositions.get(i);
          int[] end = starPositions.get(i + 1);
    
          float dashLength = 10;
          float totalDist = dist(start[0], start[1], end[0], end[1]);
          float numDashes = totalDist / dashLength;
          for (float d = 0; d < 1; d += 1.0 / numDashes) {
            float x1 = lerp(start[0], end[0], d);
            float y1 = lerp(start[1], end[1], d);
            float x2 = lerp(start[0], end[0], d + 0.5 / numDashes);
            float y2 = lerp(start[1], end[1], d + 0.5 / numDashes);
            
            if (flag1 == true){
              line(x1, y1, x2, y2);
            }
        
      }
    }
  }
}

void mouseClicked() {
  if (mouseX >= 1590 && mouseX <= 1620 && mouseY >= 170 && mouseY <= 190){
    flag1 = false;
  }
}

class Trail {
  ArrayList<float[]> trailPositions;
  
  Trail(float x, float y) {
    trailPositions = new ArrayList<>();
    trailPositions.add(new float[] {x, y});
  }

  void addTrailPosition(float x, float y) {
    trailPositions.add(new float[] {x, y});
    if (trailPositions.size() > 10) {
      trailPositions.remove(0); 
    }
  }
  void drawTrail() {
    noFill();
    stroke(255, 150); 
    for (int i = 0; i < trailPositions.size() - 1; i++) {
      float[] start = trailPositions.get(i);
      float[] end = trailPositions.get(i + 1);
      float alpha = map(i, 0, trailPositions.size() - 1, 0, 255);
      stroke(255, alpha);
      line(start[0], start[1], end[0], end[1]);
    }
  }
}

boolean isTrailTarget(int row, int col) {
  return row == 6 || row == 7 || row == 8;
}
