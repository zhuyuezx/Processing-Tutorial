final String sketchName = getClass().getName();

import com.hamoid.*;
VideoExport videoExport;

void rec() {
  if (frameCount == 1) {
    videoExport = new VideoExport(this, "../"+sketchName+".mp4");
    videoExport.setFrameRate(30);
    videoExport.startMovie();
  }
  if (frameCount % 3 == 0)
    videoExport.saveFrame();
}
