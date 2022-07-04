function setupButtons() {
    // slider1 = createSlider(0, 0.5, 0.25, 0.01);
    // slider1.position(10, 10);
    // slider1.style('width', '80px');

    slider1 = createSlider(0, 1, 0.5, 0.1);
    slider1.position(10, 10);
    slider1.style('width', '80px');

    slider2 = createSlider(0, 1, 0.5, 0.1);
    slider2.position(10, 40);
    slider2.style('width', '80px');

    slider3 = createSlider(0, 2, 1, 0.1);
    slider3.position(10, 70);
    slider3.style('width', '80px');

    slider4 = createSlider(0, 1, 0.25, 0.05);
    slider4.position(10, 100);
    slider4.style('width', '80px');

    slider5 = createSlider(0, 2, 1, 0.1);
    slider5.position(10, 130);
    slider5.style('width', '80px');

    diffSlider = createSlider(1, 10, 1, 1);
    diffSlider.position(10, 160);
    diffSlider.style('width', '80px');
}