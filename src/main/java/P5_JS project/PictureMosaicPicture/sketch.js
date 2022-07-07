let pic;
let smaller;
let video;
var scl = 8;
var w = 0, h = 0;
var initialized = false;
var images = [];
var img_brighness = [];
var brightImages = [];

function preload() {
  pic = loadImage("data/main_pic.jpg");

  for (let i = 1; i <= 12; i++) {
    //files.push("data/nature" + i + ".jpg");
    images.push(loadImage("data/nature" + i + ".jpg"));
  }
}

function setup() {
  createCanvas(windowWidth, windowHeight);

  w = floor(pic.width / scl);
  h = floor(pic.height / scl);
  smaller = createImage(w, h);
  smaller.copy(pic, 0, 0, pic.width, pic.height, 0, 0, w, h);
  video = createCapture(VIDEO);
  video.size(w, h);


  for (let i = 0; i < images.length; i++) {
    var avg = 0;
    images[i].loadPixels();
    var curr_len = images[i].pixels.length;
    for (let j = 0; j < curr_len; j += 4) {
      var b = brightness(images[i].pixels[j], images[i].pixels[j + 1], images[i].pixels[j + 2]);
      avg += b;
    }
    avg /= images[i].pixels.length / 4;
    img_brighness.push(avg);
  }
  
  for (let i = 0; i <= 255; i++) {
    var record = 256;
    for (let j = 0; j < img_brighness.length; j++) {
      var diff = abs(i - img_brighness[j]);
      if (diff < record) {
        record = diff;
        brightImages.push(images[j]);
      }
    }
  }


  // background(0);
  // smaller.loadPixels();
  // for (let x = 0; x < w; x++) {
  //   for (let y = 0; y < h; y++) {
  //     var index = (x + y * w) * 4;
  //     var c = color(smaller.pixels[index], smaller.pixels[index + 1],
  //       smaller.pixels[index + 2], smaller.pixels[index + 3]);
  //     var imageIndex = int(brightness(c));
  //     image(brightImages[imageIndex], x * scl, y * scl, scl, scl);
  //   }
  // }
}

function draw() {
  // image(pic, 0, 0);
  // image(smaller, 0, 0);
  
  background(0);
  video.loadPixels();
  for (let x = 0; x < w; x++) {
    for (let y = 0; y < h; y++) {
      var index = (x + y * w) * 4;
      var c = color(video.pixels[index], video.pixels[index + 1],
        video.pixels[index + 2], video.pixels[index + 3]);
      var imageIndex = int(brightness(c));
      image(brightImages[imageIndex], x * scl, y * scl, scl, scl);
    }
  }
}