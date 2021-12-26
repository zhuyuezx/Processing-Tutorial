package proc.FourierDrawing;

import processing.core.PVector;

public class Phasor{
    float amplitude, frequency, phase;

    public Phasor(float A, float f, float p){
        amplitude = A;
        frequency = f;
        phase = p;
    }


    public PVector state(float time, float offset){
        return PVector.fromAngle(frequency * time + phase + offset).mult(amplitude);
    }
}
