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
        if (inputWav == null || inputWav.length == 0) {
            throw new IllegalArgumentException("Le tableau d'entrée n'est pas valide'.");
        }

        if (freqScale <= 0) {
            throw new IllegalArgumentException("Le facteur de dilatation n'est pas valide.");
        }

        freqScale = 1.0 / freqScale;

        int newLength = (int) (inputWav.length * freqScale);

        double[] output = new double[newLength];

        for (int i = 0; i < newLength; i++) {
            int indice = (int) (i / freqScale);

            if (indice >= inputWav.length) {
                indice = inputWav.length - 1;
            }

            output[i] = inputWav[indice];
        }



        return output;
    }

    /**
     * Simple dilatation, without any overlapping
     * @param inputWav
     * @param dilatation factor
     * @return dilated wav
     */
public static double[] vocodeSimple(double[] inputWav, double dilatation) {
    int inputLength = inputWav.length;
    int newlength = (int) (inputLength / dilatation);
    double[] outputWav = new double[newlength];

    int compteur = 0; // Index pour parcourir l'entrée
    int newcompteur = 0; // Index pour remplir la sortie

    while (newcompteur < newlength) {
        // Copie les échantillons du segment courant
        for (int i = 0; i < SEQUENCE && newcompteur < newlength; i++) {
            if (compteur + i < inputLength) {
                outputWav[newcompteur] = inputWav[compteur + i];
            } else {
                outputWav[newcompteur] = 0.0; // Zéro padding si on dépasse les limites
            }
            newcompteur++;
        }

        // Avance dans l'entrée en fonction du facteur de fréquence
        compteur += (int) (SEQUENCE * dilatation);
    }

    return outputWav;
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
        throw new UnsupportedOperationException("Not implemented yet");

    }

    /**
     * Display the waveform
     * @param wav
     */
    public static void displayWaveform(double[] wav) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


}





