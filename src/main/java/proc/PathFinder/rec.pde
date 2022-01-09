final String sketchName = getClass().getName();
boolean begin = false;

import com.hamoid.*;
VideoExport videoExport;

void rec() {
  if (frameCount == 1 && !begin) {
    videoExport = new VideoExport(this, "../PathFinder/"+sketchName+".mp4");
    videoExport.setFrameRate(60);
    videoExport.startMovie();
    begin = true;
  }
  
  videoExport.saveFrame();
}
