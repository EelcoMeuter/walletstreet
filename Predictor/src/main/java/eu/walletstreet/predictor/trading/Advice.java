package eu.walletstreet.predictor.trading;

public abstract class Advice {

    private abstract Advice advice;

    public Advice(Advice advice) {
        this.advice = advice;
    }


    public final boolean advice() {
        if (advice == null) {
            return provideAdvice();
        }
        return provideAdvice() && advice.provideAdvice();
    }

    protected abstract boolean provideAdvice();

}
