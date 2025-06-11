package de.gothaer.math;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MultipiziererOptimierer implements Multiplizierer{
    private final Multiplizierer multiplizierer;

    public long multiplizier(final int a, final int b) {
        if (a<b)
         return multiplizierer.multiplizier(a, b);
        return multiplizierer.multiplizier(b, a);
    }
}
