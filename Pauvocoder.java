import static java.lang.System.exit;

public class Pauvocoder {

    // Processing SEQUENCE size (100 msec with 44100Hz samplerate)
    final static int SEQUENCE = StdAudio.SAMPLE_RATE/10;

    // Overlapping size (20 msec)
    final static int OVERLAP = SEQUENCE/5 ;
    // Best OVERLAP offset seeking window (15 msec)
    final static int SEEK_WINDOW = 3*OVERLAP/4;

    public static void main(String[] args) {
        if (args.length < 2)
        {
            System.out.println("usage: pauvocoder <input.wav> <freqScale>\n");
            exit(1);
        }


        String wavInFile = args[0];
        double freqScale = Double.valueOf(args[1]);
        String outPutFile= wavInFile.split("\\.")[0] + "_" + freqScale +"_";

        // Open input .wev file
        double[] inputWav = StdAudio.read(wavInFile);

        // Resample test
        double[] newPitchWav = resample(inputWav, freqScale);
        StdAudio.save(outPutFile+"Resampled.wav", newPitchWav);

        // Simple dilatation
        double[] outputWav   = vocodeSimple(newPitchWav, 1.0/freqScale);
        StdAudio.save(outPutFile+"Simple.wav", outputWav);

        // Simple dilatation with overlaping
        outputWav = vocodeSimpleOver(newPitchWav, 1.0/freqScale);
        StdAudio.save(outPutFile+"SimpleOver.wav", outputWav);

        // Simple dilatation with overlaping and maximum cross correlation search
        outputWav = vocodeSimpleOverCross(newPitchWav, 1.0/freqScale);
        StdAudio.save(outPutFile+"SimpleOverCross.wav", outputWav);

        joue(outputWav);

        // Some echo above all
        outputWav = echo(outputWav, 100, 0.7);
        StdAudio.save(outPutFile+"SimpleOverCrossEcho.wav", outputWav);

        // Display waveform
        displayWaveform(outputWav);

    }

    /**
     * Resample inputWav with freqScale
     * @param inputWav
     * @param freqScale
     * @return resampled wav
     */
    public static double[] resample(double[] inputWav, double freqScale) {
        double saut = 0;
        double tailleinputWav = inputWav.length;
        double[] newinputWav = new double[1];

    if (freqScale > 1){
        double enleve = (freqScale - 1) / freqScale;
        newinputWav = new double[(int)(tailleinputWav-enleve)];
        saut = inputWav.length/enleve;

        for (int i = 0; i < newinputWav.length; i++){
            if (i /saut != 1){
                newinputWav[i] = inputWav[i];
                
            }
        }
    }else if (freqScale < 1){
        double ajoute = (1 - freqScale) / freqScale;
        newinputWav = new double[(int)(tailleinputWav+ajoute)] ;
        saut = inputWav.length/ajoute;

        for (int i = 0; i < newinputWav.length; i++){
            if (i /saut != 1){
                newinputWav[i] = inputWav[i];

            }else{
                double avant = inputWav[i-1];
                double apres = inputWav[i+1];
                newinputWav[i] = (avant + apres) / 2;
            }
        }


     }
    return newinputWav;
    }

    /**
     * Simple dilatation, without any overlapping
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
    public static double[] vocodeSimple(double[] inputWav, double dilatation) {
        double tailleinputWav = inputWav.length;
        double tailledilate = 0;
        double[] dilatedWav = new double[1];
        double seq = tailleinputWav/20;
        double saut = seq*dilatation;

        if (dilatation>1){
            //comprime le signal
            newinputWav = new double[(int)(tailleinputWav-)] ;

        } else if (dilatation<1){
            //dilate le signal
            tailledilate = tailleinputWav/dilatation;

        }


    }

    /**
     * Simple dilatation, with overlapping
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
    public static double[] vocodeSimpleOver(double[] inputWav, double dilatation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Simple dilatation, with overlapping and maximum cross correlation search
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
    public static double[] vocodeSimpleOverCross(double[] inputWav, double dilatation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Play the wav
     * @param wav
     */
    public static void joue(double[] wav) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Add an echo to the wav
     * @param wav
     * @param delay in msec
     * @param gain
     * @return wav with echo
     */
    public static double[] echo(double[] wav, double delay, double gain) {


    }

    /**
     * Display the waveform
     * @param wav
     */
    public static void displayWaveform(double[] wav) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


}





